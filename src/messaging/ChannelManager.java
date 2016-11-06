package messaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import messaging.OutgoingMessage.OutType;
import state.ChannelState;

public class ChannelManager implements Runnable {

	private static List<String> channels = new ArrayList<String>();
	private static Map<String, IChannelHandler> handlers = Collections.synchronizedMap(new HashMap<String, IChannelHandler>());
	
	public ChannelManager(String[] test) {
		channels.addAll(Arrays.asList(test));
	}

	@Override
	public void run() {
		
		for (String channel : channels) {
			IChannelHandler h = new BaseChannelHandler(channel);
			handlers.put(channel, h);
			new Thread(h).start();
		}
		
		
		while (true) {
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.Logger.TRACE("Checking thread state");
			for (String channel : channels) {
				try {
					if (ChannelState.getLastMessageTimeAgo(channel) >  (6*60*1000)) {
						logger.Logger.TRACE(ChannelState.getLastMessageTimeAgo(channel) + ", " + channel + " restarting");
						handlers.get(channel).terminateExecution();
						IChannelHandler h = new BaseChannelHandler(channel);
						handlers.put(channel, h);
						new Thread(h).start();
					}
				} catch (NullPointerException npe) {
					logger.Logger.WARNING("Channel not initialised properly");
				}
				
			}
		}
		
	}

	public static void newMessageToIRC(OutgoingMessage messageOut) { handlers.get(messageOut.target).newMessageToIRC(messageOut); }

}
