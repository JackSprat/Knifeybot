package processing;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import logger.Logger;
import messaging.incoming.BaseIncomingMessage;
import messaging.outgoing.BaseOutgoingMessage;
import processing.command.ProcCommand;
import processing.functions.ProcFunctions;
import processing.permissions.ProcPermissions;
import processing.points.ProcPoints;
import processing.quoter.ProcQuoter;
import processing.subs.ProcSubscriptions;
import processing.uptime.ProcUptime;


public class ProcManager extends Thread implements Runnable {

	private ArrayList<ProcBase>					processors		= new ArrayList<ProcBase>();
	private BlockingQueue<BaseIncomingMessage>	listIn;
	private boolean								continueRunning	= true;

	public ProcManager(BlockingQueue<BaseIncomingMessage> listIn, BlockingQueue<BaseOutgoingMessage> listOut,
			String channel) {
		Logger.INFO("Initialising ProcessManager for " + channel);
		processors.add(new ProcQuoter(listOut, channel));
		processors.add(new ProcFunctions(listOut, channel));
		processors.add(new ProcPermissions(listOut, channel));
		processors.add(new ProcCommand(listOut, channel));
		processors.add(new ProcUptime(listOut, channel));
		processors.add(new ProcPoints(listOut, channel));
		processors.add(new ProcSubscriptions(listOut, channel));
		// processors.add(new ProcPokemon(listOut, channel));
		for (ProcBase processor : processors) {
			for (CommandBase command : processor.getCommands()) {
				command.setParent(processor, listOut);
			}
			for (EventBase event : processor.getEvents()) {
				event.setParent(processor);
			}
			for (WebsocketBase wscommand : processor.getWebsocketCommands()) {
				wscommand.setParent(processor, listOut);
			}
		}
		this.listIn = listIn;
		Logger.INFO("Finished initialisation");
	}

	public void endExecution() {
		continueRunning = false;
	}

	@Override
	public void run() {
		ProcThread t = new ProcThread(processors);
		while (continueRunning) {
			try {
				BaseIncomingMessage message = listIn.poll(1, TimeUnit.SECONDS);
				t.process(message);
				message = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
