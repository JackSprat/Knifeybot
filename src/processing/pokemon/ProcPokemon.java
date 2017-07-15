package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import messaging.outgoing.BaseOutgoingMessage;
import processing.ProcBase;

public class ProcPokemon extends ProcBase {
	
	public ProcPokemon(BlockingQueue<BaseOutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		
		commands.add(new CommandStarter());
		commands.add(new CommandViewParty());
		commands.add(new CommandViewPokemon());
		
	}
	
}
