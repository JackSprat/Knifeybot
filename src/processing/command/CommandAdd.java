package processing.command;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandAdd extends CommandBase {
	
	@Override
	public boolean isValid(BlockingQueue<OutgoingMessage> listOut) {
		if (((ProcCommand)parent).getCommand(getToken("alias"), getUser()) != null)	return false;
																					return true;
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		UserManager.addCommand(getUser(), getToken("alias"), getToken("..."));
		return listOut.add(new OutgoingMessage(OutType.CHAT, getUser() + ", custom response \"" + getToken("alias") + "\" added", parent.channel));
		
	}

	@Override public String getPermissionString() 			{ return "command.add"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kcommand add <alias> ..."; }
	@Override public String getHelpString() 				{ return "This command adds a response \"...\" when you type :<alias>"; }

}
