package pokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;


public class PokemonUser {

	private static ObjectContainer database = Db4o.openFile("data/pokemonusers.db");
	private String name;
	private UUID[] party = new UUID[6];
	private ArrayList<UUID> pokemonList = new ArrayList<UUID>();
	private HashMap<Integer, Integer> inventory = new HashMap<Integer, Integer>();
	private int selectedSlot = 0;
	
	private PokemonUser(String username) {
		
		this.name = username;
		inventory.put(0, 3);
		
		database.store(this);
		database.commit();
		
	}
	
	public int getSelectedSlot() {
		return selectedSlot;
	}
	
	public void setSelectedSlot(int slot) {
		if (slot >= 0 && slot < 6) selectedSlot = slot;
		database.store(this);
		database.commit();
	}
	
	public synchronized static PokemonUser getUser(String username) {
		ObjectSet<PokemonUser> users = database.query(new Predicate<PokemonUser>() {
		    public boolean match(PokemonUser pu) {
		        return pu.name.equalsIgnoreCase(username);
		    }
		});
		
		if (users != null && users.size() > 0) {
			return users.get(0);
		} else {
			return new PokemonUser(username);
		}
		
	}
	
	public synchronized static boolean userExists(String username) {
		ObjectSet<PokemonUser> users = database.query(new Predicate<PokemonUser>() {
		    public boolean match(PokemonUser pu) {
		        return pu.name.equalsIgnoreCase(username);
		    }
		});
		if (users != null) return users.size() > 0;
		return false;
	}
	
	public synchronized int getInventoryCount(Integer itemID) {
		return (int)inventory.get(itemID);
	}
	public synchronized boolean useInventoryItem(Integer itemID) {
		int count = (int)inventory.get(itemID);
		if (count > 0) {
			inventory.put(itemID, count - 1);
			database.store(this);
			database.commit();
			return true;
		}
		return false;
		
	}
	
	public synchronized Pokemon getPartyMember(int slot) {
		if (slot < 0 || slot > 5 || party[slot] == null) return null;
		return Pokemon.getPokemon(party[slot]);
	}
	public synchronized Pokemon getPCMember(int slot) {
		if (slot < 0 || pokemonList.size() <= slot) return null;
		return Pokemon.getPokemon(pokemonList.get(slot));
	}
	public synchronized String getName() {
		return name;
	}
	
	public synchronized int addPokemon(Pokemon p) {
		int i = 0;
		for (i = 0; i < 6; i++) {
			if (party[i] == null) {
				party[i] = p.getUUID();
				database.store(this);
				database.commit();
				return i;
			}
		}
		pokemonList.add(p.getUUID());
		database.store(this);
		database.commit();
		return 6;
	}

	public synchronized String partyString() {
		String message = this.getName() + "'s party: ";
		for (int i = 0; i < 6; i++) {
			Pokemon p = Pokemon.getPokemon(party[i]);
			if (p != null) {
				message += i + ": " + p.toString() + ", ";
			}
		}
		
		return message;
	}
	
	public synchronized static void tick() {
		ObjectSet<PokemonUser> users = database.query(new Predicate<PokemonUser>() {
		    public boolean match(PokemonUser pu) {
		        return true;
		    }
		});
		
		for (PokemonUser pu : users) {
			for (UUID pokemon : pu.pokemonList) {
				Pokemon.getPokemon(pokemon).addHP(5);
			}
		}
		
	}

	public synchronized void addItems(int id, int addAmount) {
		int count = inventory.get(id);
		inventory.put(id, count + addAmount);
		database.store(this);
		database.commit();
	}

	public boolean depositPokemon(int testInt) {
		if (party[testInt] != null) {
			pokemonList.add(party[testInt]);
			party[testInt] = null;
		} else {
			return false;
		}
		database.store(this);
		database.commit();
		return true;
	}
	
	public boolean withdrawPokemon(int testInt) {
		
		UUID pokemonID = pokemonList.get(testInt);
		
		if (pokemonID != null) {
			pokemonList.remove(pokemonID);
			boolean addedToParty = false;
			for (int i = 0; i < 6; i++) {
				if (party[i] != null) continue;
				party[i] = pokemonID;
				addedToParty = true;
				break;
			}
			if (!addedToParty) return false;
		}
		database.store(this);
		database.commit();
		return true;
	}
	
}