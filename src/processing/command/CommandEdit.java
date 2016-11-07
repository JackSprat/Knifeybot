package processing.command;

import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandEdit extends CommandBase {

	@Override
	public boolean isValid() {
		if (((ProcCommand)parent).getCommand(getToken("alias"), getUser()) == null)	return false;
																									return true;
	}

	@Override
	public void execute() {
		
		UserManager.addCommand(getUser(), getToken("@alias"), getToken("+"));
		sendReply(getUser() + ", custom response \"" + getToken("@alias") + "\" edited");
		
	}

	@Override public String getPermissionString() 			{ return "command.edit"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "kcommand edit @alias +"; }
	@Override public String getHelpString() 				{ return "This command edits a response \"+\" when you type :alias"; }

}
