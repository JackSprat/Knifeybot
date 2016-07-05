package messaging;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import logger.Logger;
import processing.ProcManager;
import utils.Constants;

public class MessagingManager implements IMessagingManager {
	
	private String channel;
	private ProcManager p;
	private ISender ircSender;
	private IReceiver ircReceiver;
	private BlockingQueue<IncomingMessage> messageFromIRC = 	new LinkedBlockingQueue<IncomingMessage>();
	private BlockingQueue<IncomingMessage> messageToProc = 	new LinkedBlockingQueue<IncomingMessage>();
	private BlockingQueue<OutgoingMessage> messageFromProc = new LinkedBlockingQueue<OutgoingMessage>();
	private BlockingQueue<OutgoingMessage> messageToIRC = 	new LinkedBlockingQueue<OutgoingMessage>();
	
	private boolean continueRunning = true;
	private long lastMessage = System.currentTimeMillis();
	
	public MessagingManager(String channel) {
		this.channel = channel;
	}

	@Override
	public void run() {
		
		Socket socket = getNewSocket();
		
		Logger.DEBUG("Creating IRC and processor threads");
		ircSender	= new Sender( socket, messageToIRC);
		ircReceiver = new Receiver( socket, messageFromIRC);
		p = new ProcManager( messageToProc, messageFromProc, this.channel);
		
		new Thread(ircSender).start();
		new Thread(ircReceiver).start();
		p.start();
		
		Logger.DEBUG("Entering message passing loop");
		
		while (continueRunning) {

			IncomingMessage messageIRCIn = messageFromIRC.poll();

			if (messageIRCIn != null) {
				
				switch(messageIRCIn.getType()) {
					case IRCPING: case IRCINFO: case IRCCHAT: case IRCJOIN: case IRCPART: case COMMAND: case POKEMON:
						lastMessage = System.currentTimeMillis();
						messageToProc.add(messageIRCIn); break;
					default:
						break;
				}
				
			}
			
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

			if (lastMessage > 5*60*1000 + System.currentTimeMillis()) {
				
				continueRunning = false;
				
			}
			
			if (!ircReceiver.continueRunning()) {
				continueRunning = false;
			}
			
		}
		
		ircSender.endExecution();
		ircReceiver.endExecution();
		p.endExecution();
		
		IMessagingManager m = new MessagingManager(this.channel);
		IMessagingManager.managers.add(m);
		new Thread(m).start();
		
	}
	
	@Override
	public void newMessageToProc(IncomingMessage m) {
		messageToProc.add(m);
	}
	
	private Socket getNewSocket() {
		
		Socket socket = null;
		
		try {

	        socket = new Socket(Constants.server, Constants.port);
		} catch (Exception e) {
			Logger.STACK("Error creating socket", e);
		}
		
		return socket;
	}

	@Override
	public String getChannel() { return channel; }

	@Override
	public void newMessageToIRC(OutgoingMessage m) { messageToIRC.add(m); }
	
}
