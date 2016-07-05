package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.CommandBase;
import users.PermissionClass;

public class CommandCheckInventory extends CommandBase {

	public CommandCheckInventory() {
		this.setCommand("kbattle").setKeyword("inv").setTokenCount(3,3);
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		return ((ProcPokemon)parent).showInventory(in.getUser(), tokenList.getToken(2));
		
	}
	
	@Override public String getPermissionString() 			{ return "battle.inventory"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kbattle inv <item>"; }
	@Override public String getHelpString() 				{ return "Shows the number of different items in a user's inventory"; }
}
