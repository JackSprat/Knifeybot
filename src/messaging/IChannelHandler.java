package messaging;

public interface IChannelHandler extends Runnable {
	
	public abstract String getChannel();

	public abstract void run();

	public abstract void newMessageToProc(IncomingMessage m);
	
	public abstract void newMessageToIRC(OutgoingMessage m);
	
	public abstract void terminateExecution();

}