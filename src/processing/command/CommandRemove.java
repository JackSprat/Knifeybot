package processing.command;

import data.DataManager;
import processing.CommandBase;
import users.PermissionClass;

public class CommandRemove extends CommandBase {

	@Override
	public void execute() {
		
		DataManager.removeCommand(parent.channel, getToken("@alias"));
		sendReply(getUser() + ", global response \"" + getToken("@alias") + "\" deleted");
		
	}
	
	@Override public String getPermissionString() 			{ return "command.remove"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kcommand remove @alias"; }
	@Override public String getHelpString() 				{ return "This command removes the response <alias>"; }
}
