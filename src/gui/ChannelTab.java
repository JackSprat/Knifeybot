package gui;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import messaging.IMessagingManager;
import messaging.IncomingMessage;
import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;

public class ChannelTab extends Tab {

	private String displayName = "CHANNELTAB";
	private Button sendMessage = new Button();
	private TextField messageInput = new TextField();
	private Label text = new Label();
	private ArrayList<IncomingMessage> messages = new ArrayList<IncomingMessage>();
	
	public ChannelTab(String channel, TabPane parentObject) {
	
		super();

		displayName = channel;
		
		this.setText(displayName);
        
        messageInput.setText("");
        messageInput.prefWidthProperty().bind(parentObject.widthProperty());
        
        sendMessage.setText("Message IRC");
        sendMessage.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent event) {
            	String text = messageInput.getText();
            	OutgoingMessage messageOut = new OutgoingMessage(OutType.CHAT, text, channel);
            	for (IMessagingManager m : IMessagingManager.managers) {
    				if (m.getChannel().equalsIgnoreCase(channel)) m.newMessageToIRC(messageOut);
    			}
            	messageInput.clear();
        	}
        });
        
        BorderPane border = new BorderPane();
        this.setContent(border);
        
        
        text.setWrapText(false);
        text.setText("test\nt\nt\nt");

        border.setCenter(text);
        border.setTop(sendMessage);
        border.setBottom(messageInput);
        
        parentObject.getTabs().add(this);
		
	}

	public void updateText(ArrayList<IncomingMessage> messages) {
		this.messages.addAll(messages);
		if (this.messages.size() > 20) {
			for (int i = 0; i < messages.size(); i++) {
				this.messages.remove(i);
			}
		}
		String listString = "";
		for (IncomingMessage m : this.messages) {
			listString += m.getUser() + ": " + m.getTokenList().toString() + "\n";
		}
		
		final String finalListString = listString;
		
		Platform.runLater(() -> {
    		text.setText(finalListString);
        });
	}
	
}