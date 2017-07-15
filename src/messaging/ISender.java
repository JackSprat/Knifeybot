package messaging;

public interface ISender extends Runnable {

	public abstract void endExecution();

	@Override
	public abstract void run();
}