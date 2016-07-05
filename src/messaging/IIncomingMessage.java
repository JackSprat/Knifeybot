package messaging;

import java.util.Date;

import messaging.IncomingMessage.InType;

public interface IIncomingMessage {

	public abstract InType getType();

	public abstract String getUser();

	public abstract String getID();

	public abstract Date getDate();

	public abstract TokenList getTokenList();

}