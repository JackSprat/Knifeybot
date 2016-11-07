package processing;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.IIncomingMessage;
import messaging.OutgoingMessage;
import utils.DirectoryUtils;

public abstract class ProcBase {

	protected ArrayList<CommandBase> commands = new ArrayList<CommandBase>();
	protected ArrayList<EventBase> events = new ArrayList<EventBase>();

	protected BlockingQueue<OutgoingMessage> listOut;
	public String channel;
	
	public ProcBase(BlockingQueue<OutgoingMessage> listOut, String channel) {
		DirectoryUtils.createDirectories("data/" + channel + "/");
		
		this.listOut = listOut;
		this.channel = channel;
	}
	
	public ArrayList<CommandBase> getCommands() { return commands; }
	
	public ArrayList<EventBase> getEvents() { return events; }
	
	public void parseMessage(IIncomingMessage in) {
		
		for (CommandBase command : commands) {
			
			try {
				command.setMessage(in);
				if (!command.validate()) continue;
				command.execute();
				continue;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}
		
	}
	
	public void tick() {
		for (EventBase event : events) {
			try {
				if (event.isTime()) event.tick(listOut);
			} catch (Exception e) {
				Logger.WARNING(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	//public void sendIRC() {
	//	listOut.add(new OutgoingIRCMessage(OutType.RAW, "PASS " + Constants.oAuthpass + "\r\n"));
	//}
	
}