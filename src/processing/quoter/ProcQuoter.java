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
	
	public void updateList() {
		/*
		List<Quote> quotes = getQuotes();
		List<Quote> quoteList = new ArrayList<Quote>(quotes);
		try {

			byte[] encoded = Files.readAllBytes(Paths.get("default.html"));
			String htmlString = new String(encoded, "UTF-8");
			
			String title = "New Page";
			String body = "";
			for (Quote q : quoteList) {
				body += "<p>" + q.toString() + "</p>";
			}
			
			htmlString = htmlString.replace("$title", title);
			htmlString = htmlString.replace("$body", body);
			FileWriter writer = new FileWriter("\\\\HTPC\\System\\WAMP\\wamp\\www\\knifeybot\\" + channel + "\\quotes.html", false);
			writer.write(htmlString);
			writer.close();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}