package pokemon.moves.facets;

import pokemon.Pokemon;

public class ContactFacet extends Facet {

	public ContactFacet() {
		super(FacetType.CONTACT);
	}

	@Override
	public boolean execute(Pokemon user, Pokemon target) {
		// TODO Implement contact routine
		return true;
	}

}
