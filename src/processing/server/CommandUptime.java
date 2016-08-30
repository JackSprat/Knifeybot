package processing.server;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandUptime extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {

		if (!((ProcInfo)parent).getChannelLive()) return false;
		
		long timeDiffSecs = (System.currentTimeMillis() - ((ProcInfo)parent).getStreamStartTime())/1000;

		Logger.DEBUG("Stream time live in s: " + timeDiffSecs);
		int secs = (int) (timeDiffSecs % 60);
		int mins = (int) ((timeDiffSecs / 60) % 60);
		int hours = (int) ((timeDiffSecs / 3600) % 24);
		String uptime = "Current stream uptime: " + 
						(hours < 9 ? "0" + hours : "" + hours) + ":" + 
						(mins < 9 ? "0" + mins : "" + mins) + ":" + 
						(secs < 9 ? "0" + secs : "" + secs);
		
		String message = "Stream Uptime: " + uptime;
		listOut.add(new OutgoingMessage(OutType.CHAT, message, parent.channel));
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "server.uptime"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "uptime"; }
	@Override public String getHelpString() 				{ return "Shows current stream uptime"; }

}
