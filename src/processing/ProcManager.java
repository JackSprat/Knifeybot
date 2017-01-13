package processing;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import logger.Logger;
import messaging.IIncomingMessage;
import messaging.IncomingMessage;
import messaging.OutgoingMessage;
import processing.command.ProcCommand;
import processing.permissions.ProcPermissions;
import processing.pokemon.ProcPokemon;
import processing.quoter.ProcQuoter;
import processing.responder.ProcResponder;
import processing.roll.ProcRoll;
import processing.server.ProcInfo;
import processing.uptime.ProcUptime;

public class ProcManager extends Thread implements Runnable {

	private ArrayList<ProcBase> processors = new ArrayList<ProcBase>();
	private BlockingQueue<IncomingMessage> listIn;
	private boolean continueRunning = true;
	
	public ProcManager(BlockingQueue<IncomingMessage> listIn, BlockingQueue<OutgoingMessage> listOut, String channel) {
		Logger.INFO("Initialising ProcessManager for " + channel);
		
		//processors.add(ProcTimeConverter.getProcTimeConverter());
		processors.add(new ProcQuoter(listOut, channel));
		processors.add(new ProcRoll(listOut, channel));
		processors.add(new ProcLogin(listOut, channel));
		processors.add(new ProcPermissions(listOut, channel));
		processors.add(new ProcCommand(listOut, channel));
		processors.add(new ProcUptime(listOut, channel));
		//processors.add(new ProcHelp(listOut, channel));
		processors.add(new ProcInfo(listOut, channel));
		//processors.add(new ProcRepeat(listOut, channel));
		processors.add(new ProcPokemon(listOut, channel));
		processors.add(new ProcResponder(listOut, channel));
		
		//if (channel == "pokket") processors.add(new ProcPokemon(listOut, channel));
		
		for (ProcBase processor : processors) {
			for (CommandBase command : processor.getCommands()) {

				command.setParent(processor, listOut);
			}
			for (EventBase event : processor.getEvents()) {
				event.setParent(processor);
			}
		}
		
		this.listIn = listIn;
		
		Logger.INFO("Finished initialisation");
		
	}

	public void run() {

		ProcThread t = new ProcThread(processors);
		
		while (continueRunning) {
			try {
				
				IIncomingMessage message = listIn.poll(1, TimeUnit.SECONDS);
				t.process(message);
				message = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void endExecution() { continueRunning  = false; }
	
}
