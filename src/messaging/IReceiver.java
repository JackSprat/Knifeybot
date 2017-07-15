package messaging;

public interface IReceiver extends Runnable {
	
	@Override
	public abstract void run();

	public abstract void endExecution();

}