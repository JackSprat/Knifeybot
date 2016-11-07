package processing.server;

import processing.CommandBase;
import users.PermissionClass;

public class CommandSetServer extends CommandBase {

	@Override
	public void execute() {
		
		String game = ((ProcInfo)parent).getCurrentGame();

		if (game != "") {
			((ProcInfo)parent).setGameAttribute("server", getToken("+"));
			sendReply("Server info set");
		}
		
	}
	
	@Override public String getPermissionString() 			{ return "server.set"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "server +"; }
	@Override public String getHelpString() 				{ return "This command sets the current game's server"; }

}
