package processing.permissions;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.ProcBase;

public class ProcPermissions extends ProcBase {
	
	public ProcPermissions(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		commands.add(new CommandPermGrant());
		commands.add(new CommandPermRemove());
		commands.add(new CommandPermSetGroup());
		commands.add(new CommandPermShow());

	}
	
}