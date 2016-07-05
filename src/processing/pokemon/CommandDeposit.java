package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.OutgoingMessage;
import pokemon.PokemonUser;
import processing.CommandBase;
import processing.ProcBase;
import users.PermissionClass;

public class CommandDeposit extends CommandBase {

	public CommandDeposit() {
		this.setCommand("kbattle").setKeyword("deposit").setTokenCount(3,3);
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		try {
			int testInt = Integer.parseInt(tokenList.getToken(2));
			return PokemonUser.getUser(in.getUser()).depositPokemon(testInt);
		} catch (NumberFormatException nfe) {
			Logger.STACK("", nfe);
		}
		return false;
		
	}
	
	@Override public String getPermissionString() 			{ return "battle.deposit"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kbattle deposit <partyID>"; }
	@Override public String getHelpString() 				{ return "If there is a pokemon encountered, battle it"; }
}
