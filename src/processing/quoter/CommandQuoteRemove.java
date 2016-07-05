package processing.quoter;

import java.util.concurrent.BlockingQueue;

import channel.ChannelManager;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandQuoteRemove extends CommandBase {
	
	@Override
	public boolean isValid(BlockingQueue<OutgoingMessage> listOut) {
			
		String q1 = ((ProcQuoter)parent).getQuote(getToken("ID"));
		
		if (q1 == null || q1 == "") {
			listOut.add(new OutgoingMessage(OutType.CHAT, "Can't find quote pokketFeels", parent.channel));
			return false;
		}
		
		return true;
		
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		int id;
		try {
			id = Integer.parseInt(getToken("ID"));
		} catch (@SuppressWarnings("unused") NumberFormatException nfe) {
			listOut.add(new OutgoingMessage(OutType.CHAT, getToken("ID") + " is not a valid quote ID", parent.channel));
			return false;
		}
		
		String q = ((ProcQuoter)parent).getQuote(getToken("ID"));
		
		if (q != null) {
			
			ChannelManager.removeQuote(parent.channel, (long) id);
			listOut.add(new OutgoingMessage(OutType.CHAT, "Quote " + id + " deleted", parent.channel));
			return true;

		}
		
		listOut.add(new OutgoingMessage(OutType.CHAT, getToken("ID") + " is not a valid quote ID", parent.channel));
		
		return false;

	}

	@Override public String getPermissionString() 			{ return "quoter.removequote"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatString() 				{ return ":kquote remove <ID>"; }
	@Override public String getHelpString() 				{ return "This command removes a quote with quote ID <ID>"; }
	
}
