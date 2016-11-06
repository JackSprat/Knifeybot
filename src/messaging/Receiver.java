package messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.IncomingMessage.InType;
import utils.TextUtils;

public class Receiver implements IReceiver {

	private BufferedReader reader;
	private BlockingQueue<IncomingMessage> messageList;
	private boolean continueRunning = true;
	
	public Receiver(Socket socket, BlockingQueue<IncomingMessage> messageFromIRC) {
		
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException io) {
			Logger.STACK("Error accessing socket input stream", io);
		}
		
		messageList = messageFromIRC;
		
	}
	
	/* (non-Javadoc)
	 * @see messaging.IReceiver#setSocket(java.net.Socket)
	 */
	@Override
	public synchronized void setSocket(Socket s) { 
		try {
			reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException io) {
			Logger.STACK("Error creating IRC socket writer", io);
		}
	}

	/* (non-Javadoc)
	 * @see messaging.IReceiver#run()
	 */
	@Override
	public void run() {

		while (continueRunning) {
			
			String line = null;
			
			try {
				line = reader.readLine();
			} catch (SocketException sock) {
				Logger.STACK("Error reading from IRC socket", sock);
				continueRunning = false;
			} catch (IOException e) {
				Logger.STACK("", e);
				continueRunning = false;
			}
			
			if (line != null) {
				IncomingMessage m = new IncomingMessage(line);
				if (m.getType() != InType.UNKNOWN) Logger.INFO(TextUtils.setLength("Rec: " + m.getType().toString(), 15) + " - " + m.getUser() + " - " + String.join(" ", m.getTokenList()));
				messageList.add(m);
				
			}
			
			try { Thread.sleep(25); } catch (InterruptedException e) { Logger.STACK("", e); }
				
		}
		
	}

	@Override
	public void endExecution() {
		continueRunning = false;
		
	}

	@Override
	public boolean continueRunning() {
		// TODO Auto-generated method stub
		return continueRunning;
	}
	
}
