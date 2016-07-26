package processing.pokemon.moves;

public enum Move {

	TACKLE(33, 35, 35, 95, MoveType.NORMAL, MoveTarget.ANY_ADJACENT, "A physical attack in which the user charges, full body, into the foe.", "Tackle");
	
	public final int ID, basePP, power, accuracy;
	public final MoveType type;
	public final MoveTarget target;
	public final String description, name;
	
	private Move(int ID, int basePP, int power, int accuracy, MoveType type, MoveTarget target, String description, String name) {
        this.ID = ID;
        this.basePP = basePP;
        this.power = power;
        this.accuracy = accuracy;
        this.type = type;
        this.target = target;
        this.description = description;
        this.name = name;
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