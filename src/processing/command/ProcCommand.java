package processing.command;

import java.util.concurrent.BlockingQueue;

import channel.DataManager;
import messaging.OutgoingMessage;
import processing.ProcBase;
import users.UserManager;

public class ProcCommand extends ProcBase {
	
	public ProcCommand(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		
		commands.add(new CommandAdd());
		commands.add(new CommandRemove());
		commands.add(new CommandEdit());
		commands.add(new CommandAddGlobal());
		commands.add(new CommandRemoveGlobal());
		commands.add(new CommandEditGlobal());
		commands.add(new CommandCommandView());
		
	}
	
	public String getCommand(String alias, String user) {

		String reply = UserManager.getCommand(user, alias);
		
		if (reply != "" && reply != null) return reply;
		
		reply = DataManager.getCommand(channel, alias);
		
		if (reply != "" && reply != null) return reply;
		
		return null;
		
	}

}
