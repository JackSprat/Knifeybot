package messaging;

public interface IChannelHandler extends Runnable {
	
	public abstract String getChannel();

	@Override
	public abstract void run();
	
	public abstract void terminateExecution();

}