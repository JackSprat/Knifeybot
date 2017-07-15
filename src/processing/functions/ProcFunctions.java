package processing.functions;

import java.util.concurrent.BlockingQueue;

import messaging.outgoing.BaseOutgoingMessage;
import processing.ProcBase;

public class ProcFunctions extends ProcBase {

	public ProcFunctions(BlockingQueue<BaseOutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		commands.add(new CommandRoll());
		commands.add(new CommandMath());
		commands.add(new CommandStatus());
		commands.add(new CommandSub());
		commands.add(new CommandUptime());
		
	}

}