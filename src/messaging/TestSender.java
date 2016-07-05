package messaging;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import logger.Logger;
import utils.TextUtils;

public class TestSender extends Thread implements Runnable, ISender {

	private BlockingQueue<OutgoingMessage> listOut;
	
	public TestSender(Socket socket, BlockingQueue<OutgoingMessage> listOut) {
		
		this.listOut = listOut;
		if (socket != null) {
			try { socket.close(); } catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	public void run() {
		
		while (true) {
			
			OutgoingMessage message = listOut.poll();
			
			if (message != null) {
				Logger.INFO(TextUtils.setLength("Snd: " + message.type.toString(), 15) + " - " + message.toString());

			}
			
			try { Thread.sleep(3000); } catch (InterruptedException e) { Logger.STACK("", e); }

		}
		
	}

	@Override
	public void setSocket(Socket s) {
		// TODO Auto-generated method stub
		
	}
	
}