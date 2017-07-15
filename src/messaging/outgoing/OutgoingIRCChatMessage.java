package messaging.outgoing;

public class OutgoingIRCChatMessage extends OutgoingIRCMessage {

	String channel;

	public OutgoingIRCChatMessage(String text, String channel) {
		this.text = text;
		this.channel = channel;
	}

	@Override
	public String getTypeString() {
		return "IRCCHAT";
	}

	@Override
	public String toString() {
		return "PRIVMSG #" + channel + " :" + text + "\r\n";
	}
}
