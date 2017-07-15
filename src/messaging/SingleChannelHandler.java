package messaging;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import data.DataManager;
import logger.Logger;
import messaging.incoming.BaseIncomingMessage;
import messaging.incoming.IncomingIRCChatMessage;
import messaging.incoming.IncomingIRCInfoMessage;
import messaging.incoming.IncomingIRCPingMessage;
import messaging.incoming.IncomingWebsocketSubMessage;
import messaging.outgoing.BaseOutgoingMessage;
import messaging.outgoing.OutgoingIRCMessage;
import messaging.outgoing.OutgoingIRCPongMessage;
import messaging.outgoing.OutgoingIRCRawMessage;
import messaging.outgoing.OutgoingWebsocketMessage;
import processing.ProcManager;
import state.ChannelState;
import utils.ConfigLoader;
import utils.Constants;


public class SingleChannelHandler implements IChannelHandler {

	private final String	channel;
	private ProcManager		p;
	private ISender			ircSender;
	private IReceiver		ircReceiver;
	private WebsocketClient	wsClient;
	private boolean			continueRunning	= true;

	public SingleChannelHandler(String channel) {
		this.channel = channel;
		ChannelState.registerChannel(channel);
	}

	@Override
	public String getChannel() {
		return channel;
	}

	@Override
	public void run() {
		BlockingQueue<OutgoingIRCMessage> messageToIRC = ChannelState.getMessageToIRC(channel);
		BlockingQueue<BaseIncomingMessage> messageToProc = ChannelState.getMessageToProc(channel);
		BlockingQueue<BaseIncomingMessage> messageFromIRC = ChannelState.getMessageFromIRC(channel);
		BlockingQueue<BaseOutgoingMessage> messageFromInternal = ChannelState.getMessageFromInternal(channel);
		BlockingQueue<BaseIncomingMessage> messageFromWebsocket = ChannelState.getMessageFromWebsocket(channel);
		BlockingQueue<OutgoingWebsocketMessage> messageToWebsocket = ChannelState.getMessageToWebsocket(channel);
		boolean sentLogin = false;
		try (Socket socket = new Socket(Constants.server, Constants.port)) {
			Logger.DEBUG("Creating IRC and processor threads for " + getChannel());
			ircSender = new IRCSender(socket, messageToIRC);
			ircReceiver = new IRCReceiver(socket, messageFromIRC);
			p = new ProcManager(messageToProc, messageFromInternal, getChannel());
			new Thread(ircSender).start();
			new Thread(ircReceiver).start();
			wsClient = new WebsocketClient(messageFromWebsocket, messageToWebsocket);
			int userID = DataManager.getUserID(channel);
			System.out.println(channel + ": " + userID);
			String oauthKey = DataManager.getOauthKey(userID, "channel_subscriptions");
			System.out.println(userID + ": " + oauthKey);
			if (!oauthKey.equals("")) {
				new Thread(wsClient).start();
				messageToWebsocket.add(new OutgoingWebsocketMessage(
						"{\"type\": \"LISTEN\",\"nonce\": \"\",\"data\": {\"topics\": [\"channel-subscribe-events-v1."
								+ userID + "\"], \"auth_token\": \"" + oauthKey + "\"}}"));
			}
			p.start();
			Logger.DEBUG("Entering message passing loop for " + getChannel());
			while (continueRunning) {
				if (!sentLogin) {
					messageToIRC.add(new OutgoingIRCRawMessage("PASS " + ConfigLoader.getOAuth() + "\r\n"));
					messageToIRC.add(new OutgoingIRCRawMessage("NICK " + ConfigLoader.getUsername() + "\r\n"));
					sentLogin = true;
				}
				BaseIncomingMessage messageIRCIn = messageFromIRC.poll();
				if (messageIRCIn != null) {
					ChannelState.newMessageNotify(channel, messageIRCIn);
					if (messageIRCIn instanceof IncomingIRCChatMessage) {
						messageToProc.add(messageIRCIn);
					} else if (messageIRCIn instanceof IncomingIRCPingMessage) {
						messageToIRC.add(new OutgoingIRCPongMessage("tmi.twitch.tv\r\n"));
					} else if ((messageIRCIn instanceof IncomingIRCInfoMessage)
							&& ((IncomingIRCInfoMessage) messageIRCIn).getID().equals("004")) {
						messageToIRC.add(new OutgoingIRCRawMessage("CAP REQ :twitch.tv/membership\r\n"));
						messageToIRC.add(new OutgoingIRCRawMessage("JOIN #" + channel + "\r\n"));
						Logger.INFO("Attempting to join channel: #" + channel);
					}
				}
				if (!oauthKey.equals("")) {
					BaseIncomingMessage messageWebsocketIn = messageFromWebsocket.poll();
					if (messageWebsocketIn != null) {
						ChannelState.newMessageNotify(channel, messageWebsocketIn);
						if (messageWebsocketIn instanceof IncomingWebsocketSubMessage) {
							messageToProc.add(messageWebsocketIn);
						}
					}
				}
				BaseOutgoingMessage messageProcOut = messageFromInternal.poll();
				if ((messageProcOut != null) && (messageProcOut instanceof OutgoingIRCMessage)) {
					messageToIRC.add((OutgoingIRCMessage) messageProcOut);
				} else if ((messageProcOut != null) && (messageProcOut instanceof OutgoingWebsocketMessage)) {
					messageToWebsocket.add((OutgoingWebsocketMessage) messageProcOut);
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					Logger.STACK("", e);
				}
			}
			ircSender.endExecution();
			ircReceiver.endExecution();
			p.endExecution();
		} catch (IOException e) {
			Logger.STACK("Error creating socket for channel " + channel, e);
		}
	}

	@Override
	public void terminateExecution() {
		continueRunning = false;
	}
}
