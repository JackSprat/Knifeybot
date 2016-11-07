package processing.server;

import processing.CommandBase;
import state.ChannelState;
import users.PermissionClass;

public class CommandDeathsMinus extends CommandBase {

	
	
	@Override
	public boolean isValid() {
			
		if (!((ProcInfo)parent).getChannelLive()) return false;
		if (((ProcInfo)parent).getCurrentGame() == "") return false;
		return true;
	}
	
	@Override
	public void execute() {
		
		String message = "Who fucked up? pokketKappa";
		ChannelState.addDeaths(parent.channel, -1);
		sendReply(message);
		
	}
	
	@Override public String getPermissionString() 			{ return "server.deathminus"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "death-"; }
	@Override public String getHelpString() 				{ return "This command adds a death"; }

}
