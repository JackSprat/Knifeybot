package processing.functions;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.ProcBase;

public class ProcFunctions extends ProcBase {

	public ProcFunctions(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		commands.add(new CommandRoll());
		commands.add(new CommandMath());
		commands.add(new CommandStatus());
		commands.add(new CommandSub());
		commands.add(new CommandUptime());
		
	}

}