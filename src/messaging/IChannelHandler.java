package messaging;

public interface IChannelHandler extends Runnable {
	
	public abstract String getChannel();

	public abstract void run();
	
	public abstract void terminateExecution();

}