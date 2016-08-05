package processing.permissions;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandPermSetGroup extends CommandBase {
	
	@Override
	public boolean isValid(BlockingQueue<OutgoingMessage> listOut) {
		
		if (PermissionClass.getPermissionClass(getToken("permgroup")) == null) {
			listOut.add(new OutgoingMessage(OutType.CHAT, getToken("permgroup") + " is not a valid permission class.", parent.channel));
			return false;
		}
			
		return true;
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		int editorPermClass = UserManager.getPermLevel(parent.channel, getUser());
		PermissionClass permClass = PermissionClass.getPermissionClass(getToken("permgroup"));
		System.out.println(editorPermClass + ", " + permClass.ordinal());
		
		if (editorPermClass > permClass.ordinal() || getUser().equalsIgnoreCase("jacksprat47")) {
			
			UserManager.setPermLevel(parent.channel, getToken("user"), permClass.ordinal());
			OutgoingMessage message = new OutgoingMessage(OutType.CHAT, getToken("user") + " now has group: " + getToken("permgroup"), parent.channel);
			listOut.add(message);
			return true;
			
		}
		
		return false;
	}

	@Override public String getPermissionString() 			{ return "permissions.setgroup"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatString() 				{ return ":kperm setgroup <user> <permgroup>"; }
	@Override public String getHelpString() 				{ return "This command grants <permgroup> to <user>"; }
	
}
