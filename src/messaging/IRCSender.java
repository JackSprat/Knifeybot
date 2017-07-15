package messaging;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.outgoing.BaseOutgoingMessage;
import messaging.outgoing.OutgoingIRCMessage;
import utils.TextUtils;


public class IRCSender implements ISender {

	private BlockingQueue<OutgoingIRCMessage>	listOut;
	private BufferedWriter						writer;
	private boolean								continueRunning	= true;
	private Set<Long>							messageTimes	= new HashSet<Long>();

	public IRCSender(Socket socket, BlockingQueue<OutgoingIRCMessage> listOut) {
		this.listOut = listOut;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (IOException io) {
			Logger.STACK("Error creating IRC socket writer", io);
		}
	}

	@Override
	public void endExecution() {
		continueRunning = false;
	}

	@Override
	public void run() {
		while (continueRunning) {
			BaseOutgoingMessage message = listOut.peek();
			if (message instanceof OutgoingIRCMessage) {
				message = listOut.poll();
			}
			Iterator<Long> i = messageTimes.iterator();
			while (i.hasNext()) {
				Long time = i.next();
				if ((System.currentTimeMillis() - (30 * 1000)) > time) {
					i.remove();
				}
			}
			if ((message != null) && (messageTimes.size() < 30)) {
				Logger.INFO(TextUtils.setLength("Snd: " + message.getTypeString(), 15) + " - " + message.toString());
				String m = message.toString();
				try {
					writer.write(m);
					writer.flush();
				} catch (SocketException socket) {
					Logger.STACK("Error writing to IRC socket, reconnecting...", socket);
				} catch (IOException ioex) {
					Logger.STACK("Error writing to IRC socket", ioex);
				}
				messageTimes.add(System.currentTimeMillis());
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Logger.STACK("", e);
			}
		}
	}

	public synchronized void setSocket(Socket s) {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
		} catch (IOException io) {
			Logger.STACK("Error creating IRC socket writer", io);
		}
	}
}