package processing.twitter;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.EventBase;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class EventTweet extends EventBase {
	
	@Override
	protected long getTickSeconds() { return 10; }

	protected boolean tickSelf(BlockingQueue<OutgoingMessage> listOut) {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("IPDlVB4LDzYOugCzK8898SkSz")
		  .setOAuthConsumerSecret("3864EAxQmkzPwoyT8k26axxnqnk6kj0YARpnxf2xY5GUu5yowM")
		  .setOAuthAccessToken("140554068-cQx3KLXno8ObDHZBIAx4bF6ZBAjRfjEfg2fBJAug")
		  .setOAuthAccessTokenSecret("kqGkjoFdeDqIrfDMB9Obl1GMgD7uZlC0N1rCh0Zr5W2so");
		TwitterFactory tf = new TwitterFactory(cb.build());
		
		Twitter twitterController = tf.getInstance();
		
		
		List<TwitterObject> twitters = ((ProcTwitter)parent).getTwitters();
		for (TwitterObject twitter : twitters) {
			Logger.TRACE("Checking twitter: " + twitter.username);
	        try {
	            String status = twitterController.getUserTimeline(twitter.username).get(0).getText();
	            Logger.TRACE("Found tweet: " + status);
	            if (status.equalsIgnoreCase(twitter.lastTweet) || status.startsWith("@") || status.startsWith("RT")) continue;
	            status.replace("\n", " ");
	            listOut.add(new OutgoingMessage(OutType.CHAT, twitter.username + " tweeted: " + status, ((ProcTwitter)parent).channel));
	            
	            ((ProcTwitter)parent).saveLastTweet(twitter.username, status);
	        } catch (TwitterException te) {
	        	Logger.STACK("Failed to get timeline: ", te);
	        }
		}
		return true;
	}
	
}
