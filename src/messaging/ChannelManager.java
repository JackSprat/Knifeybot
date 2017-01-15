package messaging;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.DataManager;
import state.ChannelState;

public class ChannelManager implements Runnable {

	private static Map<String, IChannelHandler> handlers = Collections.synchronizedMap(new HashMap<String, IChannelHandler>());

	@Override
	public void run() {
		
		WebReceiver webRec = new WebReceiver();
		new Thread(webRec).start();
		
		while (true) {
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			List<String> channels = DataManager.getActiveChannels();
			
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
					logger.Logger.STACK("Channel not initialised properly", npe);
					IChannelHandler h = new BaseChannelHandler(channel);
					handlers.put(channel, h);
					new Thread(h).start();
				}
				
			}
			
		}
		
	}

}
