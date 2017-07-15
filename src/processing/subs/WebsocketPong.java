package processing.subs;

import messaging.incoming.IncomingWebsocketPongMessage;
import processing.WebsocketBase;
import state.ChannelState;


public class WebsocketPong extends WebsocketBase {

	@Override
	public void execute() {
		long timeDiff = ChannelState.getLastWebsocketPingTimeAgo(parent.channel);
		if (timeDiff > 10000) {
			sendWebsocketCloseReply();
		}
	}

	@Override
	public boolean isMatch() {
		return wsIn instanceof IncomingWebsocketPongMessage;
	}
}
