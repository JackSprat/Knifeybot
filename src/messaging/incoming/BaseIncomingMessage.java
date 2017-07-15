package messaging.incoming;

import java.util.Date;


public abstract class BaseIncomingMessage {

	public static BaseIncomingMessage constructMessage(String message) {
		if (message.startsWith("PING")) {
			return new IncomingIRCPingMessage(message);
		} else if (message.startsWith(":")) {
			// :message
			String[] splitLines = message.split(":", 3);
			if (splitLines.length > 1) {
				String[] messageMetadata = splitLines[1].split(" ");
				// INFO message
				if (messageMetadata[0].equals("tmi.twitch.tv")) {
					return new IncomingIRCInfoMessage(splitLines[2].split(" "), messageMetadata[1], message);
					// CHAT message
					// IRCFormat: :user!user@user.tmi.twitch.tv PRIVMSG #channel
				} else if (messageMetadata[1]
						.equals("PRIVMSG")) { return new IncomingIRCChatMessage(splitLines[2].split(" "),
								messageMetadata[0].split("!")[0], messageMetadata[2].substring(1), message); }
			}
		} else if (message.startsWith("{")) {
			if (message.contains("topic\":\"channel-subscribe-events")) {
				return new IncomingWebsocketSubMessage(message);
			} else if (message.contains("\"type\": \"PONG\"")) { return new IncomingWebsocketPongMessage(); }
		}
		return new IncomingUnknownMessage(message);
	}

	protected String	originalString	= "NOSTRING";
	protected Date		date;
	protected String	channel			= "NOSTRING";

	public BaseIncomingMessage() {
		date = new Date();
	}

	public String getChannel() {
		return channel;
	}

	public Date getDate() {
		return date;
	}

	public String getOriginalString() {
		return originalString;
	}

	public abstract String getTypeString();
}