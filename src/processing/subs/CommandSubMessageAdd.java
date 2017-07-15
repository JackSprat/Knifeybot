package processing.subs;

import data.DataManager;
import processing.CommandBase;


public class CommandSubMessageAdd extends CommandBase {

	@Override
	public void execute() {
		int months;
		try {
			months = Integer.parseInt(getToken("months"));
		} catch (NumberFormatException nfe) {
			if (getToken("months").equals("default")) {
				months = -1;
			} else {
				sendChatReply(getUser() + ", " + getToken("months") + " is not a valid points value");
				return;
			}
		}
		DataManager.setSubMessage(parent.channel, months, getToken("+"));
		sendChatReply(getUser() + ", Message for " + (months < 2 ? "new" : months + " month") + " subs set to: "
				+ getToken("+"));
	}

	@Override
	public String getFormatTokens() {
		return "ksub addmessage @months +";
	}

	@Override
	public String getHelpString() {
		return "";
	}

	@Override
	public String getPermissionString() {
		return "sub.addmessage";
	}
}
