package begin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.DataManager;
import gui.ChannelTab;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import logger.Logger;
import messaging.ChannelManager;
import utils.ConfigLoader;


public class DefaultRun extends Application {

	private static Map<String, ChannelTab> channelTabList = new HashMap<String, ChannelTab>();

	public static void main(String[] args) throws InterruptedException {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		ConfigLoader.initialize();
		DataManager.initialiseDB();
		ChannelManager manager = new ChannelManager();
		new Thread(manager).start();
		primaryStage.setTitle("Knifeybot");
		TabPane channels = new TabPane();
		List<String> channelList = DataManager.getActiveChannels();
		for (String s : channelList) {
			channelTabList.put(s, new ChannelTab(s, channels));
		}
		TabPane mainTabs = new TabPane();
		Tab channeltab = new Tab();
		channeltab.setText("Channels");
		channeltab.setClosable(false);
		mainTabs.getTabs().add(channeltab);
		Tab testtab = new Tab();
		testtab.setText("0");
		testtab.setClosable(false);
		mainTabs.getTabs().add(testtab);
		channeltab.setContent(channels);
		primaryStage.setScene(new Scene(mainTabs, 500, 400));
		primaryStage.show();
		Thread updateUI = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(2000);
					} catch (Exception ex) {
						Logger.WARNING("Error waiting in UI thread\n" + ex.getMessage());
					}
					/*
					 * for (String s : channelList) {
					 * ArrayList<IncomingIRCMessage> messages =
					 * ChannelState.retrieveMessages(s); if (messages != null &&
					 * messages.size() >= 1) { Logger.DEBUG(
					 * "Updating GUI messages for " + s);
					 * channelTabList.get(s).updateText(messages); } }
					 */
				}
			}
		});
		updateUI.start();
	}
}