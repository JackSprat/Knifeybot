package processing.responder;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandResub extends CommandBase {

	@Override
	public boolean isMatch() {
		
		if (!getUser().equalsIgnoreCase("twitchnotify")) 	return false;
		if (getToken("user").equalsIgnoreCase("knifeybot"))	return false;
															return true;
	}
	
	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {

		if (getToken("duration").equals("9")) {
			listOut.add(new OutgoingMessage(OutType.CHAT, "Congrats " + getToken("user") + " on the BABBY BabyRage pokketHype BabyRage pokketHype BabyRage pokketHype pokketH", parent.channel));
		} else if (getToken("duration").equals("12")) {
			listOut.add(new OutgoingMessage(OutType.CHAT, "Let's get this party started for " + getToken("user") + "! pokketHype FeelsBirthdayMan pokketHype FeelsBirthdayMan pokketHype pokketH", parent.channel));
		}
		
		int months = Integer.parseInt(getToken("duration"));
		String emote = "pokketHype ";
		String message = "";
		
		for (int i = 0; i < months; i++) { message += emote; }
		
		UserManager.subNotify(parent.channel, getToken("user"), months);
		listOut.add(new OutgoingMessage(OutType.CHAT, message, parent.channel));
		
		return true;
	}

	@Override public String getPermissionString() 			{ return "responder.resub"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return "<user> subscribed for <duration> months in a row!"; }
	@Override public String getHelpString() 				{ return "N/A"; }
	
}
