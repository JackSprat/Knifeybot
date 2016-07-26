package processing.pokemon;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;

import logger.Logger;
import processing.pokemon.moves.MoveType;
import utils.DirectoryUtils;

@Root
public class PokemonObject {
	
	private static Serializer serializer = new Persister();

	@Attribute
	private long pokemonUUID;
	
	@Attribute
	private int pokemonID;
	
	@ElementMap(entry="date", key="name", attribute=true, inline=false, required = true)
	private Map<String, Long> trainerList = new HashMap<String, Long>();
	
	@Element
	private int XP;
	
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
	
	@Element
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
	
	public int getLevel() { return (int) Math.pow(this.XP, (1.0f/3.0f)); }

	public int getHP() {
		int HP = (2 * BaseValues.getBaseValues(pokemonID).baseStats[0]) + this.IVs[0] + (this.EVs[0] / 4);
		HP = (HP * this.getLevel()) / 100;
		return HP + this.getLevel() + 10;
	}
	public int getAtk() { 	return this.getStat(1);}
	public int getDef() { 	return this.getStat(2);}
	public int getSPAtk() { return this.getStat(3);}
	public int getSPDef() { return this.getStat(4);}
	public int getSpd() { 	return this.getStat(5);}
	private int getStat(int statValue) {
		
		int stat = (2 * BaseValues.getBaseValues(pokemonID).baseStats[statValue]) + this.IVs[statValue] + (this.EVs[statValue] / 4);
		stat = (stat * this.getLevel()) / 100;
		System.out.println(statValue + ": " + BaseValues.getBaseValues(pokemonID).baseStats[statValue] + "," + this.IVs[statValue]);
		return stat + 5;
	}
 	public static synchronized PokemonObject getPokemon(long UUID) {
		
		File pokefile = getFile(UUID);

		try {
			PokemonObject p = pokefile.exists() ? serializer.read(PokemonObject.class, pokefile) : null;
			return p;
		} catch (Exception e1) {
			Logger.STACK("Error retrieving pokemon - " + UUID, e1);
		}
		
		return null;

	}
	private static synchronized File getFile(long UUID) {
		
		DirectoryUtils.createDirectories("pokemon");
		
		String filename = "pokemon/" + UUID + ".xml";
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
	
	private PokemonObject(int ID, int XP) {
		this.pokemonID = ID;
		this.XP = XP;
		this.IVs = new int[6];
		this.EVs = new int[6];
		this.ability = (int) (Math.random() * 2);
		
		//POPULATE MOVEIDS MOVEPPS AND PPUPS
		this.moveID = new int[]{0,0,0,0};
		this.movePP = new int[]{0,0,0,0};
		this.movePPUps = new int[]{0,0,0,0};
		
		for (int i = 0; i < 6; i++) {
			this.IVs[i] = (int) (Math.random() * 32);
			this.EVs[i] = 0;
		}
		
	}
	
	public static synchronized PokemonObject generatePokemon(int generatorID) {
		PokemonObject poke = null;
		if (generatorID == 47) {
			poke = new PokemonObject(1,(int) (1000000));
		}
		PokemonObject.savePokemon(poke);
		return poke;
	}
	
	@Override
	public String toString() {
		return BaseValues.getBaseValues(this.pokemonID).name() + " (Level " + this.getLevel() + ")";
	}
	
}