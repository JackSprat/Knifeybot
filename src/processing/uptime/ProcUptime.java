package processing.uptime;

import java.util.concurrent.BlockingQueue;

import messaging.outgoing.BaseOutgoingMessage;
import processing.ProcBase;

public class ProcUptime extends ProcBase {

	public long startTime;	
	
	public ProcUptime(BlockingQueue<BaseOutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		startTime = System.currentTimeMillis();
		commands.add(new CommandBotUptime());
		
	}

}
