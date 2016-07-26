package channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.simpleframework.xml.*;

import processing.quoter.Quote;
import processing.server.GameObject;

@Root
public class ChannelData {
	
	@ElementMap(entry="reply", key="alias", attribute=true, inline=false, required = false)
	private Map<String, String> commandMap = new HashMap<String, String>();

	@ElementMap(entry="quote", key="ID", attribute=true, inline=false, required = false)
	private Map<Long, Quote> quoteMap = new HashMap<Long, Quote>();
	
	@ElementMap(entry="gameobject", key="game", attribute=true, inline=false, required = false)
	private Map<String, GameObject> gameMap = new HashMap<String, GameObject>();
	
	public void addCommand(String alias, String reply) {
		commandMap.put(alias, reply);
	}
	public void removeCommand(String alias) {
		commandMap.remove(alias);
	}
	public String getCommand(String alias) {
		// TODO Auto-generated method stub
		return commandMap.get(alias);
	}
	
	public void addQuote(String alias, String username, String quote) {
		Long counter = (long) 0;
		while (true) {
			if (!quoteMap.containsKey(counter)) {
				Quote q = new Quote();
				q.username = username;
				q.alias = alias;
				q.quote = quote;
				quoteMap.put(counter, q);
				return;
			}
			counter++;
		}
		
		
	}
	public void removeQuote(Long ID) {
		quoteMap.remove(ID);
	}
	public String getQuote(String alias) {
		List<Long> quotes = new ArrayList<Long>();
		for (long q : quoteMap.keySet()) {
			if (quoteMap.get(q).alias.equalsIgnoreCase(alias) || quoteMap.get(q).username.equalsIgnoreCase(alias)) quotes.add(q);
		}
		int randint = (int)(Math.random()*quotes.size());
		
		return quoteMap.get(quotes.get(randint)).toString(quotes.get(randint));
	}
	public String getQuote(Long ID) {
		// TODO Auto-generated method stub
		return quoteMap.get(ID).toString(ID);
	}
	public String getQuote() {
		
		List<Long> quotes = new ArrayList<Long>();
		for (long q : quoteMap.keySet()) {
			quotes.add(q);
		}
		
		int i = (int) (Math.random() * quotes.size());
		
		return getQuote(quotes.get(i));
		
	}
	
	public String getGameAttribute(String game, String attribute) {
		return gameMap.get(game).getAttribute(attribute);
	}
	public void addGameAttribute(String game, String attribute, String value) {
		if (!gameMap.containsKey(game)) {
			GameObject g = new GameObject();
			g.setGame(game);
			g.setAttribute(attribute, value);
			gameMap.put(game, g);
		}
		
	}
}