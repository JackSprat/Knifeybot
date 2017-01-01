package begin;

import messaging.IncomingMessage;
import messaging.ChannelManager;
import state.ChannelState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data.DataManager;
import gui.ChannelTab;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import logger.Logger;

public class defaultRunPokket extends Application {
	
	private static String[] test = {"woundedpoptart", "mandalorianfury", "dashadow", "pokket", "jacksprat47", "stedeb", "fragmentshader", "filipenergy", "themeta4gaming"};
	private static Map<String, ChannelTab> channelTabList = new HashMap<String, ChannelTab>();
	
	public static void main(String[] args) throws InterruptedException {
		
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) {

		ChannelManager manager = new ChannelManager(test);
		DataManager.initialiseDB();
		
		new Thread(manager).start();
		primaryStage.setTitle("Knifeybot");
		
		TabPane channels = new TabPane();
		
        for (String s : test) {
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
            @Override public void run() {
                while (true) {
                	try {
	                    Thread.sleep(2000);
	                } catch (Exception ex) {
	                    Logger.WARNING("Error waiting in UI thread\n" + ex.getMessage());
	                }
                	for (String s : test) {
                		
                    	ArrayList<IncomingMessage> messages = ChannelState.retrieveMessages(s);
                    	
                    	if (messages != null && messages.size() >= 1) {
                    		Logger.DEBUG("Updating GUI messages for " + s);
                    		channelTabList.get(s).updateText(messages);
                    	}
                    }
                }
            }
        });
        updateUI.start();
    }
	
}