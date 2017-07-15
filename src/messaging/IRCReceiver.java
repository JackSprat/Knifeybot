package messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.incoming.BaseIncomingMessage;
import utils.TextUtils;


public class IRCReceiver implements IReceiver {

	private BufferedReader						reader;
	private BlockingQueue<BaseIncomingMessage>	messageList;
	private boolean								continueRunning	= true;

	public IRCReceiver(Socket socket, BlockingQueue<BaseIncomingMessage> messageFromIRC) {
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException io) {
			Logger.STACK("Error accessing socket input stream", io);
		}
		messageList = messageFromIRC;
	}

	@Override
	public void endExecution() {
		continueRunning = false;
	}

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
				BaseIncomingMessage m = BaseIncomingMessage.constructMessage(line);
				Logger.INFO(TextUtils.setLength("Rec: " + m.getTypeString(), 15) + " - " + m.getOriginalString());
				messageList.add(m);
			}
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				Logger.STACK("", e);
			}
		}
	}
}