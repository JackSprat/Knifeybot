package processing.quoter;

import data.DataManager;
import processing.CommandBase;

public class CommandQuoteAdd extends CommandBase {

	@Override
	public void execute() {
		
		DataManager.addQuote(parent.channel, getToken("alias"), getToken("user"), getToken("+"));
		sendChatReply(getUser() + ", quote added pokketGOOD");
		
	}
	
	@Override public String getPermissionString() 			{ return "quotes.add"; }
	@Override public String getFormatTokens() 				{ return "kquote add @user @alias +"; }
	@Override public String getHelpString() 				{ return "This command adds a quote by user <user> which can be viewed by :kquote <alias>"; }
}
