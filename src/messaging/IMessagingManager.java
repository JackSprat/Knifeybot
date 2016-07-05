package messaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface IMessagingManager extends Runnable {

	public static List<IMessagingManager> managers = Collections.synchronizedList(new ArrayList<IMessagingManager>());
	
	public abstract String getChannel();

	public abstract void run();

	public abstract void newMessageToProc(IncomingMessage m);
	
	public abstract void newMessageToIRC(OutgoingMessage m);

}