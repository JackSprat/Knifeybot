package messaging.outgoing;

public class OutgoingIRCRawMessage extends OutgoingIRCMessage {

	public OutgoingIRCRawMessage(String text) {
		this.text = text;
	}

	@Override
	public String getTypeString() {
		return "IRCRAW";
	}

	@Override
	public String toString() {
		return text;
	}
}
