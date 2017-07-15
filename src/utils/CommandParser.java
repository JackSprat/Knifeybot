package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import state.ChannelState;


public class CommandParser {

	private String		commandStr;
	private String[]	args;
	private String		username;
	private String		channel;
	private int			submonths	= 0;

	public CommandParser(String commandStr) {
		this.commandStr = commandStr;
	}

	public String parse() {
		while (true) {
			// Find most nested pair
			Pattern p = Pattern.compile("\\{[^{}]*\\}");
			Matcher m = p.matcher(commandStr);
			if (m.find()) {
				String parsedString = parseStringForCommands(commandStr.substring(m.start() + 1, m.end() - 1));
				commandStr = commandStr.substring(0, m.start()) + parsedString + commandStr.substring(m.end());
			} else {
				break;
			}
		}
		return commandStr;
	}

	private String parseStringForCommands(String s) {
		String[] commandTokens = s.split(" ");
		if (commandTokens[0].trim().equalsIgnoreCase("me")) {
			if (!username.equals("")) { return username; }
			return "Knifeybot";
		}
		if (commandTokens[0].trim().startsWith("arg") && (args != null)) {
			String argIntString = commandTokens[0].trim().substring(3);
			if (argIntString == null) { return "(Error here: " + commandTokens[0].trim() + " not valid arg)"; }
			if (argIntString.equalsIgnoreCase("s")) { return String.join(" ", args); }
			try {
				int i = Integer.parseInt(argIntString);
				if ((i > 0) && (i <= args.length)) { return args[i - 1]; }
				return "(Error here: " + s + " not valid arg)";
			} catch (NumberFormatException nfe) {
				return "(Error here: " + s + " not valid arg)";
			}
		}
		if (commandTokens[0].trim().startsWith("currentgame") && (channel != null)) {
			String channelToGet = commandTokens.length > 1 ? commandTokens[1].trim() : channel;
			String game = ChannelState.getCurrentGame(channelToGet);
			return game.equals("") ? "(Channel not found)" : game;
		}
		if (commandTokens[0].trim().equalsIgnoreCase("condition:live") && (channel != null)) {
			String[] commandGroups = s.split(" ", 2);
			if (commandGroups.length < 3) { return ""; }
			commandGroups = commandGroups[2].split(":", 2);
			return ChannelState.isStreamLive(channel) ? commandGroups[0] : commandGroups[1];
		}
		if (commandTokens[0].trim().equalsIgnoreCase("months")) { return submonths + ""; }
		return s;
	}

	public CommandParser setArgs(String[] args) {
		this.args = args;
		return this;
	}

	public CommandParser setChannel(String channel) {
		this.channel = channel;
		return this;
	}

	public CommandParser setSubMonths(int months) {
		submonths = months;
		return this;
	}

	public CommandParser setUsername(String username) {
		this.username = username;
		return this;
	}
}
