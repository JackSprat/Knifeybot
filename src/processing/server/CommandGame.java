package processing.server;

import java.io.UnsupportedEncodingException;
import logger.Logger;
import processing.CommandBase;
import users.PermissionClass;

public class CommandGame extends CommandBase {

	@Override
	public boolean isValid() {
			
		if (!((ProcInfo)parent).getChannelLive()) return false;
		if (((ProcInfo)parent).getCurrentGame() == "") return false;
		return true;
	}
	
	@Override
	public void execute() {
		
		String game = ((ProcInfo)parent).getCurrentGame();
		
		String gameInfo = ((ProcInfo)parent).getGameAttribute("gameinfo");
		String outputString = "";
		try {
		    byte[] utf8Bytes = game.getBytes("UTF8");

		    outputString = new String(utf8Bytes, "UTF8");
		} 
		catch (UnsupportedEncodingException e) {
			Logger.STACK("", e);
		}
		String message = ((ProcInfo)parent).channel + " is playing " + outputString + ". " + gameInfo;
		
		sendReply(message);
		
	}
	
	@Override public String getPermissionString() 			{ return "game.view"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "game"; }
	@Override public String getHelpString() 				{ return "This command shows what the current game and optionally gameinfo is"; }

}
