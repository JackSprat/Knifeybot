package processing.permissions;

import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandPermGrant extends CommandBase {

	@Override
	public void execute() {
		
		int editorPermClass = UserManager.getPermLevel(parent.channel, getUser());
		PermissionClass permClass = PermissionClass.getPermissionClass(getToken("permission"));
		
		if (editorPermClass > permClass.ordinal()) {
			
			UserManager.addPermission(parent.channel, getToken("user"), getToken("permission"), true);
			sendReply(getToken("user") + " granted permission: " + getToken("permission"));
			
		}
		
	}

	@Override public String getPermissionString() 			{ return "permissions.grant"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kperm grant @user @permission"; }
	@Override public String getHelpString() 				{ return "This command grants <permission> to <user> - see :khelp for permissions"; }
	
}
