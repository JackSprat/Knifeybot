package processing.server;

import processing.CommandBase;
import users.PermissionClass;

public class CommandSetGameInfo extends CommandBase {

	@Override
	public void execute() {
		
		String game = ((ProcInfo)parent).getCurrentGame();

		if (game != "") {
			((ProcInfo)parent).setGameAttribute("gameinfo", getToken("+"));
			sendReply("Server gameinfo set");
		}
		
	}
	
	@Override public String getPermissionString() 			{ return "gameinfo.set"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "gameinfo +"; }
	@Override public String getHelpString() 				{ return "This command sets the current game's info"; }

}
