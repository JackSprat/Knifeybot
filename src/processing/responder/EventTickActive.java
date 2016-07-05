package processing.responder;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.EventBase;

public class EventTickActive extends EventBase {
	
	@Override
	protected long getTickSeconds() { return 3600; }

	protected boolean tickSelf(BlockingQueue<OutgoingMessage> listOut) {
		
		((ProcResponder)parent).update();
		return true;
		
	}
	
}
