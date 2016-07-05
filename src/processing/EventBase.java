package processing;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;

public abstract class EventBase {

	private static final long tickMulti = 1000;
	private long tickTimer = 5;
	private long lastTick = 0;
	protected ProcBase parent;
	
	protected abstract long getTickSeconds();
	protected abstract boolean tickSelf(BlockingQueue<OutgoingMessage> listOut);
	
	public boolean isTime() {
		if (lastTick + (tickMulti * Math.max(tickTimer, getTickSeconds())) < System.currentTimeMillis()) return true;
		return false;
	}
	public void setParent(ProcBase parent) {
		this.parent = parent;
	}
	public boolean tick(BlockingQueue<OutgoingMessage> listOut) {
		lastTick = System.currentTimeMillis();
		return tickSelf(listOut);
	}
	
}