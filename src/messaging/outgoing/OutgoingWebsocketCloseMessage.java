package messaging.outgoing;

public class OutgoingWebsocketCloseMessage extends BaseOutgoingMessage {

	public OutgoingWebsocketCloseMessage() {}

	@Override
	public String getTypeString() {
		return "WSSCLOSE";
	}

	@Override
	public String toString() {
		return "NOSTRING";
	}
}
