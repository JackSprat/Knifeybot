package processing.help;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandHelpView extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		listOut.add(new OutgoingMessage(OutType.CHAT, "A list of Knifeybot commands can be found at http://www.js47.co.uk/knifeybot/help.html", ((ProcHelp)parent).channel));
		return false;
		
	}
	
	@Override public String getPermissionString() 			{ return "help.view"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":khelp"; }
	@Override public String getHelpString() 				{ return "Views this page"; }
}
