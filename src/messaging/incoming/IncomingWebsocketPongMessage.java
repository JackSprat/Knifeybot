package messaging.incoming;

public class IncomingWebsocketPongMessage extends IncomingWebsocketMessage {

	public IncomingWebsocketPongMessage() {}

	@Override
	public String getTypeString() {
		return "WSSPONG";
	}
}
