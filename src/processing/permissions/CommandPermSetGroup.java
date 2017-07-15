package processing.permissions;

import data.DataManager;
import logger.Logger;
import processing.CommandBase;
import users.PermissionClass;

public class CommandPermSetGroup extends CommandBase {
	
	@Override
	public boolean isValid() {
		
		if (PermissionClass.getPermissionClass(getToken("permgroup")) == null) {
			sendChatReply(getToken("permgroup") + " is not a valid permission class.");
			return false;
		}
			
		return true;
	}

	@Override
	public void execute() {
		
		PermissionClass editorPermClass = DataManager.getUserLevel(parent.channel, getUser());
		PermissionClass targetPermClass = DataManager.getUserLevel(parent.channel, getToken("user"));
		PermissionClass permClass = PermissionClass.getPermissionClass(getToken("permgroup"));
		
		if (editorPermClass.ordinal() > permClass.ordinal() && editorPermClass.ordinal() > targetPermClass.ordinal()) {
			Logger.INFO("Permission set: " + getUser() + " (" + editorPermClass + ") set " + getToken("user") + " (" + targetPermClass + ") to: " + permClass);
			DataManager.setUserLevel(parent.channel, getToken("user"), permClass);
			sendChatReply(getToken("user") + " now has group: " + getToken("permgroup"));
			
		}

	}

	@Override public String getPermissionString() 			{ return "permissions.setgroup"; }
	@Override public String getFormatTokens() 				{ return "kperm setgroup @user @permgroup"; }
	@Override public String getHelpString() 				{ return "This command grants <permgroup> to <user>"; }
	
}
