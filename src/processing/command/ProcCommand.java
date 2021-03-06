package processing.command;

import java.util.concurrent.BlockingQueue;

import data.DataManager;
import messaging.outgoing.BaseOutgoingMessage;
import processing.ProcBase;
import state.ChannelState;

public class ProcCommand extends ProcBase {
	
	public ProcCommand(BlockingQueue<BaseOutgoingMessage> listOut, String channel) {
		
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

	
	
	public void setCounter(String alias, int value) {
		DataManager.setCounter(this.channel, alias, value);
	}

}
