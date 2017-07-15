package processing;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import messaging.incoming.IncomingWebsocketMessage;
import messaging.outgoing.BaseOutgoingMessage;
import messaging.outgoing.OutgoingIRCChatMessage;
import messaging.outgoing.OutgoingWebsocketCloseMessage;
import messaging.outgoing.OutgoingWebsocketMessage;


public abstract class WebsocketBase {

	private BlockingQueue<BaseOutgoingMessage>	listOut;
	protected ProcBase							parent;
	protected IncomingWebsocketMessage			wsIn;
	protected ArrayList<WebsocketBase>			wscommands	= new ArrayList<WebsocketBase>();

	public abstract void execute();

	public abstract boolean isMatch();

	protected void sendChatReply(String message) {
		listOut.add(new OutgoingIRCChatMessage(message, parent.channel));
	}

	protected void sendWebsocketCloseReply() {
		listOut.add(new OutgoingWebsocketCloseMessage());
	}

	protected void sendWebsocketReply(String message) {
		listOut.add(new OutgoingWebsocketMessage(message));
	}

	public void setParent(ProcBase parent, BlockingQueue<BaseOutgoingMessage> listOut) {
		this.parent = parent;
		this.listOut = listOut;
	}

	public void setWebsocketMessage(IncomingWebsocketMessage wsIn) {
		this.wsIn = wsIn;
	}

	public boolean validate() {
		return isMatch();
	}
}
