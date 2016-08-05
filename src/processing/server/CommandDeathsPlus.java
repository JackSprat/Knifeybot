package processing.server;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import state.ChannelState;
import users.PermissionClass;

public class CommandDeathsPlus extends CommandBase {

	
	
	@Override
	public boolean isValid(BlockingQueue<OutgoingMessage> listOut) {
			
		if (!((ProcInfo)parent).getChannelLive()) return false;
		if (((ProcInfo)parent).getCurrentGame() == "") return false;
		return true;
	}
	
	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		String message = "riPepperonis " + parent.channel + ", pokketFeels 7";
		ChannelState.addDeaths(parent.channel, 1);
		listOut.add(new OutgoingMessage(OutType.CHAT, message, parent.channel));
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "server.deathadd"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatString() 				{ return ":death+"; }
	@Override public String getHelpString() 				{ return "This command adds a death"; }

}
