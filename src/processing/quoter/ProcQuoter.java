package processing.quoter;

import java.util.concurrent.BlockingQueue;

import channel.DataManager;
import messaging.OutgoingMessage;
import processing.ProcBase;

public class ProcQuoter extends ProcBase {
	
	public ProcQuoter(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		commands.add(new CommandQuoteView());
		commands.add(new CommandQuoteViewMulti());
		commands.add(new CommandQuoteAdd());
		commands.add(new CommandQuoteRemove());
	}
	
	public String getQuote(String alias) {

		try {
			int id = Integer.parseInt(alias);
			return DataManager.getQuote(channel, (long)id);
		} catch (NumberFormatException nfe) {
			return DataManager.getQuote(channel, alias);
		}
		
	}

}