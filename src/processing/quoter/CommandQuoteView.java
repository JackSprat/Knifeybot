package processing.quoter;

import java.util.concurrent.BlockingQueue;

import channel.ChannelManager;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandQuoteView extends CommandBase {

	@Override
	public boolean isMatch() {
		
		if (	getToken("alias").equalsIgnoreCase("add") || 
				getToken("alias").equalsIgnoreCase("remove"))						return false;
																						return true;
	}

	@Override
	public boolean isValid(BlockingQueue<OutgoingMessage> listOut) {
		
		if (getToken("alias").equalsIgnoreCase("random")) return true;
		
		String q1 = ((ProcQuoter)parent).getQuote(getToken("alias"));
		if (q1 == null || q1 == "") {
			listOut.add(new OutgoingMessage(OutType.CHAT, "Can't find quote pokketFeels", parent.channel));
			return false;
		}
		
		return true;

	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		if (getToken("alias").equalsIgnoreCase("random")) {
			String q = ChannelManager.getRandomQuote(parent.channel);
			listOut.add(new OutgoingMessage(OutType.CHAT, q, ((ProcQuoter)parent).channel));
			return true;
		}

		String q = ((ProcQuoter)parent).getQuote(getToken("alias"));
		listOut.add(new OutgoingMessage(OutType.CHAT, q, parent.channel));
		return true;
		
	}

	@Override public String getPermissionString() 			{ return "quoter.viewquote"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "kquote @alias"; }
	@Override public String getHelpString() 				{ return "This command views a quote with ID or alias <alias>, or a random quote if <alias> is \"random\""; }
	
}
