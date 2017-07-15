package processing.subs;

import data.DataManager;
import messaging.incoming.IncomingWebsocketSubMessage;
import processing.WebsocketBase;
import utils.CommandParser;


public class WebsocketSubNotify extends WebsocketBase {

	@Override
	public void execute() {
		IncomingWebsocketSubMessage ws = (IncomingWebsocketSubMessage) wsIn;
		String user = ws.getUser();
		int months = ws.getMonths();
		String messageToParse = DataManager.getSubMessage(parent.channel, months);
		String parsedMessage = new CommandParser(messageToParse).setChannel(parent.channel).setUsername(user)
				.setSubMonths(months).parse();
		if (parsedMessage.equals("")) {
			parsedMessage = (months < 2 ? "Thanks for the new sub, " : "Thanks for the resub, ") + user + "!";
		}
		sendChatReply(parsedMessage);
	}

	@Override
	public boolean isMatch() {
		return wsIn instanceof IncomingWebsocketSubMessage;
	}
}
