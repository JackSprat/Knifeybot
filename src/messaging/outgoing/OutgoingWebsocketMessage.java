package messaging.outgoing;

public class OutgoingWebsocketMessage extends BaseOutgoingMessage {

	public OutgoingWebsocketMessage(String message) {
		text = message;
	}

	@Override
	public String getTypeString() {
		return "WSS";
	}

	@Override
	public String toString() {
		return text;
	}
}
