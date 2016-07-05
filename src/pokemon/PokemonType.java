package pokemon;

public enum PokemonType {

	NONE,
	
	NORMAL,
	GRASS,
	POISON,
	FIRE,
	FLYING,
	
	WATER,
	BUG,
	ELECTRIC,
	GROUND,
	FAIRY,
	
	FIGHTING,
	ROCK,
	STEEL,
	PSYCHIC,
	ICE,
	
	GHOST,
	DRAGON,
	DARK;
	
	public static PokemonType getType(String s) {
		if (s == null || s.equals("")) return NONE;
		for (PokemonType pt : PokemonType.values()) {
			if (pt.name().equalsIgnoreCase(s)) {
				return pt;
			}
		}
		return NONE;
	}
}