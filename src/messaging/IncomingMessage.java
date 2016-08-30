package messaging;

import java.util.Date;

public class IncomingMessage implements IIncomingMessage {

	public enum InType {
		IRCPING,
		IRCCHAT,
		IRCINFO,
		IRCJOIN,
		IRCPART,
		COMMAND,
		POKEMON,
		UNKNOWN
	}
	
	private InType type = InType.UNKNOWN;
	private String originalString = "NOSTRING";
	private String ID = "NOSTRING";
	private String user = "NOSTRING";
	private Date date;
	private String[] tokenList;
	
	public IncomingMessage(String message) {
		
		this.originalString = message;
		this.date = new Date();

		if (originalString.startsWith("POKE")) {
			
			String[] splitLines = originalString.split(" ");
			this.type = InType.POKEMON;
			
			if (splitLines[1].equals("ENCOUNTER")) {
				this.ID = "POKEMONENCOUNTER";
				this.user = "pokemon";
				this.tokenList = originalString.split(" ");
			}
			
		} else if (originalString.startsWith("PING")) {
			
			this.type = InType.IRCPING;
			
		} else if (originalString.startsWith(":")) {
		
			//IRCFormat: :user!user@user.tmi.twitch.tv PRIVMSG #channel :message
			String[] splitLines = originalString.split(":", 3);
			
			//PING message
			if (splitLines.length > 1) {
				
				String[] messageMetadata = splitLines[1].split(" ");
				
				//INFO message
				if (messageMetadata[0].equals("tmi.twitch.tv")) {
					
					this.type = InType.IRCINFO;
					this.tokenList = splitLines[2].split(" ");
					this.ID = messageMetadata[1];
					
				//CHAT message
				} else if (messageMetadata[1].equals("PRIVMSG")) {
					
					this.type = InType.IRCCHAT;
					this.tokenList = splitLines[2].split(" ");
					this.user = messageMetadata[0].split("!")[0];
					
				//UNKNOWN message	
				} else {
					
					this.type = InType.UNKNOWN;
					
				}
				
			//PART/JOIN message
			} else {
				
				String[] messageMetadata = splitLines[1].split(" ");
				
				if (messageMetadata[1].equals("JOIN")) {
					
					this.type = InType.IRCJOIN;
					this.user = messageMetadata[0].split("!")[0];
					
				//UNKNOWN message	
				} else if (messageMetadata[1].equals("PART")) {
					
					this.type = InType.IRCPART;
					this.user = messageMetadata[0].split("!")[0];
					
				//UNKNOWN message	
				} else {
					
					this.type = InType.UNKNOWN;
					
				}
				
			}
		}

	}
	
	/* (non-Javadoc)
	 * @see messaging.IIncomingMessage#getType()
	 */
	@Override
	public InType getType() {
		return type;
	}
	
	/* (non-Javadoc)
	 * @see messaging.IIncomingMessage#getUser()
	 */
	@Override
	public String getUser() {
		return user;
	}	
	
	/* (non-Javadoc)
	 * @see messaging.IIncomingMessage#getID()
	 */
	@Override
	public String getID() {
		return ID;
	}
	
	/* (non-Javadoc)
	 * @see messaging.IIncomingMessage#getDate()
	 */
	@Override
	public Date getDate() {
		return this.date;
	}
	
	/* (non-Javadoc)
	 * @see messaging.IIncomingMessage#getTokenList()
	 */
	@Override
	public String[] getTokenList() {
		if (tokenList == null) tokenList = originalString.split(" ");
		return tokenList;
	}//*/

}