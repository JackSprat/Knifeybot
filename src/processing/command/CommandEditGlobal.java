package processing.command;

import data.DataManager;
import processing.CommandBase;
import users.PermissionClass;

public class CommandEditGlobal extends CommandBase {

	@Override
	public boolean isValid() {
		if (((ProcCommand)parent).getCommand(getToken("@alias"), getUser()) == null)	return false;
																									return true;
	}

	@Override
	public void execute() {
		
		DataManager.addCommand(parent.channel, getToken("@alias"), getToken("+"));
		sendReply(getUser() + ", global response \"" + getToken("@alias") + "\" edited");
		
	}

	@Override public String getPermissionString() { return "command.editglobal"; }
	@Override public PermissionClass getPermissionClass() { return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kcommand editglobal @alias +"; }
	@Override public String getHelpString() 				{ return "This command edits a response \"+\" when you type :alias - This command will be active for every user"; }
	
	

}
