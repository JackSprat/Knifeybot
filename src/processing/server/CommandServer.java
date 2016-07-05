package processing.server;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandServer extends CommandBase {

	@Override
	public boolean isValid(BlockingQueue<OutgoingMessage> listOut) {
			
		if (!((ProcInfo)parent).getChannelLive()) return false;
		if (((ProcInfo)parent).getCurrentGame() == "") return false;
		return true;
	}
	
	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		String game = ((ProcInfo)parent).getCurrentGame();
		String message = "No server found for " + game;
		
		String server = ((ProcInfo)parent).getGameAttribute("server");
		if (server != "") message = ((ProcInfo)parent).channel + " is playing " + game + " on " + server;
		
		listOut.add(new OutgoingMessage(OutType.CHAT, message, parent.channel));
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "server.view"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":server"; }
	@Override public String getHelpString() 				{ return "This command shows what the current game's server is set to"; }

}
