package processing.pokemon.moves;

public enum Move {

	TACKLE(33, 35, 35, 95, MoveType.NORMAL, MoveTarget.ONE_ADJACENT_ANY, "A physical attack in which the user charges, full body, into the foe.", "Tackle", new MoveFacet[] {MoveFacet.AFFECTED_BY_PROTECT, MoveFacet.AFFECTED_BY_KINGS_ROCK, MoveFacet.MAKES_CONTACT, MoveFacet.DEALS_DAMAGE}),
	SCRATCH(10, 35, 35, 100, MoveType.NORMAL, MoveTarget.ONE_ADJACENT_ANY, "Hard, pointed, and sharp claws rake the foe.", "Scratch", new MoveFacet[] {MoveFacet.AFFECTED_BY_PROTECT, MoveFacet.AFFECTED_BY_KINGS_ROCK, MoveFacet.MAKES_CONTACT, MoveFacet.DEALS_DAMAGE}),
	GROWL(45, 40, 0, 100, MoveType.NORMAL, MoveTarget.ALL_ADJACENT_ENEMY, "The user growls in a cute way, making the foe lower its Attack. stat.", "Growl", new MoveFacet[] {MoveFacet.AFFECTED_BY_PROTECT, MoveFacet.IS_SOUND_BASED, MoveFacet.AFFECTED_BY_KINGS_ROCK, MoveFacet.AFFECTED_BY_MAGIC_COAT, MoveFacet.APPLIES_STATUS_EFFECT});
	
	public final int ID, basePP, power, accuracy;
	public final MoveType type;
	public final MoveTarget target;
	public final String description, name;
	public final MoveFacet[] facets;
	
	private Move(int ID, int basePP, int power, int accuracy, MoveType type, MoveTarget target, String description, String name, MoveFacet[] facets) {
        this.ID = ID;
        this.basePP = basePP;
        this.power = power;
        this.accuracy = accuracy;
        this.type = type;
        this.target = target;
        this.description = description;
        this.name = name;
        this.facets = facets;
    }
	
	public boolean hasFacet(MoveFacet facet) {
		if (facets == null) { return false; }
		for (int i = 0; i < facets.length; i++) { if (facets[i].equals(facet)) return true; }
		return false;
	}

	public MoveType getMoveType() { return type; }
	public int getMoveBasePP() { return basePP; }
	public int getMovePower() { return power; }
	public int getMoveAccuracy() { return accuracy; }
	public MoveTarget getMoveTarget() { return target;}
	public String getDescription() { return description; }
	public String getName() { return name; }
	
	@Override
	public String toString() { return this.name + ": " + description; }
	
}