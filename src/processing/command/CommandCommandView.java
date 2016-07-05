package processing.command;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandCommandView extends CommandBase {

	@Override
	public boolean isMatch() {
		
		if (!getToken("alias").startsWith(":"))	return false;
									return true;
	}

	@Override
	public boolean isValid(BlockingQueue<OutgoingMessage> listOut) {
		
		String command = ((ProcCommand)parent).getCommand(getToken("alias").substring(1), getUser());
		
		if (command != null && command != "") 	return true;		
												return false;
		
		
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		String commandStr = ((ProcCommand)parent).getCommand(getToken("alias").substring(1), getUser());
		
		commandStr = commandStr.replaceAll("%me", getUser());
		return listOut.add(new OutgoingMessage(OutType.CHAT, commandStr, parent.channel));
		
	}

	@Override public String getPermissionString() 			{ return "command.view"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":<alias>"; }
	@Override public String getHelpString() 				{ return "This command views the response <alias>"; }
	
}
