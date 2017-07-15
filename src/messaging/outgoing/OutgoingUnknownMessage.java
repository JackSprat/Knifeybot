package messaging.outgoing;

public class OutgoingUnknownMessage extends BaseOutgoingMessage {

	public OutgoingUnknownMessage(String text) {
		this.text = text;
	}

	@Override
	public String getTypeString() {
		return "UNKNOWN";
	}

	@Override
	public String toString() {
		return text;
	}
}
