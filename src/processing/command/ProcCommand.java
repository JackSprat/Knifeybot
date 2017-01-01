package processing.command;

import java.util.concurrent.BlockingQueue;

import data.DataManager;
import messaging.OutgoingMessage;
import processing.ProcBase;
import state.ChannelState;

public class ProcCommand extends ProcBase {
	
	public ProcCommand(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		
		commands.add(new CommandAdd());
		commands.add(new CommandRemove());
		commands.add(new CommandUpdateCounter());
		commands.add(new CommandView());
		
	}
	
	public String getCommand(String alias) {
		
		String game = ChannelState.getCurrentGame(this.channel);
		
		String reply = DataManager.getCommand(channel, alias, game);
		
		if (reply != "" && reply != null) return reply;
		
		return null;
		
	}

	public int getCounter(String alias) {
		String game = ChannelState.getCurrentGame(this.channel);
		
		int counter = DataManager.getCounter(this.channel, alias, game);
		
		return counter;
	}
	
	public void setCounter(String alias, int value) {
		DataManager.setCounter(this.channel, alias, value);
	}

}
