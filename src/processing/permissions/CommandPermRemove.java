package processing.permissions;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandPermRemove extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		int editorPermClass = UserManager.getPermLevel(parent.channel, getUser());
		PermissionClass permClass = PermissionClass.getPermissionClass(getToken("permission"));
		
		if (editorPermClass > permClass.ordinal()) {
			
			UserManager.addPermission(parent.channel, getToken("user"), getToken("permission"), false);
			OutgoingMessage message = new OutgoingMessage(OutType.CHAT, getToken("user") + " removed permission: " + getToken("permission"), parent.channel);
			listOut.add(message);
			return true;
			
		}
		
		return false;
		
	}

	@Override public String getPermissionString() 			{ return "permissions.remove"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kperm remove @user @permission"; }
	@Override public String getHelpString() 				{ return "This command removes <permission> from <user> - see :khelp for permissions"; }
	
}
