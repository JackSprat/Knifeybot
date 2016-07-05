package pokemon.moves.facets;

import pokemon.Pokemon;

public abstract class Facet {

	FacetType type;
	protected Facet(FacetType type) {
		this.type = type;
	}
	
	public boolean isType(FacetType check) {
		return type == check;
	}
	
	public abstract boolean execute(Pokemon user, Pokemon target);
	
}
