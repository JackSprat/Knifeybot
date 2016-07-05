package processing;

import java.util.concurrent.BlockingQueue;

import messaging.IIncomingMessage;
import messaging.OutgoingMessage;
import messaging.TokenList;
import users.PermissionClass;
import users.UserManager;
import utils.Constants;

public abstract class CommandBase {
	
	private IIncomingMessage in;
	protected ProcBase parent;
	
	public abstract String 			getPermissionString();
	public abstract PermissionClass getPermissionClass();
	public abstract String			getFormatString();
	public abstract String			getHelpString();

	public void setParent(ProcBase parent) {
		this.parent = parent;
	}
	protected boolean isMatch() { return true; }
	protected boolean isValid(BlockingQueue<OutgoingMessage> listOut) {	return true; }
	public abstract boolean execute(BlockingQueue<OutgoingMessage> listOut);
	
	
	
	//Validation process for chat message
	public boolean validate(BlockingQueue<OutgoingMessage> listOut) {
		
		TokenList formatTokens = new TokenList(this.getFormatString());
		for (int i = 0; i < formatTokens.length(); i++) {
			
			if (i >= this.getTokenLength()) return false;
			
			String s = formatTokens.getToken(i);
			if ((s.startsWith("<") || s.startsWith(":<")) && s.endsWith(">")) {
				//Required, named
				if (in.getTokenList().length() < i) return false;
			} else if (s.startsWith("[") && s.endsWith("]")) {
				//optional, named
				continue;
			} else if (s.equals("...")) {
				//optional, rest of command
				continue;
			} else {
				//required, fixed
				if (in.getTokenList().length() < i) return false;
				if (!in.getTokenList().getToken(i).equalsIgnoreCase(formatTokens.getToken(i))) return false;
			}
		}
		
		if (!isMatch()) 																	return false;	//if match info is invalid (custom)
		if (!UserManager.hasPermission(parent.channel, in.getUser(), 
				getPermissionString(), getPermissionClass().ordinal()))						return false;	//if user has no permissions
		if (!isValid(listOut))																return false;	//if command has invalid parameters (custom)
		
		return true;
		
	}
	
	public String getUser() { if (in != null) return in.getUser(); return null; }
	public void setMessage(IIncomingMessage in2) { this.in = in2; }
	public String getToken(String tokenName) {

		TokenList formatTokens = new TokenList(this.getFormatString());
		for (int i = 0; i < formatTokens.length(); i++) {
			if (i >= this.getTokenLength()) return "";
			if (formatTokens.getToken(i).contains(tokenName)) {
				if (tokenName.equals("...")) {
					String s = "";
					for (int rest = i; rest < in.getTokenList().length(); rest++) {
						s += " " + in.getTokenList().getToken(rest);
					}
					
					return s;
				}
				
				return in.getTokenList().getToken(i);
			}
			
		}
		
		return "";
	}

	private int getTokenLength() {
		return this.in.getTokenList().length();
	}

}