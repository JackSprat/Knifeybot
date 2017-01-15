package processing.command;

import data.DataManager;
import processing.CommandBase;

public class CommandRemove extends CommandBase {

	@Override
	public void execute() {
		
		DataManager.removeCommand(parent.channel, getToken("@alias"));
		sendReply(getUser() + ", response \"" + getToken("@alias") + "\" deleted");
		
	}
	
	@Override public String getPermissionString() 			{ return "command.remove"; }
	@Override public String getFormatTokens() 				{ return "command remove @alias"; }
	@Override public String getHelpString() 				{ return "This command removes the response <alias>"; }
	
}