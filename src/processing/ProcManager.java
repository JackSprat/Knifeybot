package processing;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import logger.Logger;
import messaging.IIncomingMessage;
import messaging.IncomingMessage;
import messaging.OutgoingMessage;
import processing.command.ProcCommand;
import processing.help.ProcHelp;
import processing.permissions.ProcPermissions;
import processing.pokemon.ProcPokemon;
import processing.quoter.ProcQuoter;
import processing.repeat.ProcRepeat;
import processing.responder.ProcResponder;
import processing.roll.ProcRoll;
import processing.server.ProcInfo;
import processing.twitter.ProcTwitter;
import processing.uptime.ProcUptime;

public class ProcManager extends Thread implements Runnable {

	private ArrayList<ProcBase> processors = new ArrayList<ProcBase>();
	private BlockingQueue<IncomingMessage> listIn;
	private BlockingQueue<OutgoingMessage> listOut;
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
		processors.add(new ProcHelp(listOut, channel));
		processors.add(new ProcInfo(listOut, channel));
		processors.add(new ProcRepeat(listOut, channel));
		processors.add(new ProcPokemon(listOut, channel));
		processors.add(new ProcResponder(listOut, channel));
		
		//if (channel == "pokket") processors.add(new ProcPokemon(listOut, channel));
		
		for (ProcBase processor : processors) {
			for (CommandBase command : processor.getCommands()) {

				command.setParent(processor);
			}
			for (EventBase event : processor.getEvents()) {
				event.setParent(processor);
			}
		}
		
		this.listIn = listIn;
		this.listOut = listOut;
		
		Logger.INFO("Finished initialisation");
		
	}

	public void run() {
		
		outputHelpFile();
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
	
	private void outputHelpFile() {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get("default.html"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String htmlString = null;
		try {
			htmlString = new String(encoded, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String title = "New Page";
		String body = "";
		for (ProcBase processor : processors) {
			body += "<p>" + processor.getClass().getSimpleName() + "<br />";
			for (CommandBase command : processor.getCommands()) {
				body += "<p>" + command.getClass().getSimpleName() + " - ";
				body += command.getHelpString().replace("<", "&lt;").replace(">", "&gt;") + "<br />";
				body += "Format: " + command.getFormatString().replace("<", "&lt;").replace(">", "&gt;") + "<br />";
				body += "Required Permissions: " + command.getPermissionString() + "</p>";
			}
		}
		
		htmlString = htmlString.replace("$title", title);
		htmlString = htmlString.replace("$body", body);
		FileWriter writer;
		try {
			writer = new FileWriter("\\\\HTPC\\System\\WAMP\\wamp\\www\\knifeybot\\help.html", false);
			writer.write(htmlString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void endExecution() { continueRunning  = false; }
	
}
