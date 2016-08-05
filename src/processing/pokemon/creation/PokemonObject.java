package processing.pokemon.creation;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;

import logger.Logger;
import processing.pokemon.moves.Learnset;
import processing.pokemon.moves.Move;
import processing.pokemon.moves.MoveType;
import utils.DirectoryUtils;

@Root
public class PokemonObject {
	
	private static long lastUUID = -1;
	private static Serializer serializer = new Persister();

	@Attribute
	private long pokemonUUID;
	
	@Attribute
	private int pokemonID;
	
	@ElementMap(entry="date", key="name", attribute=true, inline=false, required = false)
	private Map<String, Long> trainerList = new HashMap<String, Long>();
	
	@Element
	private int XP;
	
	@Element
	private int HP;
	
	@ElementArray
	private int[] EVs;
	
	@ElementArray
	private int[] IVs;
	
	@Element
	private int ability;
	
	@Element
	private int moveID[];

	@Element
	private int movePP[];
	
	@Element
	private int movePPUps[];
	
	@Element(required=false)
	private String Nickname = "";
	
	@Element
	private boolean isFemale;


	public synchronized MoveType[] getTypes() { return BaseValues.getBaseValues(pokemonID).types; }
	

	public synchronized boolean hasType(MoveType moveType) {
		
		for (MoveType type : BaseValues.getBaseValues(pokemonID).types) {
			if (moveType == type) { return true; }
		}
		return false;
		
	}
	
	public void useMove(int moveIndex) {
		moveIndex = Math.min(Math.max(0, moveIndex), 3);
		movePP[moveIndex]--;
		savePokemon(this);
	}
	
	public int getLevel() { return Math.max((int) Math.pow(this.XP, (1.0f/3.0f)), 1); }
	public long getID() { return pokemonUUID; }
	
	
	public int getBaseHP() {
		int baseHP = (2 * BaseValues.getBaseValues(pokemonID).baseStats[0]) + this.IVs[0] + (this.EVs[0] / 4);
		baseHP = (baseHP * this.getLevel()) / 100;
		return baseHP + this.getLevel() + 10;
	}
	
	public void modifyHP(int modify) {
		this.HP = Math.max(0, Math.min(this.getBaseHP(), this.HP + modify));
		savePokemon(this);
	}
	public int getHP() { 	return this.HP;}
	public int getAtk() { 	return this.getStat(1);}
	public int getDef() { 	return this.getStat(2);}
	public int getSPAtk() { return this.getStat(3);}
	public int getSPDef() { return this.getStat(4);}
	public int getSpd() { 	return this.getStat(5);}
	private int getStat(int statValue) {
		
		int stat = (2 * BaseValues.getBaseValues(pokemonID).baseStats[statValue]) + this.IVs[statValue] + (this.EVs[statValue] / 4);
		stat = (stat * this.getLevel()) / 100;
		return stat + 5;
	}
 	public static synchronized PokemonObject getPokemon(long UUID) {
		System.out.println(UUID);
		File pokefile = getFile(UUID);

		try {
			PokemonObject p = pokefile.exists() ? serializer.read(PokemonObject.class, pokefile) : null;
			System.out.println(p.pokemonID);
			return p;
		} catch (Exception e1) {
			Logger.STACK("Error retrieving pokemon - " + UUID, e1);
		}
		
		return null;

	}
	private static synchronized File getFile(long UUID) {
		
		DirectoryUtils.createDirectories("pokemon/pokemon");
		
		String filename = "pokemon/pokemon/" + UUID + ".xml";
		return new File(filename);
		
	}
	private static synchronized void savePokemon(PokemonObject p) {
		
		File pokefile = getFile(p.pokemonUUID);
		try {
			serializer.write(p, pokefile);
		} catch (Exception e) {
			Logger.STACK("Error writing pokemon - " + p.pokemonUUID, e);
		}
		
	}
	public PokemonObject() {}
	private PokemonObject(int ID, int XP) {
		this.pokemonUUID = getUUID();
		this.pokemonID = ID;
		this.XP = XP;
		
		this.IVs = new int[6];
		this.EVs = new int[6];
		this.HP = this.getBaseHP();
		this.ability = (int) (Math.random() * 2);
		
		//POPULATE MOVEIDS MOVEPPS AND PPUPS
		this.moveID = new int[]{0,0,0,0};
		this.movePP = new int[]{0,0,0,0};
		this.movePPUps = new int[]{0,0,0,0};
		
		List<Move> moveset = Learnset.getLearnset(BaseValues.getBaseValues(ID), getLevel());
		
		for (int i = 0; i < 4 && i < moveset.size(); i++) {
			moveID[i] = moveset.get(moveset.size() - (i + 1)).ID;
			movePP[i] = moveset.get(moveset.size() - (i + 1)).basePP;
		}
		for (int i = 0; i < 6; i++) {
			this.IVs[i] = (int) (Math.random() * 32);
			this.EVs[i] = 0;
		}
		
		this.isFemale = Math.random() * 100 > BaseValues.getBaseValues(ID).percentageMale;
		
	}
	
	private long getUUID() {
		long currentUUID = System.currentTimeMillis();
		if (lastUUID == currentUUID) {
			currentUUID++;
		}
		lastUUID = currentUUID;
		return currentUUID;
	}


	public static synchronized PokemonObject generatePokemon(int generatorID, int pokemonID) {
		PokemonObject poke = null;
		if (generatorID == 47) {
			poke = new PokemonObject(1,(int) (Math.random() * 10000));
		} else if (generatorID == 1) {
			poke = new PokemonObject(pokemonID, 0);
		}
		PokemonObject.savePokemon(poke);
		return poke;
	}
	
	@Override
	public String toString() {
		return BaseValues.getBaseValues(this.pokemonID).name() + " (Level " + this.getLevel() + ")";
	}
	
}