package processing.pokemon;

import processing.pokemon.moves.MoveType;

public enum BaseValues {

	BULBASAUR(1, new MoveType[]{MoveType.GRASS, MoveType.POISON}, null, 87.5f, 0.059f, null, 5000, 0.7f, 6.9f, 64, new int[]{0,0,0,1,0,0}, new int[]{45,49,49,65,65,45});
	
	public final int ID, hatchTime, baseXPYield;
	public final int[] EVYield, baseStats;
	
	public final float percentageMale, catchRate, height, weight;
	public final EggGroup[] eggGroups;
	public final MoveType[] types;
	public final Ability[] abilities;
	
	private BaseValues(int ID, MoveType[] types, Ability[] abilities, 
			float percentageMale, float catchRate, EggGroup[] eggGroups, 
			int hatchTime, float height, float weight,
			int baseXPYield, int[] EVYield, int[] baseStats) {
        this.ID = ID;
        this.types = types;
        this.abilities = abilities;
        this.percentageMale = percentageMale;
        this.catchRate = catchRate;
        this.eggGroups = eggGroups;
        this.hatchTime = hatchTime;
        this.height = height;
        this.weight = weight;
        this.baseXPYield = baseXPYield;
        this.EVYield = EVYield;
        this.baseStats = baseStats;
    }
	
	public static BaseValues getBaseValues(int index) {
		switch(index) {
			case 1: return BULBASAUR;
		default:
			break;
		}
		return null;
	}
	
}