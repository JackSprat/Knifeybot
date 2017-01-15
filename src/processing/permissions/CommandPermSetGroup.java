package processing.permissions;

import data.DataManager;
import processing.CommandBase;
import users.PermissionClass;

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
		
		PermissionClass editorPermClass = DataManager.getUserLevel(parent.channel, getUser());
		PermissionClass targetPermClass = DataManager.getUserLevel(parent.channel, getToken("user"));
		PermissionClass permClass = PermissionClass.getPermissionClass(getToken("permgroup"));
		System.out.println(editorPermClass + ", " + permClass.ordinal());
		
		if (editorPermClass.ordinal() > permClass.ordinal() && editorPermClass.ordinal() > targetPermClass.ordinal()) {
			
			DataManager.setUserLevel(parent.channel, getToken("user"), permClass);
			sendReply(getToken("user") + " now has group: " + getToken("permgroup"));
			
		}

	}

	@Override public String getPermissionString() 			{ return "permissions.setgroup"; }
	@Override public String getFormatTokens() 				{ return "kperm setgroup @user @permgroup"; }
	@Override public String getHelpString() 				{ return "This command grants <permgroup> to <user>"; }
	
}
