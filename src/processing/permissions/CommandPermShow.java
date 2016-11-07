package processing.permissions;

import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandPermShow extends CommandBase {

	@Override
	public void execute() {
		String user = getUser();
		String specifiedUser = getToken("user");
		
		if (specifiedUser != null && specifiedUser != "") user = specifiedUser;
		
		for (PermissionClass p : PermissionClass.values()){
			if (p.ordinal() == UserManager.getPermLevel(parent.channel, user)) {
				sendReply(user + " has group: " + p.toString());
			}
		}

	}

	@Override public String getPermissionString() 			{ return "permissions.setgroup"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "kperm show #user"; }
	@Override public String getHelpString() 				{ return "This command shows permissions, optionally for [user]"; }
	
}
