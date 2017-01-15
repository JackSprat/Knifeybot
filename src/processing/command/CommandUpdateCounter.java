package processing.command;

import data.DataManager;
import processing.CommandBase;
import state.ChannelState;

public class CommandUpdateCounter extends CommandBase {

	@Override
	public boolean isValid() {
		
		String alias = getToken("@alias").substring(1);
		if (alias.endsWith("+") || alias.endsWith("-") || alias.endsWith("=")) {
			alias = alias.substring(0, alias.length()-1);
		} else {
			return false;
		}
		String command = ((ProcCommand)parent).getCommand(alias);
		
		if (command != null && command != "") 	return true;		
												return false;
		
	}

	@Override
	public void execute() {
		
		String alias = getToken("@alias");
		int counter = getCounter(alias.substring(1, alias.length()-1));
		
		int amount = 1;
		
		String amountToken = getToken("#amount");
		
		if (!amountToken.equals("")) {
			try {
				int i = Integer.parseInt(amountToken);
				amount = i;
			} catch (@SuppressWarnings("unused") NumberFormatException nfe) {
				amount = 0;
			}
		}
		
		if (alias.endsWith("+")) {
			((ProcCommand)parent).setCounter(alias.substring(1, alias.length()-1), counter + amount);
		} else if (alias.endsWith("-")) {
			((ProcCommand)parent).setCounter(alias.substring(1, alias.length()-1), counter - amount);
		} else if (alias.endsWith("=")) {
			((ProcCommand)parent).setCounter(alias.substring(1, alias.length()-1), amount);
		}
		
	}
	
	private int getCounter(String alias) {
		String game = ChannelState.getCurrentGame(parent.channel);
		
		int counter = DataManager.getCounter(parent.channel, alias, game);
		
		return counter;
	}

	@Override public String getPermissionString() 			{ return "command.counter"; }
	@Override public String getFormatTokens() 				{ return "@alias #amount"; }
	@Override public String getHelpString() 				{ return "This command updates <alias> counter, using :alias+ <value>, :alias- <value> or :alias= <value>"; }
	
}
