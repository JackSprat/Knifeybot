package processing.help;

import processing.CommandBase;
import users.PermissionClass;

public class CommandHelpView extends CommandBase {

	@Override
	public void execute() {
		
		sendReply("A list of Knifeybot commands can be found at http://www.js47.co.uk/knifeybot/help.html");
		
	}
	
	@Override public String getPermissionString() 			{ return "help.view"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "khelp"; }
	@Override public String getHelpString() 				{ return "Views this page"; }
}
