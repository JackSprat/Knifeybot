package messaging.incoming;

public class IncomingUnknownMessage extends BaseIncomingMessage {

	public IncomingUnknownMessage(String message) {}

	@Override
	public String getTypeString() {
		return "UNKNOWN";
	}
}
