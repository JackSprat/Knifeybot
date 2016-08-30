package processing.quoter;

import java.util.concurrent.BlockingQueue;

import channel.ChannelManager;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandQuoteAdd extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		ChannelManager.addQuote(parent.channel, getToken("alias"), getToken("user"), getToken("+"));
		return listOut.add(new OutgoingMessage(OutType.CHAT, getUser() + ", quote added pokketGOOD", parent.channel));
		
	}
	
	@Override public String getPermissionString() 			{ return "quoter.addquote"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kquote add @user @alias +"; }
	@Override public String getHelpString() 				{ return "This command adds a quote by user <user> which can be viewed by :kquote <alias>"; }
}
