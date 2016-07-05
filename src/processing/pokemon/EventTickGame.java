package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.EventBase;

public class EventTickGame extends EventBase {
	
	@Override
	protected long getTickSeconds() { return 10; }

	protected boolean tickSelf(BlockingQueue<OutgoingMessage> listOut) {
		
		((ProcPokemon)parent).tickGame();
		return true;
		
	}
	
}
