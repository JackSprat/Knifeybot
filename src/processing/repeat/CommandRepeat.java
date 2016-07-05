package processing.repeat;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandRepeat extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		listOut.add(new OutgoingMessage(OutType.CHAT, getToken("..."), getToken("channel")));

		return true;
	}

	@Override public String getPermissionString() 			{ return "repeat.repeat"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.SuperAdmin; }
	@Override public String getFormatString() 				{ return ":krepeat <channel> ..."; }
	@Override public String getHelpString() 				{ return "N/A"; }
	
}
