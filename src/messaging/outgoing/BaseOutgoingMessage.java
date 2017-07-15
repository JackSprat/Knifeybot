package messaging.outgoing;

public abstract class BaseOutgoingMessage {

	public String text = "NOSTRING";

	public abstract String getTypeString();

	@Override
	public abstract String toString();
}