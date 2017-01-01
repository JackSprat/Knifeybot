package state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.mb3364.twitch.api.Twitch;
import com.mb3364.twitch.api.handlers.ChannelResponseHandler;
import com.mb3364.twitch.api.handlers.StreamResponseHandler;
import com.mb3364.twitch.api.models.Channel;
import com.mb3364.twitch.api.models.Stream;

import logger.Logger;
import messaging.IncomingMessage;
import messaging.OutgoingMessage;

public class ChannelState {

	private static Map<String, ChannelState> channels = new HashMap<String, ChannelState>();
	
	protected Map<String, Long> newSubs = new HashMap<String, Long>();
	protected ArrayList<IncomingMessage> messagesIn = new ArrayList<IncomingMessage>();
	protected boolean isLive = false;
	protected int deaths = 0;
	private long lastChannelMessage = 0;
	
	private Twitch twitchAPI;
	private Channel twitchChannel;
	private Stream stream;
	
	protected long lastStreamEnd = 0;
	
	private BlockingQueue<IncomingMessage> messageFromIRC = 	new LinkedBlockingQueue<IncomingMessage>();
	private BlockingQueue<IncomingMessage> messageToProc = 		new LinkedBlockingQueue<IncomingMessage>();
	private BlockingQueue<OutgoingMessage> messageFromProc = 	new LinkedBlockingQueue<OutgoingMessage>();
	private BlockingQueue<OutgoingMessage> messageToIRC = 		new LinkedBlockingQueue<OutgoingMessage>();
	private BlockingQueue<OutgoingMessage> messageToWeb = 		new LinkedBlockingQueue<OutgoingMessage>();
	
	private ChannelState() {
		twitchAPI = new Twitch();
		twitchAPI.setClientId("h6aj63iy3hapxofqywj8qk7uoeey5ww");
	}
	
	public static synchronized void updateStreamObjects() {
		for (String channel : channels.keySet()) {
			if (channels.get(channel).isLive) {
				updateObject(channel);
			}
		}
	}
	
	private static synchronized void updateObject(String channel) {
		
		ChannelState state = channels.get(channel);
		
		state.twitchAPI.streams().get(channel, new StreamResponseHandler() {
		    @Override
		    public void onSuccess(Stream s) { state.stream = s; }

			@Override
			public void onFailure(Throwable arg0) { Logger.WARNING("failed to get channel info"); }

			@Override
			public void onFailure(int arg0, String arg1, String arg2) { Logger.WARNING("failed to get channel info"); }
		});
		state.twitchAPI.channels().get(channel, new ChannelResponseHandler() {
		    @Override
		    public void onSuccess(Channel c) { state.twitchChannel = c; }

			@Override
			public void onFailure(Throwable arg0) { Logger.WARNING("failed to get channel info"); }

			@Override
			public void onFailure(int arg0, String arg1, String arg2) { Logger.WARNING("failed to get channel info"); }
		});
	}
	
	public static synchronized void registerChannel(String channelName) {
		Logger.INFO("Registering ChannelState for " + channelName);
		ChannelState c = new ChannelState();
		
		channels.put(channelName, c);
		
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

	public static synchronized Set<String> getChannelList() {
		return channels.keySet();
	}
	
	public static synchronized String getCurrentGame(String channel) {
		if (channels.get(channel) != null) return channels.get(channel).twitchChannel.getGame();
		return "";
	}
	
	public static synchronized BlockingQueue<OutgoingMessage> getMessageFromProc(String channelName) { return channels.get(channelName).messageFromProc; }
	public static synchronized BlockingQueue<OutgoingMessage> getMessageToIRC(String channelName) { return channels.get(channelName).messageToIRC; }
	public static synchronized BlockingQueue<IncomingMessage> getMessageToProc(String channelName) { 	return channels.get(channelName).messageToProc; }
	public static synchronized BlockingQueue<IncomingMessage> getMessageFromIRC(String channelName) { 	return channels.get(channelName).messageFromIRC; }
	public static synchronized BlockingQueue<OutgoingMessage> getMessageToWeb(String channelName) { return channels.get(channelName).messageToWeb; }

}