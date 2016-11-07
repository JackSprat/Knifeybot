package processing.permissions;

import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandPermRemove extends CommandBase {

	@Override
	public void execute() {
		
		int editorPermClass = UserManager.getPermLevel(parent.channel, getUser());
		PermissionClass permClass = PermissionClass.getPermissionClass(getToken("permission"));
		
		if (editorPermClass > permClass.ordinal()) {
			
			UserManager.addPermission(parent.channel, getToken("user"), getToken("permission"), false);
			sendReply(getToken("user") + " removed permission: " + getToken("permission"));
			
		}
		
	}

	@Override public String getPermissionString() 			{ return "permissions.remove"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kperm remove @user @permission"; }
	@Override public String getHelpString() 				{ return "This command removes <permission> from <user> - see :khelp for permissions"; }
	
}
