package messaging.incoming;

public class IncomingIRCPingMessage extends IncomingIRCMessage {

	public IncomingIRCPingMessage(String message) {
		originalString = message;
	}

	@Override
	public String getTypeString() {
		return "PING";
	}
}
