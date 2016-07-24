package processing.responder;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.ProcBase;
import users.UserManager;

public class ProcResponder extends ProcBase {

	private Set<String> names = new HashSet<String>();
	
	public ProcResponder(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		commands.add(new CommandUnicorn());
		commands.add(new CommandResub());
		commands.add(new CommandSub());
		commands.add(new CommandActiveCounter());
		commands.add(new CommandPoints());
		
		events.add(new EventTickActive());
		
	}
	
	public synchronized void addUserToSet(String username) {
		names.add(username);
	}

	public void update() {

		for (String s : names) {
			UserManager.addPoints(channel, s, 1);
		}
		
		names.clear();
	}
	
	public int getPoints(String username) {
		return UserManager.getPoints(channel, username);
	}

}
