package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.OutgoingMessage;
import processing.CommandBase;
import processing.ProcBase;
import users.PermissionClass;

public class CommandMove extends CommandBase {

	public CommandMove() {
		this.setCommand("kbattle").setKeyword("move").setTokenCount(3,3);
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		try {
			return ((ProcPokemon)parent).chooseMove(in.getUser(), tokenList.getToken(2));
		} catch (NumberFormatException nfe) {
			Logger.STACK("", nfe);
		}
		return false;
	}
	
	@Override public String getPermissionString() 			{ return "battle.select"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kbattle select <partyID>"; }
	@Override public String getHelpString() 				{ return "Select a pokemon to evolve/give items to/learn a move etc."; }
}
