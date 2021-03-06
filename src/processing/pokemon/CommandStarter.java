package processing.pokemon;

import processing.CommandBase;
import processing.pokemon.creation.PokemonObject;
import processing.pokemon.creation.PokemonUser;

public class CommandStarter extends CommandBase {

	@Override
	public void execute() {
		
		String idToken = this.getToken("id");
		
		if (PokemonUser.hasUsedStarter(getUser())) { sendChatReply("You have already selected a starter pokemon!"); }
		
		int pokeID = 0;
		
		switch(idToken.toLowerCase()) {
		case "charmander":
			pokeID = 4; break;
		case "bulbasaur":
			pokeID = 1; break;
		case "squirtle":
			pokeID = 7; break;
		default:
			pokeID = 0;
		}
		
		if (pokeID > 1) {
			PokemonObject po = PokemonObject.generatePokemon(1, pokeID);
			PokemonUser.setUsedStarter(getUser());
			PokemonUser.addPokemon(getUser(), po.getID());
			sendChatReply(getUser() + ", you have selected " + po.toString() + " as your starter pokemon!");
		} else {
			sendChatReply(idToken + " is not a valid starter pokemon!");
		}

		
	}

	@Override public String getPermissionString() 			{ return "kpokemon.starter"; }
	@Override public String getFormatTokens() 				{ return "poke starter @id"; }
	@Override public String getHelpString() 				{ return "This command slects a starter pokemon."; }
	
}
