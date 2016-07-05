package processing.responder;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandSub extends CommandBase {

	@Override
	public boolean isMatch() {
		
		if (!getUser().equalsIgnoreCase("twitchnotify")) 		return false;
		if (getToken("user").equalsIgnoreCase("knifeybot"))	return false;
																	return true;
	}
	
	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		UserManager.subNotify(parent.channel, getToken("user"), 1);
		listOut.add(new OutgoingMessage(OutType.CHAT, "A wild " + getToken("user") + " has appeared! pokketHype pokketH pokketGOOD pokketGOOD pokketH pokketHype", parent.channel));
		return true;
	}

	@Override public String getPermissionString() 			{ return "responder.sub"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return "<user> just subscribed!"; }
	@Override public String getHelpString() 				{ return "N/A"; }
	
}
