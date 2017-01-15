package processing.pokemon;

import processing.CommandBase;
import processing.pokemon.creation.PokemonObject;
import processing.pokemon.creation.PokemonUser;
import processing.pokemon.moves.Move;
import processing.pokemon.moves.MoveType;

public class CommandViewPokemon extends CommandBase {
	
	@Override
	public void execute() {
		
		
		long[] party = PokemonUser.getParty(getUser());
		//long[] pc = PokemonUser.getPC(getUser(), -1);
		
		//String target = getToken("pc|party");
		
		int id = Integer.parseInt(getToken("index"));
		long uuid = 0;
		
		if (party.length > id) {
			uuid = party[id];
		} else {
			return;
		}
		
		PokemonObject po = PokemonObject.getPokemon(uuid);
		
		String message = "";
		
		if (po == null) {
			message = "Slot " + getToken("index") + " not available";
		} else {
			message += po.getNickname().equals("") ? po.getName() + " " : po.getNickname() + " (" + po.getName() + ")";
			message += " - Types: ";
			for (MoveType type : po.getTypes()) {
				message += type.toString();
			}
			message += " - Stats: ";
			message += po.getBaseHP() + "/" + po.getAtk() + "/" + po.getDef() + "/" + po.getSPAtk() + "/" + po.getSPDef() + "/" + po.getSpd() + " - HP: " + po.getHP() + "/" + po.getBaseHP();
			int[] moves = po.getMoves();
			int[] movePPs = po.getMovesPP();
			message += " - Moves: ";
			for (int i = 0; i < 3; i++) {
				if (moves[i] != 0) {
					Move m = Move.getMove(moves[i]);
					message += m.getName() + " " + movePPs[i] + "/" + m.getMoveBasePP() + ", ";
				}
			}
		}
		
		sendReply(message);

		
	}

	@Override public String getPermissionString() 			{ return "kpokemon.viewpokemon"; }
	@Override public String getFormatTokens() 				{ return "poke pc|party #index"; }
	@Override public String getHelpString() 				{ return "This command views the user's pokemon."; }
	
}
