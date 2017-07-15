package processing;

import java.util.ArrayList;

import logger.Logger;
import messaging.incoming.BaseIncomingMessage;


public class ProcThread {

	private ArrayList<ProcBase> processors;

	public ProcThread(ArrayList<ProcBase> processors) {
		this.processors = processors;
	}

	public void process(BaseIncomingMessage in) {
		if (in != null) {
			for (ProcBase proc : processors) {
				try {
					proc.parseMessage(in);
				} catch (Exception e) {
					Logger.WARNING(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		tick();
	}

	private void tick() {
		for (ProcBase proc : processors) {
			try {
				proc.tick();
			} catch (Exception e) {
				Logger.WARNING(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}