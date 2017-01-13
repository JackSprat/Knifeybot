package processing.server;

import logger.Logger;
import processing.CommandBase;
import state.ChannelState;
import users.PermissionClass;

public class CommandUptime extends CommandBase {

	@Override
	public void execute() {

		if (ChannelState.isStreamLive(parent.channel)) return;
		
		int timeDiffMins = ChannelState.getUptimeMinutes(parent.channel);

		Logger.DEBUG("Stream time live in m: " + timeDiffMins);

		int hours = (int) (timeDiffMins / 60);
		String uptime = "Current stream uptime: " + 
						(hours 			< 9 ? "0" : "") + hours 		+ ":" + 
						(timeDiffMins 	< 9 ? "0" : "") + timeDiffMins;
		
		String message = "Stream Uptime: " + uptime;
		sendReply(message);
		
	}
	
	@Override public String getPermissionString() 			{ return "server.uptime"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "uptime"; }
	@Override public String getHelpString() 				{ return "Shows current stream uptime"; }

}
