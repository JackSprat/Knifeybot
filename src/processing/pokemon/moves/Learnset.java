package processing.pokemon.moves;

import java.util.HashMap;
import java.util.Map;

import processing.pokemon.creation.BaseValues;

public class Learnset {

	public static Map<Move, Integer> getLearnset(BaseValues pokemon) {
		
		Map<Move, Integer> moveset = new HashMap<Move, Integer>();
		
		switch (pokemon) {
		case BULBASAUR:
			moveset.put(Move.TACKLE, 1);
			break;
		case CHARMANDER:
			moveset.put(Move.TACKLE, 1);
			moveset.put(Move.GROWL, 1);
			break;
		case SQUIRTLE:
			moveset.put(Move.TACKLE, 1);
			break;
		default:
			break;
		}
		
		return moveset;
		
	}
	
}