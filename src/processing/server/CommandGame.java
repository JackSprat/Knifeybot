package processing.server;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandGame extends CommandBase {

	@Override
	public boolean isValid(BlockingQueue<OutgoingMessage> listOut) {
			
		if (!((ProcInfo)parent).getChannelLive()) return false;
		if (((ProcInfo)parent).getCurrentGame() == "") return false;
		return true;
	}
	
	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
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
		
		listOut.add(new OutgoingMessage(OutType.CHAT, message, ((ProcInfo)parent).channel));
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "game.view"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":game"; }
	@Override public String getHelpString() 				{ return "This command shows what the current game and optionally gameinfo is"; }

}
