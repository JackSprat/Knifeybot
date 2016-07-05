package processing.responder;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandPoints extends CommandBase {
	
	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		String user = getUser();
		String specifiedUser = getToken("user");
		
		if (specifiedUser != null && specifiedUser != "") user = specifiedUser;
		
		int points = ((ProcResponder)parent).getPoints(user);
		
		String message = user + " has " + points + " points. ";
		
		listOut.add(new OutgoingMessage(OutType.CHAT, message, parent.channel));
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "responder.viewpoints"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kpoints [user]"; }
	@Override public String getHelpString() 				{ return "View current point total"; }

}
