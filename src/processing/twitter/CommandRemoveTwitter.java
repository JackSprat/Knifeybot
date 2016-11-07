package processing.twitter;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandRemoveTwitter extends CommandBase {

	public CommandRemoveTwitter() {
		this.setCommand("twitter").setKeyword("remove").setTokenCount(3, 3);
	}
	
	@Override
	public void execute() {
		
		boolean removed = ((ProcTwitter)parent).removeTwitter(tokenList.getToken(2));
		
		if (removed) listOut.add(new OutgoingMessage(OutType.CHAT, tokenList.getToken(2) + " has been removed as a new Twitter account", ((ProcTwitter)parent).channel));
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "server.view"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatString() 				{ return ":server"; }
	@Override public String getHelpString() 				{ return "This command shows what the current game's server is set to"; }

}
