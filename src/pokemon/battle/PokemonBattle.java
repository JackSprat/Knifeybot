package pokemon.battle;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import pokemon.Pokemon;
import pokemon.PokemonUser;
import pokemon.moves.Move;

public class PokemonBattle {

	private BlockingQueue<OutgoingMessage> listOut;
	private PokemonUser attacker, defender;
	private BattleState state;
	private Pokemon attackerSelected, defenderSelected;
	private int attackerMoveSelected = -1, defenderMoveSelected = -1;
	
	private long startTime = new Date().getTime();
	
	public PokemonBattle(BlockingQueue<OutgoingMessage> listOut, String attacker, String defender) {
		this.listOut = listOut;
		if (PokemonUser.userExists(attacker) && PokemonUser.userExists(defender)) {
			this.attacker = PokemonUser.getUser(attacker);
			this.defender = PokemonUser.getUser(defender);
			
			boolean attackerValid = false;
			boolean defenderValid = false;
			for (int i = 0; i < 5; i++) {
				Pokemon p = this.attacker.getPartyMember(i);
				if (p != null && p.getCurrentHP() > 0) {
					attackerValid = true;
				}
			}
			for (int i = 0; i < 5; i++) {
				Pokemon p = this.defender.getPartyMember(i);
				if (p != null && p.getCurrentHP() > 0) {
					defenderValid = true;
				}
			}
			if (!attackerValid || !defenderValid) {
				listOut.add(new OutgoingMessage(OutType.CHAT, "Can't create game. " + (defenderValid ? "you" : "defender") + " doesn't have any pokemon awake in their party!", "pokket"));
				state = BattleState.COMPLETE;
				return;
			}
			
			listOut.add(new OutgoingMessage(OutType.CHAT, defender + "! You have been challenged by " + attacker + " to a fight! You have 30 seconds, do you accept? (:kbattle challenge accept) ", "pokket"));
			state = BattleState.CHALLENGE;
			
		} else {
			listOut.add(new OutgoingMessage(OutType.CHAT, "Can't create game between " + attacker + " and " + defender + ". Someone doesn't have pokemon!", "pokket"));
			state = BattleState.COMPLETE;
			return;
		}
		
	}
	
	public BattleState tick() {
		
		if (state == BattleState.CHALLENGE && ((startTime + 45*1000) < new Date().getTime())) {
			listOut.add(new OutgoingMessage(OutType.CHAT, defender.getName() + " didn't accept your challenge in time!", "pokket"));
			state = BattleState.COMPLETE;
		} else if (state == BattleState.CHALLENGE_ACCEPTED) {
			listOut.add(new OutgoingMessage(OutType.CHAT, "The fight is on! You have 45 seconds to select a pokemon in your party to do battle, " + attacker.getName() + " and " + defender.getName() + "!", "pokket"));
			state = BattleState.POKEMON_SELECTION;
			startTime = new Date().getTime();
		} else if (state == BattleState.POKEMON_SELECTION && ((startTime + 120*1000) < new Date().getTime())) {
			listOut.add(new OutgoingMessage(OutType.CHAT, "Players took too long to select pokemon!", "pokket"));
			state = BattleState.COMPLETE;
		} else if (state == BattleState.POKEMON_SELECTION_COMPLETE) {
			state = BattleState.MOVE_SELECTION;
			listOut.add(new OutgoingMessage(OutType.CHAT, attacker.getName() + ", " + defender.getName() + "... Select your moves! You have two minutes", "pokket"));
			startTime = new Date().getTime();
		} else if (state == BattleState.MOVE_SELECTION && ((startTime + 120*1000) < new Date().getTime())) {
			this.attackerSelected.getMoves()[this.attackerMoveSelected].execute(this.attackerSelected, this.defenderSelected, listOut);
			this.defenderSelected.getMoves()[this.defenderMoveSelected].execute(this.defenderSelected, this.attackerSelected, listOut);
			startTime = new Date().getTime();
		}
		return state;
	}
	
	public void choosePokemon(String user, int i) {
		if (state != BattleState.POKEMON_SELECTION) return;
		
		if (user.equalsIgnoreCase(attacker.getName())) {
			Pokemon p = attacker.getPartyMember(i);
			if (p != null) {
				if (attackerSelected != null) {
					listOut.add(new OutgoingMessage(OutType.CHAT, attacker.getName() + ", you have already selected a pokemon", "pokket"));
					return;
				}
				if (p.getCurrentHP() > 0) {
					attackerSelected = p;
					if (attackerSelected != null && defenderSelected != null) state = BattleState.POKEMON_SELECTION_COMPLETE;
					listOut.add(new OutgoingMessage(OutType.CHAT, attacker.getName() + ", you have selected a pokemon!", "pokket"));
					return;
				}
				listOut.add(new OutgoingMessage(OutType.CHAT, attacker.getName() + ", Pokemon has no HP!", "pokket"));
			}
			listOut.add(new OutgoingMessage(OutType.CHAT, attacker.getName() + ", can't find pokemon in that slot", "pokket"));
		}
		if (user.equalsIgnoreCase(defender.getName())) {
			Pokemon p = defender.getPartyMember(i);
			if (p != null) {
				if (defenderSelected != null) {
					listOut.add(new OutgoingMessage(OutType.CHAT, defender.getName() + ", you have already selected a pokemon", "pokket"));
					return;
				}
				if (p.getCurrentHP() > 0) {
					defenderSelected = p;
					if (attackerSelected != null && defenderSelected != null) state = BattleState.POKEMON_SELECTION_COMPLETE;
					listOut.add(new OutgoingMessage(OutType.CHAT, defender.getName() + ", you have selected a pokemon!", "pokket"));
					return;
				}
				listOut.add(new OutgoingMessage(OutType.CHAT, defender.getName() + ", Pokemon has no HP!", "pokket"));
			}
			listOut.add(new OutgoingMessage(OutType.CHAT, defender.getName() + ", can't find pokemon in that slot", "pokket"));
		}
	}
	
	public void useMove(String user, String moveToUse) {
		if (user.equalsIgnoreCase(defender.getName())) {
			Move[] moves = defenderSelected.getMoves();
			for (int i = 0; i < 4; i++) {
				if (moves[i] != null && moves[i].getName().equalsIgnoreCase(moveToUse)) {
					defenderMoveSelected = i;
					listOut.add(new OutgoingMessage(OutType.CHAT, user + " selected " + moveToUse, "pokket"));
				}
			}
		}
		if (user.equalsIgnoreCase(attacker.getName())) {
			Move[] moves = attackerSelected.getMoves();
			for (int i = 0; i < 4; i++) {
				if (moves[i] != null && moves[i].getName().equalsIgnoreCase(moveToUse)) {
					attackerMoveSelected = i;
					listOut.add(new OutgoingMessage(OutType.CHAT, user + " selected " + moveToUse, "pokket"));
				}
			}
		}
	}

	public void registerUser(String user) {
		if (state == BattleState.CHALLENGE && user.equalsIgnoreCase(defender.getName())) {
			state = BattleState.CHALLENGE_ACCEPTED;
		}
		
	}
}