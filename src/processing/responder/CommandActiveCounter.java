package processing.responder;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.IncomingMessage.InType;
import messaging.OutgoingMessage;
import processing.CommandBase;
import users.PermissionClass;

public class CommandActiveCounter extends CommandBase {
	
	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		((ProcResponder)parent).addUserToSet(getUser());

		return true;
		
	}

	@Override public String getPermissionString() 			{ return "responder.active"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ""; }
	@Override public String getHelpString() 				{ return "N/A"; }
	
}
