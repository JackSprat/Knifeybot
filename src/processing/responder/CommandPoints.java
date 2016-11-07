package processing.responder;

import processing.CommandBase;
import users.PermissionClass;

public class CommandPoints extends CommandBase {
	
	@Override
	public void execute() {
		String user = getUser();
		String specifiedUser = getToken("user");
		
		if (specifiedUser != null && specifiedUser != "") user = specifiedUser;
		
		int points = ((ProcResponder)parent).getPoints(user);
		
		String message = user + " has " + points + " points. ";
		
		sendReply(message);
		
	}
	
	@Override public String getPermissionString() 			{ return "responder.viewpoints"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "kpoints #user"; }
	@Override public String getHelpString() 				{ return "View current point total"; }

}
