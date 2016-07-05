package pokemon.moves;

import java.util.ArrayList;
import java.util.List;

import pokemon.PokemonType;
import pokemon.moves.facets.ContactFacet;
import pokemon.moves.facets.Facet;
import pokemon.moves.facets.FacetType;

public enum BaseValues {

	TACKLE(33, "Tackle", PokemonType.NORMAL, Category.PHYSICAL, 35, 50, 1f, new ContactFacet());
	
	private int moveID, basePP, basePower;
	private String name;
	private PokemonType type;
	private Category category;
	private float baseAccuracy;
	private List<Facet> facets = new ArrayList<Facet>();
	
	BaseValues(int ID, String name, PokemonType type, Category category, int basePP, int basePower, float baseAccuracy, Facet... facets) {
		this.moveID = ID;
		this.name = name;
		this.type = type;
		this.category = category;
		this.basePP = basePP;
		this.basePower = basePower;
		this.baseAccuracy = baseAccuracy;
		for (Facet f : facets) {
			this.facets.add(f);
		}
	}
	
	public List<Facet> getFacets() { return facets;	}
	public boolean hasFacet(FacetType facet) { 
		for (Facet f : facets) {
			if (f.isType(facet)) return true;
		}
		return false;
	}
	public Facet getFacet(FacetType facet) { 
		for (Facet f : facets) {
			if (f.isType(facet)) return f;
		}
		return null;
	}
	public boolean isCategory(Category cat) { return this.category == cat; }
	public int getBasePower() { return basePower; }
	public PokemonType getType() { return type; }
	public int getBasePP() { return basePP; }
	
}
