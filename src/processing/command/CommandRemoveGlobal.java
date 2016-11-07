package processing.command;

import channel.DataManager;
import processing.CommandBase;
import users.PermissionClass;

public class CommandRemoveGlobal extends CommandBase {

	@Override
	public void execute() {
		
		DataManager.removeCommand(parent.channel, getToken("@alias"));
		sendReply(getUser() + ", global response \"" + getToken("@alias") + "\" deleted");
		
	}
	
	@Override public String getPermissionString() 			{ return "command.removeglobal"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kcommand removeglobal @alias"; }
	@Override public String getHelpString() 				{ return "This command removes the global response <alias>"; }
}
