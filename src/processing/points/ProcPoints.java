package processing.points;

import java.util.concurrent.BlockingQueue;

import messaging.outgoing.BaseOutgoingMessage;
import processing.ProcBase;

public class ProcPoints extends ProcBase {
	
	public ProcPoints(BlockingQueue<BaseOutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		commands.add(new CommandPointsAdd());
	}
	

}