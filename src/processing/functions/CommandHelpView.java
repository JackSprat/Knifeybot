package processing.functions;

import processing.CommandBase;

public class CommandHelpView extends CommandBase {

	@Override
	public void execute() {
		
		sendReply("A list of Knifeybot commands can be found at http://www.js47.co.uk/knifeybot/help.php?=" + parent.channel);
		
	}
	
	@Override public String getPermissionString() 			{ return "functions.help"; }
	@Override public String getFormatTokens() 				{ return "help"; }
	@Override public String getHelpString() 				{ return "Views this page"; }
}
