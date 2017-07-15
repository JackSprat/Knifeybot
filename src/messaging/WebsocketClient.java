package messaging;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.incoming.BaseIncomingMessage;
import messaging.outgoing.BaseOutgoingMessage;
import messaging.outgoing.OutgoingWebsocketCloseMessage;
import messaging.outgoing.OutgoingWebsocketMessage;
import utils.TextUtils;


public class WebsocketClient implements IReceiver, ISender {

	private BlockingQueue<BaseIncomingMessage>		messageListIn;
	private BlockingQueue<OutgoingWebsocketMessage>	messageListOut;
	private boolean									continueRunning	= true;

	public WebsocketClient(BlockingQueue<BaseIncomingMessage> listIn, BlockingQueue<OutgoingWebsocketMessage> listOut) {
		messageListIn = listIn;
		messageListOut = listOut;
	}

	@Override
	public void endExecution() {
		continueRunning = false;
	}

	@Override
	public void run() {
		continueRunning = true;
		while (true) {
			try {
				// open websocket
				WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(
						new URI("wss://pubsub-edge.twitch.tv"), messageListIn);
				// send message to websocket
				Thread.sleep(5000);
				while (continueRunning) {
					Thread.sleep(3000);
					BaseOutgoingMessage message = messageListOut.poll();
					if ((message != null) && (message instanceof OutgoingWebsocketMessage)) {
						Logger.INFO(TextUtils.setLength("Snd: " + message.getTypeString(), 15) + " - "
								+ ((OutgoingWebsocketMessage) message).text);
						clientEndPoint.sendMessage(message.toString());
					} else if ((message != null) && (message instanceof OutgoingWebsocketCloseMessage)) {
						endExecution();
					}
				}
			} catch (InterruptedException ex) {
				Logger.STACK("Websocket error", ex);
			} catch (URISyntaxException ex) {
				Logger.STACK("Websocket error", ex);
			}
		}
	}
}
