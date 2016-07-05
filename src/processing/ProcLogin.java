package processing;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.IIncomingMessage;
import messaging.IncomingMessage.InType;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import utils.ConfigLoader;
import utils.Constants;

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
			listOut.add(new OutgoingMessage(OutgoingMessage.OutType.RAW, "CAP REQ :twitch.tv/membership\r\n", channel));
			listOut.add(new OutgoingMessage(OutgoingMessage.OutType.RAW, "JOIN #" + this.channel + "\r\n", channel));
			loggedIn = true;
			
			Logger.INFO("Logged in correctly!");
			Logger.INFO("Attempting to join channel: #" + this.channel);
		}
		
		if (in.getType() == InType.IRCPING) {
			listOut.add(new OutgoingMessage(OutgoingMessage.OutType.PONG, "tmi.twitch.tv\r\n", channel));
		}
		
	}

	@Override
	public void tick() {
		
		if (loggedIn) { return; }
		
		if (!sentPass) {
			listOut.add(new OutgoingMessage(OutType.RAW, "PASS " + ConfigLoader.getOAuth() + "\r\n", channel));
			sentPass = true;
			return;
		}
		
		if (!sentNick) {
			listOut.add(new OutgoingMessage(OutType.RAW, "NICK " + ConfigLoader.getNick() + "\r\n", channel));
			sentNick = true;
			return;
		}

	}
	
}