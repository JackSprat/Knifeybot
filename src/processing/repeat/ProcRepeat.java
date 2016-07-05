package processing.repeat;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.ProcBase;

public class ProcRepeat extends ProcBase {

	public ProcRepeat(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		commands.add(new CommandRepeat());
	}

}
