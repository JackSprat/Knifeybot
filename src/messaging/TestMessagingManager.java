package messaging;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import logger.Logger;
import processing.ProcManager;

public class TestMessagingManager implements IMessagingManager {
	
	private String channel;
	public ProcManager p;
	BlockingQueue<IncomingMessage> messageFromIRC = 	new LinkedBlockingQueue<IncomingMessage>();
	public BlockingQueue<IncomingMessage> messageToProc = 	new LinkedBlockingQueue<IncomingMessage>();
	BlockingQueue<OutgoingMessage> messageFromProc = new LinkedBlockingQueue<OutgoingMessage>();
	BlockingQueue<OutgoingMessage> messageToTwitter = new LinkedBlockingQueue<OutgoingMessage>();
	BlockingQueue<OutgoingMessage> messageToIRC = 	new LinkedBlockingQueue<OutgoingMessage>();

	public ISender 		ircSender = new TestSender(null, messageToIRC);
	
	public TestMessagingManager(String channel) {
		this.channel = channel;
	}
	
	public void run() {
		
		Logger.DEBUG("Creating IRC and processor threads");
		p = new ProcManager(	messageToProc, messageFromProc, this.channel);
		
		((TestSender) ircSender).start();
		p.start();
		
		Logger.DEBUG("Entering message passing loop");
		
		while (true) {
			
			OutgoingMessage messageProcOut = messageFromProc.poll();

			if (messageProcOut != null) {
				
				switch(messageProcOut.type) {
					case CHAT: case PONG: case RAW:
						messageToIRC.add(messageProcOut); break;
					default:
						break;
				}
				
			}
			
			try { Thread.sleep(25); } catch (InterruptedException e) { Logger.STACK("", e); }

		}
		
	}
	
	public void newMessageToProc(IncomingMessage m) { messageToProc.add(m); }
	
	@Override
	public String getChannel() { return channel; }

	@Override
	public void newMessageToIRC(OutgoingMessage m) { Logger.INFO(m.toString()); }
	
}
