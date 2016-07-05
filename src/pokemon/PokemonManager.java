package pokemon;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import pokemon.battle.BattleState;
import pokemon.battle.PokemonBattle;
import pokemon.encounter.EncounterState;
import pokemon.encounter.PokemonEncounter;

public class PokemonManager {

	private PokemonEncounter encounter;
	private PokemonBattle battle;
	
	public void tick() {
		if (encounter != null) {
			EncounterState state = encounter.tick();
			if (state == EncounterState.COMPLETE) {
				encounter = null;
			}
			return;
		}
		if (battle != null) {
			BattleState state = battle.tick();
			if (state == BattleState.COMPLETE) {
				battle = null;
			}
			return;
		}
	}


	public void createEncounter(BlockingQueue<OutgoingMessage> listOut) {
		if (encounter == null && battle == null) encounter = new PokemonEncounter(listOut);
	}

	public void addUserToEncounter(String user) { if (encounter != null) encounter.registerUser(user); }
	public void attemptCatch(String user) {	if (encounter != null) encounter.tryCatch(user); }
	public void run(String user) { if (encounter != null) encounter.run(user); }
	
	public void createBattle(BlockingQueue<OutgoingMessage> listOut, String attacker, String defender) {
		if (encounter == null && battle == null) battle = new PokemonBattle(listOut, attacker, defender);
	}

	public void joinBattle(String user) {
		if (encounter == null && battle != null) battle.registerUser(user);
	}

	public boolean choosePokemon(String user, int testInt) {
		if (encounter == null && battle != null) battle.choosePokemon(user, testInt);
		return true;
	}


	public boolean chooseMove(String user, String token) {
		if (encounter == null && battle != null) battle.useMove(user, token);
		return false;
	}
	
}