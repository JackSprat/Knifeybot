package processing.server;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandStatus extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		boolean isLive = ((ProcInfo)parent).getChannelLive();
		String message = isLive ? "Stream is live! \\ MrDestructoid /" : "Stream is not live pokketFeels";
		listOut.add(new OutgoingMessage(OutType.CHAT, message, ((ProcInfo)parent).channel));
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "server.status"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "status"; }
	@Override public String getHelpString() 				{ return "This command checks stream live status"; }

}
