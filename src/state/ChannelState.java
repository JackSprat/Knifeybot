package state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import logger.Logger;
import messaging.IncomingMessage;

public class ChannelState {

	private static Map<String, ChannelState> channels = new HashMap<String, ChannelState>();
	
	protected Map<String, Long> newSubs = new HashMap<String, Long>();
	protected ArrayList<IncomingMessage> messagesIn = new ArrayList<IncomingMessage>();
	protected boolean isLive = false;
	protected int deaths = 0;
	private long lastChannelMessage = 0;
	
	protected long lastStreamEnd = 0;

	private ChannelState() {
		
	}
	
	public static synchronized void registerChannel(String channelName) {
		Logger.INFO("Registering ChannelState for " + channelName);
		channels.put(channelName, new ChannelState());
	}
	
	public static synchronized void newSubNotify (String channelName, String username) {
		Logger.INFO("Notifying New Sub for " + channelName);
		ChannelState channel = channels.get(channelName);
		
		if(channel.isLive || channels.get(channelName).lastStreamEnd + 1000*60*30 > System.currentTimeMillis()) {
			channels.get(channelName).newSubs.put(username, System.currentTimeMillis());
		}
	}
	
	public static synchronized void setStreamLive(String channelName, boolean streamLive) {
		Logger.DEBUG("Setting ChannelState for " + channelName + ", " + streamLive);
		boolean previouslyLive = channels.get(channelName).isLive;
		channels.get(channelName).isLive = streamLive;
		
		if (streamLive && !previouslyLive) {
			Logger.INFO("Clearing sublist for " + channelName);
			if (channels.get(channelName).lastStreamEnd + 1000*60*30 < System.currentTimeMillis()) {
				channels.get(channelName).newSubs.clear();
			}
		} else if (!streamLive && previouslyLive) {
			Logger.INFO("Setting streamend for " + channelName);
			channels.get(channelName).lastStreamEnd = System.currentTimeMillis();
		}
	}
	
	public static synchronized int getNewSubCount (String channelName) {
		Logger.INFO("Getting subcount for " + channelName);
		return channels.get(channelName).newSubs.size();
	}
	
	public static synchronized void newMessageNotify (String channelName, IncomingMessage message) {
		channels.get(channelName).messagesIn.add(message);
		channels.get(channelName).lastChannelMessage = System.currentTimeMillis();
	}
	
	public static synchronized long getLastMessageTimeAgo (String channelName) {
		long time = channels.get(channelName).lastChannelMessage;
		return System.currentTimeMillis() - time;
	}
	
	public static synchronized ArrayList<IncomingMessage> retrieveMessages (String channelName) {
		ArrayList<IncomingMessage> messages = (ArrayList<IncomingMessage>) channels.get(channelName).messagesIn.clone();
		channels.get(channelName).messagesIn.clear();
		return messages;
	}
	
	public static synchronized void addDeaths(String channelName, int value) {
		int previousDeaths = channels.get(channelName).deaths;
		channels.get(channelName).deaths = previousDeaths + value;
	}
	
	public static synchronized int getDeaths(String channelName) {
		return channels.get(channelName).deaths;
	}
	
}