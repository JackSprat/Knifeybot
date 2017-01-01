package processing.quoter;

import data.DataManager;
import processing.CommandBase;
import users.PermissionClass;

public class CommandQuoteAdd extends CommandBase {

	@Override
	public void execute() {
		
		DataManager.addQuote(parent.channel, getToken("alias"), getToken("user"), getToken("+"));
		sendReply(getUser() + ", quote added pokketGOOD");
		
	}
	
	@Override public String getPermissionString() 			{ return "quoter.addquote"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kquote add @user @alias +"; }
	@Override public String getHelpString() 				{ return "This command adds a quote by user <user> which can be viewed by :kquote <alias>"; }
}
