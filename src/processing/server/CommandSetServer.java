package processing.server;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandSetServer extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		String game = ((ProcInfo)parent).getCurrentGame();

		if (game != "") {
			((ProcInfo)parent).setGameAttribute("server", getToken("+"));
			listOut.add(new OutgoingMessage(OutType.CHAT, "Server info set", parent.channel));
		}
		
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "server.set"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "server +"; }
	@Override public String getHelpString() 				{ return "This command sets the current game's server"; }

}
