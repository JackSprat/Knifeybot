package processing.pokemon.creation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import logger.Logger;

public class BaseValues {
//107107Hitmonchan5010579763534569

private static HashMap<Integer, BaseValues> info = new HashMap<Integer, BaseValues>();
public String name = "";
public int HP, Attack, Defense, SPAttack, SPDefense, Speed, CatchRate;
public int Probability;

private static void initialise() {

if (!info.isEmpty()) return;

try {

BufferedReader f = new BufferedReader(new FileReader("pokemonStats.dat"));
String line = f.readLine();

while(line != null) {

String[] vars = line.split(" |\\t");

int pokemonID = Integer.parseInt(vars[0]);
BaseValues staticPokemon = new BaseValues();
for (int i = 2; i < vars.length - 9; i++) {
staticPokemon.name += vars[i] + (((i + 1) < (vars.length - 9)) ? " " : "");
}

staticPokemon.HP = Integer.parseInt(vars[vars.length - 9]);
staticPokemon.Attack = Integer.parseInt(vars[vars.length - 8]);
staticPokemon.Defense = Integer.parseInt(vars[vars.length - 7]);
staticPokemon.SPAttack = Integer.parseInt(vars[vars.length - 6]);
staticPokemon.SPDefense = Integer.parseInt(vars[vars.length - 5]);
staticPokemon.Speed = Integer.parseInt(vars[vars.length - 4]);
staticPokemon.Probability = 
Math.max(1, (int) (Math.pow(780, 3) / Math.pow(Integer.parseInt(vars[vars.length - 3]), 3)));
staticPokemon.CatchRate = Integer.parseInt(vars[vars.length - 1]);
info.put(pokemonID, staticPokemon);

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

public static Set<Integer> getKeyList() {
initialise();
return info.keySet();
}
}