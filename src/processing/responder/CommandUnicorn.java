package processing.responder;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandUnicorn extends CommandBase {

	@Override
	public boolean isMatch() {
		
		if (!getToken("...").contains("imgur.com/XtiM1rK"))	return false;
															return true;
	}
	
	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {

		listOut.add(new OutgoingMessage(OutType.CHAT, "DO NOT click the link that " + getUser() + " just posted!", parent.channel));
		
		return true;
	}

	@Override public String getPermissionString() 			{ return "responder.unicorn"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return "..."; }
	@Override public String getHelpString() 				{ return "N/A"; }
	
}
