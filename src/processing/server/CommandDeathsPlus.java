package processing.server;

import processing.CommandBase;
import state.ChannelState;
import users.PermissionClass;

public class CommandDeathsPlus extends CommandBase {

	
	
	@Override
	public boolean isValid() {
			
		if (!((ProcInfo)parent).getChannelLive()) return false;
		if (((ProcInfo)parent).getCurrentGame() == "") return false;
		return true;
	}
	
	@Override
	public void execute() {
		
		String message = "riPepperonis " + parent.channel + ", pokketFeels 7";
		ChannelState.addDeaths(parent.channel, 1);
		sendReply(message);
		
	}
	
	@Override public String getPermissionString() 			{ return "server.deathadd"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "death+"; }
	@Override public String getHelpString() 				{ return "This command adds a death"; }

}
