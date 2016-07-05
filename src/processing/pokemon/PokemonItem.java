package processing.pokemon;

import java.util.HashMap;

public class PokemonItem {

	private static boolean initialised = false;
	private static HashMap<String, Integer> items = new HashMap<String, Integer>();
	
	public static int get(String itemName) {
		if (!initialised) initialise();
		Integer id = items.get(itemName.toLowerCase());
		if (id == null) return -1;
		return id;
	}
	
	private static void initialise() {
		items.put("pokeball",  0);
		
	}
	
	
	
}