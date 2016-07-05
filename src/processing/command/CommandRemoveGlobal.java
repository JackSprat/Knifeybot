package processing.command;

import java.util.concurrent.BlockingQueue;

import channel.ChannelManager;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandRemoveGlobal extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		ChannelManager.removeCommand(parent.channel, getToken("alias"));
		listOut.add(new OutgoingMessage(OutType.CHAT, getUser() + ", global response \"" + getToken("alias") + "\" deleted", parent.channel));
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "command.removeglobal"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatString() 				{ return ":kcommand removeglobal <alias>"; }
	@Override public String getHelpString() 				{ return "This command removes the global response <alias>"; }
}
