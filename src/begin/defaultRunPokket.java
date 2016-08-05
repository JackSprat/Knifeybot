package begin;

import messaging.IMessagingManager;
import messaging.IncomingMessage;
import messaging.MessagingManager;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import messaging.TestMessagingManager;
import state.ChannelState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gui.ChannelTab;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class defaultRunPokket extends Application {
	
	private static String[] test = {"woundedpoptart", "mandalorianfury", "dashadow", "pokket", "jacksprat47", "stedeb", "fragmentshader", "filipenergy", "themeta4gaming"};
	private static Map<String, ChannelTab> channelTabList = new HashMap<String, ChannelTab>();
	
	public static void main(String[] args) throws InterruptedException {
		
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) {

		for (String s : test) {
			IMessagingManager m = new MessagingManager(s);
			IMessagingManager.managers.add(m);
			new Thread(m).start();
		}
		
		primaryStage.setTitle("Knifeybot");

		
		TabPane channels = new TabPane();
		
        for (String s : test) {
        	channelTabList.put(s, new ChannelTab(s, channels));
        }
        
        
		TabPane mainTabs = new TabPane();
		
		Tab channeltab = new Tab();
		channeltab.setText("Channels");
		mainTabs.getTabs().add(channeltab);
		
		Tab testtab = new Tab();
		testtab.setText("0");
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
	                    
	                }
                	for (String s : test) {
                    	ArrayList<IncomingMessage> messages = ChannelState.retrieveMessages(s);
                    	
                    	if (messages != null && messages.size() >= 1) {
                    		System.out.println("Updating for " + s);
                    		channelTabList.get(s).updateText(messages);
                    	}
                    }
                }
            }
        });
        updateUI.start();
    }
	
}