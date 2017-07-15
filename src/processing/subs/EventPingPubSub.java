package processing.subs;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.outgoing.BaseOutgoingMessage;
import messaging.outgoing.OutgoingWebsocketMessage;
import processing.EventBase;
import state.ChannelState;


public class EventPingPubSub extends EventBase {

	@Override
	protected long getTickSeconds() {
		return 150;
	}

	@Override
	protected void tickSelf(BlockingQueue<BaseOutgoingMessage> listOut) {
		listOut.add(new OutgoingWebsocketMessage("{\"type\": \"PING\"}"));
		Logger.TRACE("Websocket Ping for " + parent.channel);
		ChannelState.notifyWebsocketPing(parent.channel);
	}
}
