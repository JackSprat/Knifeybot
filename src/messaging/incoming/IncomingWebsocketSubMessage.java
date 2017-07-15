package messaging.incoming;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logger.Logger;


public class IncomingWebsocketSubMessage extends IncomingWebsocketMessage {

	private String	user	= "";
	private int		months	= 0;
	private String	subPlan	= "";

	public IncomingWebsocketSubMessage(String message) {
		Logger.INFO(message);
		originalString = message;
		user = findString("display_name");
		subPlan = findString("sub_plan");
		months = findInt("months");
		channel = findString("channel_name");
		Logger.INFO(user + ", " + months + ", " + subPlan);
	}

	private int findInt(String key) {
		int returnVal = 0;
		Pattern pattern = Pattern.compile(key + "\\\\\":([0-9]+),");
		Matcher matcher = pattern.matcher(originalString);
		if (matcher.find()) {
			returnVal = Integer.parseInt(matcher.group(1));
		}
		return returnVal;
	}

	private String findString(String key) {
		String returnVal = "";
		Pattern pattern = Pattern.compile(key + "\\\\\":\\\\\"([^\\\\]*)\\\\");
		Matcher matcher = pattern.matcher(originalString);
		if (matcher.find()) {
			returnVal = matcher.group(1);
		}
		return returnVal;
	}

	@Override
	public String getChannel() {
		return channel;
	}

	public int getMonths() {
		return months;
	}

	public String getSubPlan() {
		return subPlan;
	}

	@Override
	public String getTypeString() {
		return "WSSSUB";
	}

	public String getUser() {
		return user;
	}
}
