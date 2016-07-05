package messaging;

import java.net.Socket;

public interface IReceiver extends Runnable {
	
	public abstract void setSocket(Socket s);

	public abstract void run();

	public abstract void endExecution();
	
	public abstract boolean continueRunning();

}