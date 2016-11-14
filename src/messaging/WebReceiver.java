package messaging;

import java.net.ServerSocket;
import java.net.Socket;

public class WebReceiver implements Runnable {

	@Override
	public void run() {
		while(true) {
			ServerSocket server;
			try {
				server = new ServerSocket(4732);
				logger.Logger.INFO("Web Service Created");
	            while (true) {
	                Socket p = server.accept();
	                logger.Logger.INFO("New connection recieved from " + p.getInetAddress().getHostAddress());
	                new Thread(new ServerThread(p)).start();
	            }
			} catch (Exception ex) {
				logger.Logger.STACK("Error with web service", ex);
			}
		}
	}
}