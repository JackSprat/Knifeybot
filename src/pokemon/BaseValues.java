package pokemon;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import logger.Logger;

public class BaseValues {

	private static HashMap<Integer, BaseValues> info = new HashMap<Integer, BaseValues>();
	private String name = "";
	private int HP, Attack, Defense, SPAttack, SPDefense, Speed, CatchRate;
	private int Probability;
	private float height, weight;
	private PokemonType type1, type2 = PokemonType.NONE;

	private static void initialise() {

		if (!info.isEmpty()) return;

		try {

			BufferedReader f = new BufferedReader(new FileReader("src/pokemon/PokemonBaseStats.csv"));
			String line = f.readLine();

			while(line != null) {

				String[] baseValueTokens = line.split(",");

				try {
					
					int pokemonID = Integer.parseInt(baseValueTokens[0]);

					BaseValues staticPokemon = new BaseValues();
					staticPokemon.name =  baseValueTokens[1];
					staticPokemon.HP = Integer.parseInt(baseValueTokens[2]);
					staticPokemon.Attack = Integer.parseInt(baseValueTokens[3]);
					staticPokemon.Defense = Integer.parseInt(baseValueTokens[4]);
					staticPokemon.SPAttack = Integer.parseInt(baseValueTokens[5]);
					staticPokemon.SPDefense = Integer.parseInt(baseValueTokens[6]);
					staticPokemon.Speed = Integer.parseInt(baseValueTokens[7]);
					staticPokemon.Probability = Math.max(1, (int) (Math.pow(780, 2) / Math.pow(Integer.parseInt(baseValueTokens[8]), 2)));
					staticPokemon.CatchRate = Integer.parseInt(baseValueTokens[9]);
					
					String[] types = baseValueTokens[10].split(" ");
					staticPokemon.type1 = PokemonType.getType(types[0]);
					if (types.length > 1) staticPokemon.type2 = PokemonType.getType(types[1]);
					
					staticPokemon.height = Float.parseFloat(baseValueTokens[11]);
					staticPokemon.weight = Float.parseFloat(baseValueTokens[12]);
					
					info.put(pokemonID, staticPokemon);
					
				} catch (NumberFormatException nfe) {
					Logger.STACK("Error: Not a number ", nfe);
				}


				line = f.readLine();

			}
			f.close();

		} catch (IOException e) {
			Logger.STACK("", e);
		}
	}

	public static String getName(int ID) { initialise(); if (info.containsKey(ID)) return info.get(ID).name; return ""; }
	public static int getHP(int ID) { initialise(); if (info.containsKey(ID)) return info.get(ID).HP; return 0; }
	public static int getAttack(int ID) { initialise(); if (info.containsKey(ID)) return info.get(ID).Attack; return 0; }
	public static int getDefense(int ID) { initialise(); if (info.containsKey(ID)) return info.get(ID).Defense; return 0; }
	public static int getSPAttack(int ID) { initialise(); if (info.containsKey(ID)) return info.get(ID).SPAttack; return 0; }
	public static int getSPDefense(int ID) { initialise(); if (info.containsKey(ID)) return info.get(ID).SPDefense; return 0; }
	public static int getSpeed(int ID) { initialise(); if (info.containsKey(ID)) return info.get(ID).Speed; return 0; }
	public static int getProbability(int ID) { initialise(); if (info.containsKey(ID)) return info.get(ID).Probability; return 0; }
	public static int getCatchRate(int ID) { initialise(); if (info.containsKey(ID)) return info.get(ID).CatchRate; return 0; }
	public static PokemonType getType1(int ID) { initialise(); if (info.containsKey(ID)) return info.get(ID).type1; return PokemonType.NONE; }
	public static PokemonType getType2(int ID) { initialise(); if (info.containsKey(ID)) return info.get(ID).type2; return PokemonType.NONE; }
	public static float getHeight(int ID) { initialise(); if (info.containsKey(ID)) return info.get(ID).height; return 0; }
	public static float getWeight(int ID) { initialise(); if (info.containsKey(ID)) return info.get(ID).weight; return 0; }
	public static Set<Integer> getListOfIDs() {
		initialise();
		return info.keySet();
	}
}