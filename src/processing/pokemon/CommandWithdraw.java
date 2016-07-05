package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.OutgoingMessage;
import pokemon.PokemonUser;
import processing.CommandBase;
import processing.ProcBase;
import users.PermissionClass;

public class CommandWithdraw extends CommandBase {

	public CommandWithdraw() {
		this.setCommand("kbattle").setKeyword("withdraw").setTokenCount(3,3);
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		try {
			int testInt = Integer.parseInt(tokenList.getToken(2));
			return PokemonUser.getUser(in.getUser()).withdrawPokemon(testInt);
		} catch (NumberFormatException nfe) {
			Logger.STACK("", nfe);
		}
		return false;
		
	}
	
	@Override public String getPermissionString() 			{ return "battle.withdraw"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kbattle withdraw <pcID>"; }
	@Override public String getHelpString() 				{ return "Withdraws a pokemon from pcID in the PC to your party"; }
}
