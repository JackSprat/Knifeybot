package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import pokemon.PokemonManager;
import pokemon.PokemonUser;
import processing.ProcBase;

public class ProcPokemon extends ProcBase {
	
	private PokemonManager pokemon = new PokemonManager();
	
	public ProcPokemon(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		commands.add(new CommandBattle());
		commands.add(new CommandChallenge());
		commands.add(new CommandCatch());
		commands.add(new CommandRun());
		commands.add(new CommandParty());
		commands.add(new CommandPC());
		commands.add(new CommandGift());
		commands.add(new CommandTick());
		commands.add(new CommandDeposit());
		commands.add(new CommandWithdraw());
		commands.add(new CommandMove());
		commands.add(new CommandChoose());
		commands.add(new CommandSelect());
		commands.add(new CommandStartEncounter());
		commands.add(new CommandCheckInventory());
		
		events.add(new EventTickGame());
		
	}

	public boolean createEncounter() {
		pokemon.createEncounter(listOut);
		return true;
	}

	public boolean addUserToEncounter(String user) {
		pokemon.addUserToEncounter(user);
		return true; 
	}

	public void tickGame() { pokemon.tick(); }

	public void attemptCatch(String user) { pokemon.attemptCatch(user); }

	public void run(String user) { pokemon.run(user); }

	public boolean printParty(String username) {
		System.out.println("test");
		return listOut.add(new OutgoingMessage(OutType.CHAT, PokemonUser.getUser(username).partyString(), channel));
	}
	public boolean printParty(String user, int testInt) {
		
		return listOut.add(new OutgoingMessage(OutType.CHAT, PokemonUser.getUser(user).getPartyMember(testInt).getInfoString(), channel));
		
	}
	public boolean giftItem(String user, String item, String count) {
		int id = PokemonItem.get(item);
		try {
			int intCount = Integer.parseInt(count);
			if (id != -1 && intCount > 0) {
				PokemonUser.getUser(user).addItems(id, intCount);
				return listOut.add(new OutgoingMessage(OutType.CHAT, user + " has been gifted " + count + " " + item + (intCount > 1 ? "s" : ""), channel));
			}
		} catch (NumberFormatException nfe) {
			Logger.STACK("", nfe);
		}
		return false;
	}

	public boolean showInventory(String user, String itemName) {
		int id = PokemonItem.get(itemName);
		if (id >= 0) {
			int itemCount = PokemonUser.getUser(user).getInventoryCount(0);
			return listOut.add(new OutgoingMessage(OutType.CHAT, user + " has " + itemCount + " " + itemName, channel));
		} else {
			return listOut.add(new OutgoingMessage(OutType.CHAT, "\"" + itemName + "\" not recognised. Please check TELLJACKTOIMPLEMENTTHIS to see a list of items", channel));
		}
	}

	public void startBattle(String attacker, String defender) {
		pokemon.createBattle(listOut, attacker, defender);
	}
	
	public void joinBattle(String user) {
		pokemon.joinBattle(user);
	}

	public boolean printPC(String user, int testInt) {
		return listOut.add(new OutgoingMessage(OutType.CHAT, PokemonUser.getUser(user).getPCMember(testInt).getInfoString(), channel));
	}

	public boolean choosePokemon(String user, int testInt) {
		return pokemon.choosePokemon(user, testInt);
	}

	public boolean chooseMove(String user, String token) {
		return pokemon.chooseMove(user, token);
	}
	
}
