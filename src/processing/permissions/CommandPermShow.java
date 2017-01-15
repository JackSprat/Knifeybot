package processing.permissions;

import data.DataManager;
import processing.CommandBase;

public class CommandPermShow extends CommandBase {

	@Override
	public void execute() {
		String user = getUser();
		String specifiedUser = getToken("user");
		
		if (specifiedUser != null && specifiedUser != "") user = specifiedUser;
		
		sendReply(user + " has group: " + DataManager.getUserLevel(parent.channel, user));

	}

	@Override public String getPermissionString() 			{ return "permissions.show"; }
	@Override public String getFormatTokens() 				{ return "kperm show #user"; }
	@Override public String getHelpString() 				{ return "This command shows permissions, optionally for [user]"; }
	
}
