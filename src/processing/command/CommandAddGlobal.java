package processing.command;

import channel.DataManager;
import processing.CommandBase;
import users.PermissionClass;

public class CommandAddGlobal extends CommandBase {
	
	@Override
	public boolean isValid() {
		if (((ProcCommand)parent).getCommand(getToken("alias"), getUser()) != null)	return false;
																					return true;
	}

	@Override
	public void execute() {
		
		DataManager.addCommand(parent.channel, getToken("@alias"), getToken("+"));
		sendReply(getUser() + ", global response \"" + getToken("@alias") + "\" added");
		
	}

	@Override public String getPermissionString() 			{ return "command.addglobal"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kcommand addglobal @alias +"; }
	@Override public String getHelpString() 				{ return "This command adds a response \"...\" when you type :alias - This command will be active for every user in this channel"; }
	
	

}
