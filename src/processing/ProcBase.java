package processing;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.incoming.BaseIncomingMessage;
import messaging.incoming.IncomingIRCChatMessage;
import messaging.incoming.IncomingWebsocketMessage;
import messaging.outgoing.BaseOutgoingMessage;
import utils.DirectoryUtils;


public abstract class ProcBase {

	protected ArrayList<WebsocketBase>				wscommands	= new ArrayList<WebsocketBase>();
	protected ArrayList<CommandBase>				commands	= new ArrayList<CommandBase>();
	protected ArrayList<EventBase>					events		= new ArrayList<EventBase>();
	protected BlockingQueue<BaseOutgoingMessage>	listOut;
	public String									channel;

	public ProcBase(BlockingQueue<BaseOutgoingMessage> listOut, String channel) {
		DirectoryUtils.createDirectories("data/" + channel + "/");
		this.listOut = listOut;
		this.channel = channel;
	}

	public ArrayList<CommandBase> getCommands() {
		return commands;
	}

	public ArrayList<EventBase> getEvents() {
		return events;
	}

	public ArrayList<WebsocketBase> getWebsocketCommands() {
		return wscommands;
	}

	private void parseIRCMessage(IncomingIRCChatMessage in) {
		for (CommandBase command : commands) {
			try {
				command.setIRCMessage(in);
				if (!command.validate()) {
					continue;
				}
				command.execute();
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void parseMessage(BaseIncomingMessage in) {
		if (in instanceof IncomingIRCChatMessage) {
			parseIRCMessage((IncomingIRCChatMessage) in);
		} else if (in instanceof IncomingWebsocketMessage) {
			parseWSMessage((IncomingWebsocketMessage) in);
		}
	}

	private void parseWSMessage(IncomingWebsocketMessage in) {
		for (WebsocketBase ws : wscommands) {
			try {
				ws.setWebsocketMessage(in);
				if (!ws.validate()) {
					continue;
				}
				ws.execute();
				break;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void tick() {
		for (EventBase event : events) {
			try {
				if (event.isTime()) {
					event.tick(listOut);
				}
			} catch (Exception e) {
				Logger.WARNING(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	// public void sendIRC() {
	// listOut.add(new OutgoingIRCMessage(OutType.RAW, "PASS " +
	// Constants.oAuthpass + "\r\n"));
	// }
}