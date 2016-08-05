package processing.pokemon.creation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import logger.Logger;
import utils.DirectoryUtils;

public class PokemonUser {

	@ElementList
	private List<Long> partyList = new ArrayList<Long>();
	
	@ElementList
	private List<Long> pcList = new ArrayList<Long>();
	
	@Element
	private boolean hasStarter = false;
	
	public static synchronized long[] getParty(String username) {
		PokemonUser user = getUser(username);
		return user.partyList.stream().mapToLong(l -> l).toArray();
	}
	
	public static synchronized void setUsedStarter(String username) {
		PokemonUser user = getUser(username);
		user.hasStarter = true;
		saveUser(username, user);
	}
	public static synchronized boolean hasUsedStarter(String username) {
		PokemonUser user = getUser(username);
		return user.hasStarter;
	}
	public static synchronized void addPokemon(String username, long UUID) {
		PokemonUser user = getUser(username);
		if (user.partyList.size() > 5) {
			user.pcList.add(UUID);
		} else {
			user.partyList.add(UUID);
		}
		saveUser(username, user);
	}
	public static synchronized boolean moveToPC(String username, int partyID) {
		PokemonUser user = getUser(username);
		if (user.partyList.size() <= partyID) {
			return false;
		}
		long uuid = user.partyList.get(partyID);
		user.partyList.remove(partyID);
		user.pcList.add(uuid);
		saveUser(username, user);
		return true;
	}
	
	private static Serializer serializer = new Persister();
	
	private static synchronized File getFile(String username) {
		DirectoryUtils.createDirectories("pokemon/users");
		
		String filename = "pokemon/users/" + username.toLowerCase() + ".xml";
		return new File(filename);
	}
	
	private static synchronized PokemonUser getUser(String username) {
		
		File userfile = getFile(username);

		try {
			PokemonUser u = userfile.exists() ? serializer.read(PokemonUser.class, userfile) : new PokemonUser();
			return u;
		} catch (Exception e1) {
			Logger.STACK("Error retrieving pokeuser - " + username, e1);
		}
		
		return null;
		
	}
	
	private static synchronized void saveUser(String username, PokemonUser u) {
		
		File userfile = getFile(username);
		try {
			serializer.write(u, userfile);
		} catch (Exception e) {
			Logger.STACK("Error writing pokeuser - " + username, e);
		}
	}
	
}