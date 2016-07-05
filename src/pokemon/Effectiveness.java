package pokemon;

public class Effectiveness {

	@SuppressWarnings("incomplete-switch")
	public static float getEffectivenessMultiplier(PokemonType attack, PokemonType target) {
		switch (attack) {
		case NORMAL: 
			switch (target) { 
			case GHOST: return 0; 
			case ROCK: case STEEL: return 0.5f; 
			} break;
		case FIGHTING:
			switch (target) {
			case GHOST: return 0;
			case FLYING: case POISON: case BUG: case PSYCHIC: case FAIRY: return 0.5f;
			case NORMAL: case ROCK: case STEEL: case ICE: case DARK: return 2;
			} break;
		case FLYING:
			switch (target) {
			case ROCK: case STEEL: case ELECTRIC: return 0.5f;
			case FIGHTING: case BUG: case GRASS: return 2;
			} break;
		case POISON:
			switch (target) {
			case STEEL: return 0;
			case POISON: case GROUND: case ROCK: case GHOST: return 0.5f;
			case GRASS: case FAIRY: return 2;
			} break;
		case GROUND:
			switch (target) {
			case FLYING: return 0;
			case BUG: case GRASS: return 0.5f;
			case POISON: case ROCK: case STEEL: case FIRE: case ELECTRIC: return 2;
			} break;
		case ROCK:
			switch (target) {
			case FIGHTING: case GROUND: case STEEL: return 0.5f;
			case FLYING: case BUG: case FIRE: case ICE: return 2;
			} break;
		case BUG:
			switch (target) {
			case FIGHTING: case FLYING: case POISON: case GHOST: case STEEL: case FIRE: case FAIRY: return 0.5f;
			case GRASS: case PSYCHIC: case DARK: return 2;
			} break;
		case GHOST:
			switch (target) {
			case NORMAL: return 0;
			case DARK: return 0.5f;
			case GHOST: case PSYCHIC: return 2;
			} break;
		case STEEL:
			switch (target) {
			case STEEL: case FIRE: case WATER: case ELECTRIC: return 0.5f;
			case ROCK: case ICE: case FAIRY: return 2;
			} break;
		case FIRE:
			switch (target) {
			case ROCK: case FIRE: case WATER: case DRAGON: return 0.5f;
			case BUG: case STEEL: case GRASS: case ICE: return 2;
			} break;
		case WATER:
			switch (target) {
			case WATER: case GRASS: case DRAGON: return 0.5f;
			case GROUND: case ROCK: case FIRE: return 2;
			} break;
		case GRASS:
			switch (target) {
			case FLYING: case POISON: case BUG: case STEEL: case FIRE: case GRASS: case DRAGON: return 0.5f;
			case GROUND: case ROCK: case WATER: return 2;
			} break;
		case ELECTRIC:
			switch (target) {
			case GROUND: return 0;
			case GRASS: case ELECTRIC: case DRAGON: return 0.5f;
			case FLYING: case WATER: return 2;
			} break;
		case PSYCHIC:
			switch (target) {
			case DARK: return 0;
			case STEEL: case PSYCHIC: return 0.5f;
			case FIGHTING: case POISON: return 2;
			} break;
		case ICE:
			switch (target) {
			case STEEL: case FIRE: case WATER: case ICE: return 0.5f;
			case FLYING: case GROUND: case GRASS: case DRAGON: return 2;
			} break;
		case DRAGON:
			switch (target) {
			case FAIRY: return 0;
			case STEEL: return 0.5f;
			case DRAGON: return 2;
			} break;
		case DARK:
			switch (target) {
			case FIGHTING: case DARK: case FAIRY: return 0.5f;
			case GHOST: case PSYCHIC: return 2;
			} break;
		case FAIRY:
			switch (target) {
			case POISON: case STEEL: case FIRE: return 0.5f;
			case FIGHTING: case DRAGON: case DARK: return 2;
			} break;
		case NONE:
			return 1;
		}
		return 1;
	}
	
}
