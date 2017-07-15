package processing.functions;

import logger.Logger;
import processing.CommandBase;
import state.ChannelState;

public class CommandUptime extends CommandBase {

	@Override
	public void execute() {

		if (ChannelState.isStreamLive(parent.channel)) return;
		
		int timeDiffMins = ChannelState.getUptimeMinutes(parent.channel);

		Logger.DEBUG("Stream time live in m: " + timeDiffMins);

		int hours = timeDiffMins / 60;
		String uptime = "Current stream uptime: " + 
						(hours 			< 9 ? "0" : "") + hours 		+ ":" + 
						(timeDiffMins 	< 9 ? "0" : "") + timeDiffMins;
		
		String message = "Stream Uptime: " + uptime;
		sendChatReply(message);
		
	}
	
	@Override public String getPermissionString() 			{ return "functions.uptime"; }
	@Override public String getFormatTokens() 				{ return "uptime"; }
	@Override public String getHelpString() 				{ return "Shows current stream uptime"; }

}
