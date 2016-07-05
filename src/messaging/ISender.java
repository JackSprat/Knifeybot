package messaging;

import java.net.Socket;

public interface ISender extends Runnable {

	public abstract void setSocket(Socket s);

	public abstract void run();

	public abstract void endExecution();

}