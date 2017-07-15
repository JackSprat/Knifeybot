package processing.command;

import processing.CommandBase;
import utils.CommandParser;


public class CommandView extends CommandBase {

	@Override
	public void execute() {
		String commandStr = ((ProcCommand) parent).getCommand(getToken("@alias").substring(1));
		String[] args = getToken("*").split(" ");
		commandStr = new CommandParser(commandStr).setArgs(args).setChannel(parent.channel).setUsername(getUser())
				.parse();
		sendChatReply(commandStr.trim());
	}

	@Override
	public String getFormatTokens() {
		return "@alias *";
	}

	@Override
	public String getHelpString() {
		return "This command views the response <alias>";
	}

	@Override
	public String getPermissionString() {
		return "command.view";
	}

	@Override
	public boolean isValid() {
		String command = ((ProcCommand) parent).getCommand(getToken("@alias").substring(1));
		if ((command != null) && (command != "")) { return true; }
		return false;
	}
}
