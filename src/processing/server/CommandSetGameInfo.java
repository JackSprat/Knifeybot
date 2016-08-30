package processing.server;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandSetGameInfo extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		String game = ((ProcInfo)parent).getCurrentGame();

		if (game != "") {
			((ProcInfo)parent).setGameAttribute("gameinfo", getToken("+"));
			listOut.add(new OutgoingMessage(OutType.CHAT, "Server gameinfo set", parent.channel));
		}
		
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "gameinfo.set"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "gameinfo +"; }
	@Override public String getHelpString() 				{ return "This command sets the current game's info"; }

}
