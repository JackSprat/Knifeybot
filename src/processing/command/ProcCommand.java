package processing.command;

import java.util.concurrent.BlockingQueue;

import data.DataManager;
import messaging.OutgoingMessage;
import processing.ProcBase;
import state.ChannelState;
import users.UserManager;

public class ProcCommand extends ProcBase {
	
	public ProcCommand(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		
		commands.add(new CommandAddGlobal());
		commands.add(new CommandRemoveGlobal());
		commands.add(new CommandEditGlobal());
		commands.add(new CommandCommandView());
		
	}
	
	public String getCommand(String alias, String user) {
		
		String game = ChannelState.getCurrentGame(this.channel);
		
		String reply = DataManager.getCommand(channel, alias, game);
		
		if (reply != "" && reply != null) return reply;
		
		return null;
		
	}

}
