package processing.roll;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.ProcBase;

public class ProcRoll extends ProcBase {

	public ProcRoll(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		commands.add(new CommandRoll());
		commands.add(new CommandMath());
	}

}