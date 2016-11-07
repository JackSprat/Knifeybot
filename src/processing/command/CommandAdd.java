package processing.command;

import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandAdd extends CommandBase {
	
	@Override
	public boolean isValid() {
		if (((ProcCommand)parent).getCommand(getToken("@alias"), getUser()) != null)	return false;
																						return true;
	}

	@Override
	public void execute() {
		
		UserManager.addCommand(getUser(), getToken("@alias"), getToken("+"));
		sendReply(", custom response \"" + getToken("@alias") + "\" added");

	}

	@Override public String getPermissionString() 			{ return "command.add"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "kcommand add @alias +"; }
	@Override public String getHelpString() 				{ return "This command adds a response \"...\" when you type :<alias>"; }

}
