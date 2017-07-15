package processing;

import java.util.concurrent.BlockingQueue;

import data.DataManager;
import messaging.incoming.IncomingIRCChatMessage;
import messaging.outgoing.BaseOutgoingMessage;
import messaging.outgoing.OutgoingIRCChatMessage;
import users.PermissionClass;


public abstract class CommandBase {

	private IncomingIRCChatMessage				ircIn;
	private BlockingQueue<BaseOutgoingMessage>	listOut;
	protected ProcBase							parent;

	public abstract void execute();

	public abstract String getFormatTokens();

	public abstract String getHelpString();

	public final PermissionClass getPermissionClass() {
		return DataManager.getPermissionClass(parent.channel, getPermissionString());
	}

	public abstract String getPermissionString();

	public String getToken(String token) {
		String[] formatTokens = getFormatTokens().split(" ");
		String[] messageTokens = ircIn.getTokenList();
		for (int i = 0; i < formatTokens.length; i++) {
			if (messageTokens.length <= i) { return ""; }
			String formatToken = formatTokens[i];
			if (!token.equalsIgnoreCase(formatToken) && !token.equalsIgnoreCase(formatToken.substring(1))) {
				continue;
			}
			// 0 or more tokens remaining
			if (formatToken.equals("*") || formatToken.equals("+")) {
				String tokens = "";
				for (int j = i; j < messageTokens.length; j++) {
					tokens += messageTokens[j] + " ";
				}
				return tokens;
			}
			return messageTokens[i];
		}
		return "";
	}

	public String getUser() {
		if (ircIn != null) { return ircIn.getUser(); }
		return null;
	}

	protected boolean isMatch() {
		return true;
	}

	protected boolean isValid() {
		return true;
	}

	protected void sendChatReply(String message) {
		listOut.add(new OutgoingIRCChatMessage(message, parent.channel));
	}

	public void setIRCMessage(IncomingIRCChatMessage in2) {
		ircIn = in2;
	}

	public void setParent(ProcBase parent, BlockingQueue<BaseOutgoingMessage> listOut) {
		this.parent = parent;
		this.listOut = listOut;
	}

	// Validation process for chat message
	public boolean validate() {
		String[] formatTokens = getFormatTokens().split(" ");
		String[] messageTokens = ircIn.getTokenList().clone();
		if (!messageTokens[0].startsWith(":")) { return false; }
		messageTokens[0] = messageTokens[0].substring(1);
		for (int i = 0; i < formatTokens.length; i++) {
			String formatToken = formatTokens[i];
			// 0 or more tokens remaining
			if (formatToken.equals("*")) {
				break;
			}
			// 1 or more tokens remaining
			if (formatToken.equals("+")) {
				if (messageTokens.length >= formatTokens.length) {
					break;
				}
			}
			// Optional, named
			if (formatToken.startsWith("#")) {
				continue;
			}
			// Only required remaining, so quit if no message tokens remaining
			if (messageTokens.length <= i) { return false; }
			String messageToken = messageTokens[i];
			// Required, named
			if (formatToken.startsWith("@")) {
				continue;
			}
			// Required, match only
			String[] possibleTokens = { formatToken };
			// Split out possible matchable tokens
			if (formatToken.contains("|")) {
				possibleTokens = formatToken.split("|");
			}
			// Check each token and fail if no match
			boolean found = false;
			for (String token : possibleTokens) {
				if (token.equalsIgnoreCase(messageToken)) {
					found = true;
				}
			}
			if (!found) { return false; }
		}
		if (!isMatch()) { return false; // if match info is invalid (custom)
		}
		if (!DataManager.hasPermission(parent.channel, ircIn.getUser(), getPermissionString())) { return false;
		// if
		// user
		// has
		// no
		// permissions
		}
		if (!isValid()) { return false; // if command has invalid parameters
										// (custom)
		}
		return true;
	}
}