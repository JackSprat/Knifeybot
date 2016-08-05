package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.ProcBase;

public class ProcPokemon extends ProcBase {
	
	public ProcPokemon(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		
		commands.add(new CommandStarter());
		commands.add(new CommandViewParty());
	}
	
}
