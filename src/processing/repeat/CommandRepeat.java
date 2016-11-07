package processing.repeat;

import processing.CommandBase;
import users.PermissionClass;

public class CommandRepeat extends CommandBase {

	@Override
	public void execute() {
		
		sendReply(getToken("+"));

	}

	@Override public String getPermissionString() 			{ return "repeat.repeat"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.SuperAdmin; }
	@Override public String getFormatTokens() 				{ return "krepeat @channel +"; }
	@Override public String getHelpString() 				{ return "N/A"; }
	
}
