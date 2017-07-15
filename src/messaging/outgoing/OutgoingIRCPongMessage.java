package messaging.outgoing;

public class OutgoingIRCPongMessage extends OutgoingIRCMessage {

	public OutgoingIRCPongMessage(String text) {
		this.text = text;
	}

	@Override
	public String getTypeString() {
		return "IRCPONG";
	}

	@Override
	public String toString() {
		return "PONG " + text + "\r\n";
	}
}
