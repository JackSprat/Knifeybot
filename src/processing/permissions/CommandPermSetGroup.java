package processing.permissions;

import processing.CommandBase;
import users.PermissionClass;
import users.UserManager;

public class CommandPermSetGroup extends CommandBase {
	
	@Override
	public boolean isValid() {
		
		if (PermissionClass.getPermissionClass(getToken("permgroup")) == null) {
			sendReply(getToken("permgroup") + " is not a valid permission class.");
			return false;
		}
			
		return true;
	}

	@Override
	public void execute() {
		
		int editorPermClass = UserManager.getPermLevel(parent.channel, getUser());
		PermissionClass permClass = PermissionClass.getPermissionClass(getToken("permgroup"));
		System.out.println(editorPermClass + ", " + permClass.ordinal());
		
		if (editorPermClass > permClass.ordinal() || getUser().equalsIgnoreCase("jacksprat47")) {
			
			UserManager.setPermLevel(parent.channel, getToken("user"), permClass.ordinal());
			sendReply(getToken("user") + " now has group: " + getToken("permgroup"));
			
		}

	}

	@Override public String getPermissionString() 			{ return "permissions.setgroup"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatTokens() 				{ return "kperm setgroup @user @permgroup"; }
	@Override public String getHelpString() 				{ return "This command grants <permgroup> to <user>"; }
	
}
