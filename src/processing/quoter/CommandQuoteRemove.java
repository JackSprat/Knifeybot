package processing.quoter;

import channel.DataManager;
import processing.CommandBase;
import users.PermissionClass;

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
			
			DataManager.removeQuote(parent.channel, (long) id);
			sendReply("Quote " + id + " deleted");

		}
		
		sendReply(getToken("ID") + " is not a valid quote ID");

	}

	@Override public String getPermissionString() 			{ return "quoter.removequote"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kquote remove @ID"; }
	@Override public String getHelpString() 				{ return "This command removes a quote with quote ID <ID>"; }
	
}
