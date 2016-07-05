package messaging;

public class OutgoingMessage {

	public enum OutType {
		RAW,
		PONG,
		CHAT,
		POKEMON,
		WHISPER,
		RAW_WHISPER,
		UNKNOWN
	}
	
	public OutType type = OutType.UNKNOWN;
	public String text = "NOSTRING";
	public String target = "NOSTRING";
	
	public OutgoingMessage(OutType type, String text, String target) {
		this.type = type;
		this.text = text;
		this.target = target;
	}
	
	@Override
	public String toString() {
		switch(type) {
			case RAW:
				return text;
			case PONG:
				return "PONG " + text + "\r\n";
			case CHAT:
				return "PRIVMSG #" + target + " :" + text + "\r\n";
			case UNKNOWN:
			default:
				return "ERROR";		
		}
		
	}
	
}