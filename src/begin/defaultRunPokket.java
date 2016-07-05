package begin;

import messaging.IMessagingManager;
import messaging.IncomingMessage;
import messaging.MessagingManager;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import messaging.TestMessagingManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class defaultRunPokket extends Application {
	public static void main(String[] args) throws InterruptedException {
		
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) {
		
		boolean testMode = false;
		boolean justMe = false;
		
		String[] test = {"dashadow", "pokket", "jacksprat47", "stedeb", "fragmentshader", "filipenergy", "themeta4gaming"};
		if (justMe) {
			test = new String[] {"jacksprat47"};
		}
		for (String s : test) {
			IMessagingManager m = testMode ? new TestMessagingManager(s) : new MessagingManager(s);
			IMessagingManager.managers.add(m);
			new Thread(m).start();
		}
		
		primaryStage.setTitle("Knifeybot");
        
        
        TextField messageInput = new TextField();
        
        messageInput.setText("Message Text Here");
        messageInput.setMinSize(100, 100);
        
        Button messageButton = new Button();
        messageButton.setText("Message Proc");
        messageButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent event) {
            	String text = messageInput.getText();
            	IncomingMessage messageIn = new IncomingMessage(":jacksprat47!jacksprat47@jacksprat47.tmi.twitch.tv PRIVMSG #pokket :" + text);
            	for (IMessagingManager m : IMessagingManager.managers) {
    				if (m.getChannel().equalsIgnoreCase("jacksprat47")) m.newMessageToProc(messageIn);
    			}
            	messageInput.clear();
        	}
        });
        
        Button messageButton2 = new Button();
        messageButton2.setText("Message IRC");
        messageButton2.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent event) {
            	String text = messageInput.getText();
            	OutgoingMessage messageOut = new OutgoingMessage(OutType.CHAT, text, "pokket");
            	for (IMessagingManager m : IMessagingManager.managers) {
    				if (m.getChannel().equalsIgnoreCase("pokket")) m.newMessageToIRC(messageOut);
    			}
            	messageInput.clear();
        	}
        });
        //StackPane root = new StackPane();
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        grid.add(messageButton, 0, 2);
        grid.add(messageButton2, 0, 3);
        grid.add(messageInput, 1, 2);
        primaryStage.setScene(new Scene(grid, 300, 250));
        primaryStage.show();

    }
	
}