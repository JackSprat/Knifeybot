package processing.command;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandEdit extends CommandBase {

	@Override
	public boolean isValid(BlockingQueue<OutgoingMessage> listOut) {
		if (((ProcCommand)parent).getCommand(getToken("alias"), getUser()) == null)	return false;
																									return true;
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		UserManager.addCommand(getUser(), getToken("alias"), getToken("..."));
		return listOut.add(new OutgoingMessage(OutType.CHAT, getUser() + ", custom response \"" + getToken("alias") + "\" edited", parent.channel));
		
	}

	@Override public String getPermissionString() 			{ return "command.edit"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kcommand edit <alias> ..."; }
	@Override public String getHelpString() 				{ return "This command edits a response \"...\" when you type :<alias>"; }

}
