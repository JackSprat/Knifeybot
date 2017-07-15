package messaging.incoming;

public abstract class IncomingIRCMessage extends BaseIncomingMessage {

	protected String[] tokenList;

	public String[] getTokenList() {
		if (tokenList == null) {
			tokenList = originalString.split(" ");
		}
		return tokenList;
	}
}