package pokemon.encounter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import pokemon.Pokemon;
import pokemon.PokemonUser;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;

public class PokemonEncounter {

	private BlockingQueue<OutgoingMessage> listOut;
	private EncounterState state = EncounterState.VOTE_SUBMISSION;
	private List<PokemonUser> registeredUsers = new ArrayList<PokemonUser>();
	private PokemonUser chosenUser;
	private Pokemon encounterPokemon;
	
	private long startTime = new Date().getTime();
	
	
	public PokemonEncounter(BlockingQueue<OutgoingMessage> listOut) {
		encounterPokemon = new Pokemon();
		this.listOut = listOut;
		listOut.add(new OutgoingMessage(OutType.CHAT, "A wild " + encounterPokemon.toString() + " has appeared!", "pokket"));
	}
	
	public void registerUser(String username) {
		if (state == EncounterState.VOTE_SUBMISSION || state == EncounterState.VOTE_SUBMISSION_2) registeredUsers.add(PokemonUser.getUser(username));
	}
	
	public void tryCatch(String username) {
		
		if (state == EncounterState.WINNER_CONFIRMATION) {
			
			if (chosenUser == null) 								return;
			if (!chosenUser.getName().equalsIgnoreCase(username))	return;
			if (chosenUser.useInventoryItem(0)) {
				int alpha = (3 * encounterPokemon.getMaxHP() - 2 * encounterPokemon.getCurrentHP()) * encounterPokemon.getCatchRate() / (3 * encounterPokemon.getMaxHP());
				double shakeChance = 65536 / Math.pow((255f/alpha), 0.1875);
				boolean caught = true;
				for (int i = 0; i < 4; i++) {
					double random = Math.random() * 65535;
					if (i == 0) listOut.add(new OutgoingMessage(OutType.CHAT, "Shake...", "pokket"));
					if (i == 1) listOut.add(new OutgoingMessage(OutType.CHAT, "...Shake...", "pokket"));
					if (i == 3) listOut.add(new OutgoingMessage(OutType.CHAT, "...Shake", "pokket"));
					
					if (random > shakeChance) {
						listOut.add(new OutgoingMessage(OutType.CHAT, "Oh No!", "pokket"));
						listOut.add(new OutgoingMessage(OutType.CHAT, "The Pokémon broke free!", "pokket"));
						caught = false;
						break;
					}
					
				}
				
				if (caught) {
					int slot = chosenUser.addPokemon(encounterPokemon);
					if (slot < 6) {
						listOut.add(new OutgoingMessage(OutType.CHAT, "Your new " + encounterPokemon.toString() + " was added into your party at slot " + slot, "pokket"));
					} else {
						listOut.add(new OutgoingMessage(OutType.CHAT, "Your new " + encounterPokemon.toString() + " was put into your storage", "pokket"));
					}
				}
				state = EncounterState.COMPLETE;
			}
			
		}
	}
	
	public void run(String username) {
		if (state == EncounterState.WINNER_CONFIRMATION) {
			if (chosenUser == null) 								return;
			if (!chosenUser.getName().equalsIgnoreCase(username))	return;
			
			String oldUser = chosenUser.getName();
			if (registeredUsers.size() == 0) { state = EncounterState.COMPLETE; return; }
			
			int userIndex = (int)(Math.random() * registeredUsers.size());
			chosenUser = registeredUsers.get(userIndex);
			registeredUsers.remove(userIndex);
			listOut.add(new OutgoingMessage(OutType.CHAT, oldUser + " ran like a coward! " + chosenUser.getName() + ", you have been chosen to battle the " + encounterPokemon.toString() + "! You can use :kbattle catch, :kbattle fight, or :kbattle run like the last guy :/ . You have 60 seconds!", "pokket"));
			
			startTime = new Date().getTime();
			
		}
	}
	
	public EncounterState tick() {
		
		//First voting stage, after announcement and pre 60 seconds message
		if (state == EncounterState.VOTE_SUBMISSION) {
			if (startTime + 10*1000 < new Date().getTime()) {
				listOut.add(new OutgoingMessage(OutType.CHAT, "30 seconds left to join the encounter with :kbattle", "pokket"));
				state = EncounterState.VOTE_SUBMISSION_2;
			}
		} else
		//Second voting stage, after 60 seconds message
		if (state == EncounterState.VOTE_SUBMISSION_2) {
			
			if (startTime + 40*1000 < new Date().getTime()) {
				
				
				state = EncounterState.WINNER_CONFIRMATION;
				if (registeredUsers.size() == 0) {
					state = EncounterState.COMPLETE; return state;
				}
				int userIndex = (int)(Math.random() * registeredUsers.size());
				chosenUser = registeredUsers.get(userIndex);
				
				registeredUsers.remove(userIndex);
				
				listOut.add(new OutgoingMessage(OutType.CHAT, chosenUser.getName() + ", you have been chosen to battle the " + encounterPokemon.toString() + "! You can use :kbattle catch, :kbattle fight, or :kbattle run to give someone else a chance. You have 60 seconds!", "pokket"));
				startTime = new Date().getTime();
			}
			
		} else
		
		if (state == EncounterState.WINNER_CONFIRMATION) {
			
			if (startTime + 60*1000 < new Date().getTime()) {
				
				String oldUser = chosenUser.getName();
				if (registeredUsers.size() == 0) {
					state = EncounterState.COMPLETE; return state;
				}
				int userIndex = (int)(Math.random() * registeredUsers.size());
				chosenUser = registeredUsers.get(userIndex);
				listOut.add(new OutgoingMessage(OutType.CHAT, oldUser + " was too slow. " + chosenUser.getName() + ", you have been chosen to battle the " + encounterPokemon.toString() + "! You can use :kbattle catch, :kbattle fight, or :kbattle run to give someone else a chance. You have 60 seconds!", "pokket"));
				startTime = new Date().getTime();
			}
			
		}
		
		return state;
		
	}
	
}