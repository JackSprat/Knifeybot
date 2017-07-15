package processing.functions;

import data.DataManager;
import processing.CommandBase;


public class CommandSub extends CommandBase {

	@Override
	public void execute() {
		String user = getUser();
		String specifiedUser = getToken("user");
		if ((specifiedUser != null) && (specifiedUser != "")) {
			user = specifiedUser;
		}
		int subLength = DataManager.getSubLength(parent.channel, user);
		boolean isSubbed = subLength > 0;
		String message = "@" + getUser() + ", "
				+ (isSubbed ? user + " hase been subbed for " + subLength + " month" + (subLength == 1 ? "!" : "s!")
						: user + " is not subbed to this channel");
		sendChatReply(message);
	}

	@Override
	public String getFormatTokens() {
		return "ksub length #user";
	}

	@Override
	public String getHelpString() {
		return "Use to check how long you're subbed for";
	}

	@Override
	public String getPermissionString() {
		return "functions.sub";
	}
}
