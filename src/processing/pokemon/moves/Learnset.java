package processing.pokemon.moves;

import java.util.ArrayList;
import java.util.List;

import processing.pokemon.creation.BaseValues;

public class Learnset {

	public static List<Move> getLearnset(BaseValues pokemon, int level) {
		
		List<Move> moveset = new ArrayList<Move>();
		
		switch (pokemon) {
		case BULBASAUR:
			moveset.add(Move.TACKLE);
			break;
		case CHARMANDER:
			moveset.add(Move.TACKLE);
			moveset.add(Move.GROWL);
			break;
		case SQUIRTLE:
			moveset.add(Move.TACKLE);
			break;
		default:
			break;
		}
		
		return moveset;
		
	}
	
}