package processing.subs;

import java.util.concurrent.BlockingQueue;

import messaging.outgoing.BaseOutgoingMessage;
import processing.ProcBase;


public class ProcSubscriptions extends ProcBase {

	public ProcSubscriptions(BlockingQueue<BaseOutgoingMessage> listOut, String channel) {
		super(listOut, channel);
		commands.add(new CommandSubMessageAdd());
		events.add(new EventPingPubSub());
		wscommands.add(new WebsocketSubNotify());
	}
}