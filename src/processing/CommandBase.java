package processing;

import java.util.concurrent.BlockingQueue;

import messaging.IIncomingMessage;
import messaging.OutgoingMessage;
import users.PermissionClass;
import users.UserManager;

public abstract class CommandBase {
	
	private IIncomingMessage in;
	protected ProcBase parent;
	
	public abstract String 			getPermissionString();
	public abstract PermissionClass getPermissionClass();
	public abstract String			getFormatTokens();
	public abstract String			getHelpString();

	public void setParent(ProcBase parent) {
		this.parent = parent;
	}
	protected boolean isMatch() { return true; }
	protected boolean isValid(BlockingQueue<OutgoingMessage> listOut) {	return true; }
	public abstract boolean execute(BlockingQueue<OutgoingMessage> listOut);
	
	
	
	//Validation process for chat message
	public boolean validate(BlockingQueue<OutgoingMessage> listOut) {
		
		String[] formatTokens = this.getFormatTokens().split(" ");
		String[] messageTokens = in.getTokenList().clone();
		
		if (!messageTokens[0].startsWith(":")) return false;
		
		messageTokens[0] = messageTokens[0].substring(1);
		
		for (int i = 0; i < formatTokens.length; i++) {
			
			String formatToken = formatTokens[i];
			
			System.out.println("Checking for token: " + formatToken);
			//0 or more tokens remaining
			if (formatToken.equals("*")) break;
			//1 or more tokens remaining
			if (formatToken.equals("+")) {
				if (messageTokens.length >= formatTokens.length) break;
				
			}
			
			//Optional, named
			if (formatToken.startsWith("#")) continue;
			
			//Only required remaining, so quit if no message tokens remaining
			if (messageTokens.length <= i) return false;
			
			String messageToken = messageTokens[i];
			
			//Required, named
			if (formatToken.startsWith("@")) continue;

			//Required, match only
			String[] possibleTokens = {formatToken};
			
			//Split out possible matchable tokens
			if (formatToken.contains("|")) possibleTokens = formatToken.split("|");
			
			//Check each token and fail if no match
			boolean found = false;
			for (String token : possibleTokens) {
				if (token.equalsIgnoreCase(messageToken)) found = true;
			}

			if (!found) return false;
		}
		
		System.out.println(this.toString());
		
		if (!isMatch()) 																	return false;	//if match info is invalid (custom)
		if (!UserManager.hasPermission(parent.channel, in.getUser(), 
				getPermissionString(), getPermissionClass().ordinal()))						return false;	//if user has no permissions
		if (!isValid(listOut))																return false;	//if command has invalid parameters (custom)
		
		return true;
		
	}
	
	public String getToken(String token) {
		
		String[] formatTokens = this.getFormatTokens().split(" ");
		String[] messageTokens = in.getTokenList();
		
		for (int i = 0; i < formatTokens.length; i++) {
			
			if (messageTokens.length <= i) return "";
			
			String formatToken = formatTokens[i];
			
			
			if (	!token.equalsIgnoreCase(formatToken) && 
					!token.equalsIgnoreCase(formatToken.substring(1))) continue;
			
			
			//0 or more tokens remaining
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
	
	public String getUser() { if (in != null) return in.getUser(); return null; }
	public void setMessage(IIncomingMessage in2) { this.in = in2; }

}