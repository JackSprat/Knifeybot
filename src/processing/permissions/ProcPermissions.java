package processing.permissions;

import java.util.concurrent.BlockingQueue;

import messaging.outgoing.BaseOutgoingMessage;
import processing.ProcBase;

public class ProcPermissions extends ProcBase {
	
	public ProcPermissions(BlockingQueue<BaseOutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		commands.add(new CommandPermSetGroup());
		commands.add(new CommandPermShow());

	}
	
}