package processing;

import java.util.ArrayList;

import logger.Logger;
import messaging.IIncomingMessage;

public class ProcThread {

	private ArrayList<ProcBase> processors;
	
	public void process(IIncomingMessage in) {

		if (in != null) {
			processMessage(in);
		}
		
		tick();
		
	}
	
	public ProcThread (ArrayList<ProcBase> processors) {
		this.processors = processors;
	}
	
	private void processMessage(IIncomingMessage in) {
		
		for (ProcBase proc : processors) {
			try {
				proc.parseMessage(in);
			} catch (Exception e) {
				Logger.WARNING(e.getMessage());
				e.printStackTrace();
			}
		}

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