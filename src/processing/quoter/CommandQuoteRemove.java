package processing.quoter;

import data.DataManager;
import processing.CommandBase;

public class CommandQuoteRemove extends CommandBase {
	
	@Override
	public boolean isValid() {
			
		String q1 = ((ProcQuoter)parent).getQuote(getToken("ID"));
		
		if (q1 == null || q1 == "") {
			sendReply("Can't find quote pokketFeels");
			return false;
		}
		
		return true;
		
	}

	@Override
	public void execute() {
		
		int id = 0;
		try {
			id = Integer.parseInt(getToken("ID"));
		} catch (@SuppressWarnings("unused") NumberFormatException nfe) {
			sendReply(getToken("ID") + " is not a valid quote ID");
		}
		
		String q = ((ProcQuoter)parent).getQuote(getToken("ID"));
		
		if (q != null) {
			
			DataManager.removeQuote(parent.channel, id);
			sendReply("Quote " + id + " deleted");

		}
		
		sendReply(getToken("ID") + " is not a valid quote ID");

	}

	@Override public String getPermissionString() 			{ return "quotes.remove"; }
	@Override public String getFormatTokens() 				{ return "kquote remove @ID"; }
	@Override public String getHelpString() 				{ return "This command removes a quote with quote ID <ID>"; }
	
}
