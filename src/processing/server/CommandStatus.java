package processing.server;

import processing.CommandBase;
import users.PermissionClass;

public class CommandStatus extends CommandBase {

	@Override
	public void execute() {
		
		boolean isLive = ((ProcInfo)parent).getChannelLive();
		String message = isLive ? "Stream is live! \\ MrDestructoid /" : "Stream is not live pokketFeels";
		sendReply(message);
		
	}
	
	@Override public String getPermissionString() 			{ return "server.status"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "status"; }
	@Override public String getHelpString() 				{ return "This command checks stream live status"; }

}
