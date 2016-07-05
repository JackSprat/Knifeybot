package processing.uptime;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.ProcBase;

public class ProcUptime extends ProcBase {

	public long startTime;	
	
	public ProcUptime(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		startTime = System.currentTimeMillis();
		commands.add(new CommandBotUptime());
		
	}

}
