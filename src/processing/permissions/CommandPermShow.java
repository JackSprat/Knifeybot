package processing.permissions;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandPermShow extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		String user = getUser();
		String specifiedUser = getToken("user");
		
		if (specifiedUser != null && specifiedUser != "") user = specifiedUser;
		
		for (PermissionClass p : PermissionClass.values()){
			if (p.ordinal() == UserManager.getPermLevel(parent.channel, user)) {
				OutgoingMessage message = new OutgoingMessage(OutType.CHAT, user + " has group: " + p.toString(), parent.channel);
				listOut.add(message);
				return true;
			}
		}
		
		return false;

	}

	@Override public String getPermissionString() 			{ return "permissions.setgroup"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "kperm show #user"; }
	@Override public String getHelpString() 				{ return "This command shows permissions, optionally for [user]"; }
	
}
