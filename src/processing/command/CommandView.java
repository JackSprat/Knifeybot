package processing.command;

import processing.CommandBase;
import users.PermissionClass;

public class CommandView extends CommandBase {

	@Override
	public boolean isValid() {
		
		String command = ((ProcCommand)parent).getCommand(getToken("@alias").substring(1));
		
		if (command != null && command != "") 	return true;		
												return false;
		
	}

	@Override
	public void execute() {
		
		String commandStr = ((ProcCommand)parent).getCommand(getToken("@alias").substring(1));
		
		String[] args = getToken("*").split(" ");
		String[] commandSplitStr = commandStr.split("\\|");
		System.out.println(commandSplitStr.length);
		for (int i = 0; i < commandSplitStr.length; i++) {
			
			System.out.println(commandSplitStr[i]);
			if (commandSplitStr[i].contains("%arg")) {
				for (int argCounter = 0; argCounter < args.length; argCounter++) {
					System.out.println(("Replacing %arg" + (argCounter+1)) + " with " + args[argCounter]);
					commandSplitStr[i] = commandSplitStr[i].replace("%arg" + (argCounter+1), args[argCounter]);
				}
				if (commandSplitStr[i].contains("%arg")) {
					commandSplitStr[i] = "";
				}
			}
		}
		
		commandStr = String.join("", commandSplitStr);
		
		commandStr = commandStr.replaceAll("%me", getUser());
		
		sendReply(commandStr.trim());
		
	}

	@Override public String getPermissionString() 			{ return "command.view"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "@alias *"; }
	@Override public String getHelpString() 				{ return "This command views the response <alias>"; }
	
}
