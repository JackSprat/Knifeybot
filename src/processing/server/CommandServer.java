package processing.server;

import processing.CommandBase;
import users.PermissionClass;

public class CommandServer extends CommandBase {

	@Override
	public boolean isValid() {
			
		if (!((ProcInfo)parent).getChannelLive()) return false;
		if (((ProcInfo)parent).getCurrentGame() == "") return false;
		return true;
	}
	
	@Override
	public void execute() {
		
		String game = ((ProcInfo)parent).getCurrentGame();
		String message = "No server found for " + game;
		
		String server = ((ProcInfo)parent).getGameAttribute("server");
		if (server != "") message = ((ProcInfo)parent).channel + " is playing " + game + " on " + server;
		
		sendReply(message);
		
	}
	
	@Override public String getPermissionString() 			{ return "server.view"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "server"; }
	@Override public String getHelpString() 				{ return "This command shows what the current game's server is set to"; }

}
