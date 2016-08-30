package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import processing.pokemon.creation.PokemonObject;
import processing.pokemon.creation.PokemonUser;
import users.PermissionClass;

public class CommandViewParty extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		long[] party = PokemonUser.getParty(getUser());
		
		String message = getUser() + "'s Party: ";
		
		for (long id : party) {
			PokemonObject po = PokemonObject.getPokemon(id);
			message += po == null ? "ERROR" : po.toString() + ", ";
		}
		
		return listOut.add(new OutgoingMessage(OutType.CHAT, message, parent.channel));

		
	}

	@Override public String getPermissionString() 			{ return "kpokemon.viewparty"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "poke party #user"; }
	@Override public String getHelpString() 				{ return "This command views the user's party."; }
	
}
