package pokemon;
import java.util.Set;
import java.util.UUID;

import org.simpleframework.xml.*;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

import logger.Logger;
import pokemon.moves.Move;
import pokemon.status.StatusManager;
import processing.pokemon.Attrib;

public class Pokemon {

	private static RandomIntegerDistribution idGenerator = new RandomIntegerDistribution();
	private static ObjectContainer database = Db4o.openFile("data/pokemon.db");
	
	@Element
	private String nickname = "";
	private int level = 0, IVs[] = new int[6];
	private int EVs[] = new int[6], XP = 0, HP = 0, pokedexID = 0;
	private UUID uniqueID;
	public StatusManager statuses = new StatusManager();
	private boolean isShiny;
	private Move[] moveList = new Move[4];
	
	public Pokemon() {
		if (idGenerator.isEmpty()) generateProbabilities();
		
		int pokedexId = idGenerator.getRandomValue();
		this.pokedexID = pokedexId;
		this.isShiny = Math.random() * 4096 < 1;
		this.level = ((int)100/BaseValues.getProbability(pokedexId)) + (int)(Math.random() * 5);
		for (int i = 0; i < 5; i++) {
			this.IVs[i] = (int) (Math.random() * 32);
		}
		this.uniqueID = UUID.randomUUID();
		this.moveList[0] = new Move(pokemon.moves.BaseValues.TACKLE);
		database.store(this);
		database.commit();
	}
	
	public static Pokemon getPokemon(UUID id) {
		ObjectSet<Pokemon> pokemon = database.query(new Predicate<Pokemon>() {
		    public boolean match(Pokemon p) {
		        return p.uniqueID.equals(id);
		    }
		});
		
		if (pokemon != null && pokemon.size() > 0) {
			return pokemon.get(0);
		}
		return null;
	}
	
	public void addHP(int hp) { 
		Logger.DEBUG("Adding HP to " + this.getName() + ", " + (this.getCurrentHP() + hp));
		this.HP = Math.min(this.getMaxHP(), this.getCurrentHP() + hp);
		database.store(this);
		database.commit();
	}
	public void damageHP(int hp) { 
		Logger.DEBUG("Removing HP from " + this.getName() + ", " + (this.getCurrentHP() - hp));
		this.HP = Math.max(0, this.getCurrentHP() - hp);
		database.store(this);
		database.commit();
	}
	
	public UUID getUUID() { return uniqueID; }
	public boolean isShiny() { return isShiny; }
	//public boolean hasStatus(PokemonStatus status) {};
	public void addXP(int xp) {
		this.XP += xp;
	}
	
	public int getCurrentHP() {
		return HP;
	}
	public int getID() { return pokedexID; }
	public String getName() { return BaseValues.getName(this.pokedexID); }
	public int getMaxHP() {
		
		int Base = BaseValues.getHP(this.pokedexID);
				
		int maxHP = (int) (((2 * Base) + IVs[Attrib.HP] + (int)(EVs[Attrib.HP]/4f)) * level / 100f);
		
		return maxHP + level + 10;
		
	}
	
	public int getAttack() { return ((int) (((2 * BaseValues.getAttack(this.pokedexID)) + IVs[Attrib.ATT] + (int)(EVs[Attrib.ATT]/4f)) * level / 100f)) + 5; }
	public int getDefense() { return ((int) (((2 * BaseValues.getDefense(this.pokedexID)) + IVs[Attrib.DEF] + (int)(EVs[Attrib.DEF]/4f)) * level / 100f)) + 5; }
	public int getSPAttack() { return ((int) (((2 * BaseValues.getSPAttack(this.pokedexID)) + IVs[Attrib.SPATT] + (int)(EVs[Attrib.SPATT]/4f)) * level / 100f)) + 5; }
	public int getSPDefense() { return ((int) (((2 * BaseValues.getSPDefense(this.pokedexID)) + IVs[Attrib.SPDEF] + (int)(EVs[Attrib.SPDEF]/4f)) * level / 100f)) + 5; }
	public int getSpeed() { return ((int) (((2 * BaseValues.getSpeed(this.pokedexID)) + IVs[Attrib.SPEED] + (int)(EVs[Attrib.SPEED]/4f)) * level / 100f)) + 5; }
	public int getCatchRate() { return BaseValues.getCatchRate(this.pokedexID); }
	public PokemonType getType1() { return BaseValues.getType1(this.pokedexID); }
	public PokemonType getType2() { return BaseValues.getType2(this.pokedexID); }
	public int getLevel() { return this.level; }
	
	@Override
	public String toString() {
		if (nickname == "") {
			return BaseValues.getName(this.pokedexID) + " (Level " + level + ")";
		} else {
			return nickname + " (Level " + level + " " + BaseValues.getName(this.pokedexID) + ")";
		}
	}
	
	public Move[] getMoves() {
		return moveList;
	}
	
	public String getInfoString() {
		if (nickname == "") {
			return BaseValues.getName(this.pokedexID) + 
					" (Lv " + level + "): Atk: " + getAttack() + 
					", Def: " + getDefense() +
					", SPAtk: " + getSPAttack() + 
					", SPDef: " + getSPDefense() + 
					", Spd: " + getSpeed() + 
					", HP: " + getCurrentHP() + "/" + getMaxHP() + 
					", Type: " + BaseValues.getType1(this.pokedexID) + "/" + BaseValues.getType2(this.pokedexID) + 
					", Height: " + BaseValues.getHeight(this.pokedexID) + 
					", Weight: " + BaseValues.getWeight(this.pokedexID);
		} else {
			return nickname + " (Level " + level + " " + BaseValues.getName(this.pokedexID) + "): ";
		}
	}
	
	private static void generateProbabilities() {
		System.out.println("Generating probabilities");
		Set<Integer> IDs = BaseValues.getListOfIDs();
		for (int id : IDs) {
			idGenerator.addValue(id, BaseValues.getProbability(id));
		}
		
	}
	
}