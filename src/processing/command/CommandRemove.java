package processing.command;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandRemove extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		UserManager.removeCommand(getUser(), getToken("@alias"));
		listOut.add(new OutgoingMessage(OutType.CHAT, getUser() + ", custom response \"" + getToken("@alias") + "\" deleted", parent.channel));
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "command.remove"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "kcommand remove @alias"; }
	@Override public String getHelpString() 				{ return "This command removes the response <alias>"; }
}
