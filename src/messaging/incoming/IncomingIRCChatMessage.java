package messaging.incoming;

public class IncomingIRCChatMessage extends IncomingIRCMessage {

	private String user = "NOSTRING";

	public IncomingIRCChatMessage(String[] tokenList, String user, String channel, String originalString) {
		this.tokenList = tokenList;
		this.user = user;
		this.originalString = originalString;
		this.channel = channel;
	}

	@Override
	public String getTypeString() {
		return "IRCCHAT";
	}

	public String getUser() {
		return user;
	}
}
