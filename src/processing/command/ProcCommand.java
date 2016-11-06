package processing.command;

import java.util.concurrent.BlockingQueue;

import channel.DataManager;
import javafx.scene.Parent;
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
		System.out.println(reply);
		if (reply != "" && reply != null) return reply;
		System.out.println(reply);
		reply = DataManager.getCommand(channel, alias);
		System.out.println(reply);
		if (reply != "" && reply != null) return reply;
		
		return null;
		
	}

}
