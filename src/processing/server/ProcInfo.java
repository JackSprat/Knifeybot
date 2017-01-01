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
	private long lastTick = 0, botStartTime = System.currentTimeMillis();
	
	public ProcInfo(BlockingQueue<OutgoingMessage> listOut, String channel) {
		
		super(listOut, channel);
		
		twitchAPI = new Twitch();
		twitchAPI.setClientId("h6aj63iy3hapxofqywj8qk7uoeey5ww");
		
		commands.add(new CommandStatus());
		commands.add(new CommandUptime());
		
	}
	
	public String getCurrentGame() {
		if (channel != null) return twitchChannel.getGame();
		return "";
	}
	
	
	
	public long getBotStartTime() { return botStartTime; }
	

	
	@Override
	public void tick() {
		if (lastTick < System.currentTimeMillis() - (10 * 1000)) {
			lastTick = System.currentTimeMillis();
		}
	}

}
