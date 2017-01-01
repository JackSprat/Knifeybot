package processing.command;

import data.DataManager;
import processing.CommandBase;
import users.PermissionClass;

public class CommandAdd extends CommandBase {
	
	@Override
	public void execute() {
		
		DataManager.addCommand(parent.channel, getToken("@alias"), getToken("+"));
		sendReply(getUser() + ", global response \"" + getToken("@alias") + "\" added");
		
	}

	@Override public String getPermissionString() 			{ return "command.add"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kcommand add @alias +"; }
	@Override public String getHelpString() 				{ return "This command adds a response \"...\" when you type :alias"; }
	
	

}
