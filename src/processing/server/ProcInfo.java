package processing.server;

import java.util.concurrent.BlockingQueue;

import com.mb3364.twitch.api.Twitch;
import com.mb3364.twitch.api.handlers.ChannelResponseHandler;
import com.mb3364.twitch.api.handlers.StreamResponseHandler;
import com.mb3364.twitch.api.models.Channel;
import com.mb3364.twitch.api.models.Stream;

import logger.Logger;
import messaging.OutgoingMessage;
import processing.ProcBase;
import state.ChannelState;

public class ProcInfo extends ProcBase {
	
	private Twitch twitchAPI;
	private Channel twitchChannel;
	private Stream stream;
	private long lastTick = 0, streamStartTime = 0, botStartTime = System.currentTimeMillis();
	
	public ProcInfo(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		
		twitchAPI = new Twitch();
		twitchAPI.setClientId("h6aj63iy3hapxofqywj8qk7uoeey5ww");
		commands.add(new CommandServer());
		commands.add(new CommandSetServer());
		commands.add(new CommandGame());
		commands.add(new CommandSetGameInfo());
		commands.add(new CommandStatus());
		commands.add(new CommandUptime());
		commands.add(new CommandSub());
		
		commands.add(new CommandDeaths());
		commands.add(new CommandDeathsPlus());
		commands.add(new CommandDeathsMinus());
	}
	
	public String getCurrentGame() {
		if (channel != null) return twitchChannel.getGame();
		return "";
	}
	
	public boolean getChannelLive() {
		if (stream != null) return stream.isOnline();
		return false;
	}
	
	public long getBotStartTime() { return botStartTime; }
	public long getStreamStartTime() { return streamStartTime; }
	
	private void updateChannelInfo() {
		twitchAPI.streams().get(channel, new StreamResponseHandler() {
		    @Override
		    public void onSuccess(Stream s) { stream = s; }

			@Override
			public void onFailure(Throwable arg0) { Logger.WARNING("failed to get channel info"); }

			@Override
			public void onFailure(int arg0, String arg1, String arg2) { Logger.WARNING("failed to get channel info"); }
		});
		twitchAPI.channels().get(channel, new ChannelResponseHandler() {
		    @Override
		    public void onSuccess(Channel c) { twitchChannel = c; }

			@Override
			public void onFailure(Throwable arg0) { Logger.WARNING("failed to get channel info"); }

			@Override
			public void onFailure(int arg0, String arg1, String arg2) { Logger.WARNING("failed to get channel info"); }
		});
	}
	
	@Override
	public void tick() {
		if (lastTick < System.currentTimeMillis() - (10 * 1000)) {
			lastTick = System.currentTimeMillis();
			updateChannelInfo();
			
			ChannelState.setStreamLive(channel, getChannelLive());
			
			if (streamStartTime == 0 && getChannelLive()) {
				streamStartTime = System.currentTimeMillis();
			} else if (streamStartTime != 0 && !getChannelLive()) {
				streamStartTime = 0;
			}
		}
	}

}
