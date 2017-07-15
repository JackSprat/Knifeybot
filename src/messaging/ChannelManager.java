package messaging;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.DataManager;
import state.ChannelState;


public class ChannelManager implements Runnable {

	private static Map<String, IChannelHandler> handlers = Collections
			.synchronizedMap(new HashMap<String, IChannelHandler>());

	@Override
	public void run() {
		while (true) {
			List<String> channels = DataManager.getActiveChannels();
			logger.Logger.TRACE("Checking thread state");
			for (String channel : channels) {
				boolean restart = false;
				try {
					if (ChannelState.getLastMessageTimeAgo(channel) > (6 * 60 * 1000)) {
						System.out.println(ChannelState.getLastMessageTimeAgo(channel) + "\n\n\n\n\n\n");
						logger.Logger
								.TRACE(ChannelState.getLastMessageTimeAgo(channel) + ", " + channel + " restarting");
						handlers.get(channel).terminateExecution();
						restart = true;
					}
				} catch (NullPointerException npe) {
					logger.Logger.STACK("Channel not initialised properly", npe);
					restart = true;
				}
				if (restart) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {/**/}
					IChannelHandler h = new SingleChannelHandler(channel);
					handlers.put(channel, h);
					new Thread(h).start();
				}
			}
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {/**/}
		}
	}
}
