package processing.server;

import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandSub extends CommandBase {

	@Override
	public void execute() {
		String user = getUser();
		String specifiedUser = getToken("user");
		
		if (specifiedUser != null && specifiedUser != "") user = specifiedUser;
		
		boolean isSubbed = UserManager.isSubbed(parent.channel, user);
		int subLength = UserManager.getSubLength(parent.channel, user);
		
		String message = "@" + getUser() + ", " + 
				(isSubbed ? user + " hase been subbed for " + subLength + " month" +
						(subLength == 1 ? "!" : "s!") 
				: user + " is not subbed to this channel");
		sendReply(message);
		
	}
	
	@Override public String getPermissionString() 			{ return "server.ksub"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "ksub #user"; }
	@Override public String getHelpString() 				{ return "Use to check how long you're subbed for"; }

}
