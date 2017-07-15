package processing;

import java.util.concurrent.BlockingQueue;

import messaging.outgoing.BaseOutgoingMessage;


public abstract class EventBase {

	private static final long	tickMulti	= 1000;
	private long				tickTimer	= 5;
	private long				lastTick	= 0;
	protected ProcBase			parent;

	protected abstract long getTickSeconds();

	public boolean isTime() {
		if ((lastTick + (tickMulti * Math.max(tickTimer, getTickSeconds()))) < System
				.currentTimeMillis()) { return true; }
		return false;
	}

	public void setParent(ProcBase parent) {
		this.parent = parent;
	}

	public void tick(BlockingQueue<BaseOutgoingMessage> listOut) {
		lastTick = System.currentTimeMillis();
		tickSelf(listOut);
	}

	protected abstract void tickSelf(BlockingQueue<BaseOutgoingMessage> listOut);
}