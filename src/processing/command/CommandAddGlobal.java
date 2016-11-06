package processing.command;

import java.util.concurrent.BlockingQueue;

import channel.DataManager;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandAddGlobal extends CommandBase {
	
	@Override
	public boolean isValid(BlockingQueue<OutgoingMessage> listOut) {
		if (((ProcCommand)parent).getCommand(getToken("alias"), getUser()) != null)	return false;
																					return true;
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		DataManager.addCommand(parent.channel, getToken("@alias"), getToken("+"));
		return listOut.add(new OutgoingMessage(OutType.CHAT, getUser() + ", global response \"" + getToken("@alias") + "\" added", parent.channel));
		
	}

	@Override public String getPermissionString() 			{ return "command.addglobal"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kcommand addglobal @alias +"; }
	@Override public String getHelpString() 				{ return "This command adds a response \"...\" when you type :alias - This command will be active for every user in this channel"; }
	
	

}
