package processing.quoter;

import data.DataManager;
import processing.CommandBase;

public class CommandQuoteView extends CommandBase {

	@Override
	public boolean isMatch() {
		
		if (	getToken("alias").equalsIgnoreCase("add") || 
				getToken("alias").equalsIgnoreCase("viewmulti") || 
				getToken("alias").equalsIgnoreCase("remove"))						return false;
																						return true;
	}

	@Override
	public boolean isValid() {
		
		if (getToken("alias").equalsIgnoreCase("random")) return true;
		
		String q1 = ((ProcQuoter)parent).getQuote(getToken("alias"));
		if (q1 == null || q1 == "") {
			sendChatReply("Can't find quote pokketFeels");
			return false;
		}
		
		return true;

	}

	@Override
	public void execute() {
		
		if (getToken("alias").equalsIgnoreCase("random")) {
			sendChatReply(DataManager.getRandomQuote(parent.channel));
			return;
		}

		sendChatReply(((ProcQuoter)parent).getQuote(getToken("alias")));
		
	}

	@Override public String getPermissionString() 			{ return "quotes.view"; }
	@Override public String getFormatTokens() 				{ return "kquote @alias"; }
	@Override public String getHelpString() 				{ return "This command views a quote with ID or alias <alias>, or a random quote if <alias> is \"random\""; }
	
}
