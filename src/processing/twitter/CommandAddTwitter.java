package processing.twitter;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandAddTwitter extends CommandBase {

	public CommandAddTwitter() {
		this.setCommand("twitter").setKeyword("set").setTokenCount(3, 3);
	}
	
	@Override
	public void execute() {
		
		boolean added = ((ProcTwitter)parent).addTwitter(tokenList.getToken(2));
		
		if (added) listOut.add(new OutgoingMessage(OutType.CHAT, tokenList.getToken(2) + " has been set as a new Twitter account", ((ProcTwitter)parent).channel));
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "server.view"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatString() 				{ return ":server"; }
	@Override public String getHelpString() 				{ return "This command shows what the current game's server is set to"; }

}
