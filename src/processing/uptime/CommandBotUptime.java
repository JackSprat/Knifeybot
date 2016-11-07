package processing.uptime;

import processing.CommandBase;
import users.PermissionClass;

public class CommandBotUptime extends CommandBase {

	@Override
	public void execute() {
		
		long timeDiff = System.currentTimeMillis() - ((ProcUptime)parent).startTime;
		timeDiff /= 1000;
		int secs = (int) (timeDiff % 60);
		int mins = (int) ((timeDiff / 60) % 60);
		int hours = (int) ((timeDiff / 3600) % 24);
		int days = (int) (timeDiff / (3600 * 24));
		String uptime = "Current bot uptime: " + 
						(days < 9 ? "0" + days : "" + days) + ":" + 
						(hours < 9 ? "0" + hours : "" + hours) + ":" + 
						(mins < 9 ? "0" + mins : "" + mins) + ":" + 
						(secs < 9 ? "0" + secs : "" + secs);
		sendReply(uptime);
		
	}
	
	@Override public String getPermissionString() 			{ return "uptime.bot"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kuptime"; }
	@Override public String getHelpString() 				{ return "This command shows Knifeybot's uptime"; }

}
