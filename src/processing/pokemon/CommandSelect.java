package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.OutgoingMessage;
import processing.CommandBase;
import processing.ProcBase;
import users.PermissionClass;

public class CommandSelect extends CommandBase {

	public CommandSelect() {
		this.setCommand("kbattle").setKeyword("select").setTokenCount(3,3);
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		try {
			int testInt = Integer.parseInt(tokenList.getToken(2));
			return ((ProcPokemon)parent).choosePokemon(in.getUser(), testInt);
		} catch (NumberFormatException nfe) {
			Logger.STACK("", nfe);
		}
		return false;
	}
	
	@Override public String getPermissionString() 			{ return "battle.select"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kbattle select <party ID>"; }
	@Override public String getHelpString() 				{ return "Select a pokemon to evolve/give items to/learn a move etc."; }
}
