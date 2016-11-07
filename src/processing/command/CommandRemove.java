package processing.command;

import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandRemove extends CommandBase {

	@Override
	public void execute() {
		
		UserManager.removeCommand(getUser(), getToken("@alias"));
		sendReply(getUser() + ", custom response \"" + getToken("@alias") + "\" deleted");
		
	}
	
	@Override public String getPermissionString() 			{ return "command.remove"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "kcommand remove @alias"; }
	@Override public String getHelpString() 				{ return "This command removes the response <alias>"; }
}
