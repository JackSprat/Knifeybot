package processing.twitter;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.db4o.query.Predicate;

import messaging.OutgoingMessage;
import processing.ProcBase;

public class ProcTwitter extends ProcBase {
	
	public ProcTwitter(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		events.add(new EventTweet());
		
		commands.add(new CommandAddTwitter());
		commands.add(new CommandRemoveTwitter());
	}
	
	public boolean addTwitter(String user) {
		List<TwitterObject> twitters = database.query(new Predicate<TwitterObject>() {
		    public boolean match(TwitterObject str) {
		        return str.username.equalsIgnoreCase(user);
		    }
		});
		
		if (twitters == null || twitters.size() > 0) {
			return false;
		}
		TwitterObject twitter = new TwitterObject();
		twitter.username = user;
		database.store(twitter);
		database.commit();
		return true;
	}
	
	public boolean removeTwitter(String user) {
		List<TwitterObject> twitters = database.query(new Predicate<TwitterObject>() {
		    public boolean match(TwitterObject str) {
		        return str.username.equalsIgnoreCase(user);
		    }
		});
		
		if (twitters == null || twitters.size() > 0) {
			database.delete(twitters.get(0));
			database.commit();
			return true;
		}
		return true;
	}
	
	public List<TwitterObject> getTwitters() {
		return database.query(new Predicate<TwitterObject>() {
		    public boolean match(TwitterObject str) {
		        return true;
		    }
		});
	}
	
	public void saveLastTweet(String user, String tweet) {
		List<TwitterObject> twitters = database.query(new Predicate<TwitterObject>() {
		    public boolean match(TwitterObject str) {
		        return user.equalsIgnoreCase(str.username);
		    }
		});
		
		if (twitters != null && twitters.size() > 0) {
			TwitterObject twitter = twitters.get(0);
			twitter.lastTweet = tweet;
			database.store(twitter);
			database.commit();
		}
	}
	
}
