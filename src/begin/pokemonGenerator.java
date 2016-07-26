package begin;

import processing.pokemon.PokemonObject;
import processing.pokemon.moves.Move;
import processing.pokemon.moves.MoveApplicator;

public class pokemonGenerator {
	public static void main(String[] args) {

		for (int i = 0; i < 1; i++) {
			PokemonObject p1 = PokemonObject.generatePokemon(47);
			PokemonObject p2 = PokemonObject.generatePokemon(47);
			float dmg = MoveApplicator.applyMove(p1, p2, Move.TACKLE, null);
			
		}
	}
}
