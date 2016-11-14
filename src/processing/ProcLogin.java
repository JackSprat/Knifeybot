package processing;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.IIncomingMessage;
import messaging.IncomingMessage.InType;
import messaging.OutgoingMessage;
import utils.ConfigLoader;

public class ProcLogin extends ProcBase {

	private boolean sentNick, sentPass, loggedIn = false;
	
	public ProcLogin(BlockingQueue<OutgoingMessage> listOut, String channel) {
		super(listOut, channel);
	}
	
	public void reset() {
		sentNick = false;
		sentPass = false;
		loggedIn = false;
	}
	
	@Override
	public void parseMessage(IIncomingMessage in) {
		
		if (in.getType() == InType.IRCINFO && in.getID().equals("004")) {
			sendRaw("CAP REQ :twitch.tv/membership\r\n");
			sendRaw("JOIN #" + this.channel + "\r\n");
			loggedIn = true;
			
			Logger.INFO("Logged in correctly!");
			Logger.INFO("Attempting to join channel: #" + this.channel);
		}
		
		if (in.getType() == InType.IRCPING) {
			sendPong();
		}
		
	}

	@Override
	public void tick() {
		
		if (loggedIn) { return; }
		
		if (!sentPass) {
			sendRaw("PASS " + ConfigLoader.getOAuth() + "\r\n");
			sentPass = true;
			return;
		}
		
		if (!sentNick) {
			sendRaw("NICK " + ConfigLoader.getNick() + "\r\n");
			sentNick = true;
			return;
		}

	}

	
	private void sendRaw(String message) {
		listOut.add(new OutgoingMessage(OutgoingMessage.OutType.RAW, message, this.channel, ""));
	}
	
	private void sendPong() {
		listOut.add(new OutgoingMessage(OutgoingMessage.OutType.PONG, "tmi.twitch.tv\r\n", this.channel, ""));
	}
	
}