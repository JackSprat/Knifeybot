package messaging.incoming;

public class IncomingIRCInfoMessage extends IncomingIRCMessage {

	private String ID = "NOSTRING";

	public IncomingIRCInfoMessage(String[] tokenList, String ID, String message) {
		this.tokenList = tokenList;
		this.ID = ID;
		originalString = message;
	}

	public String getID() {
		return ID;
	}

	@Override
	public String getTypeString() {
		return "IRCINFO";
	}
}
