package gui;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import messaging.incoming.BaseIncomingMessage;
import messaging.outgoing.OutgoingIRCChatMessage;
import state.ChannelState;


public class ChannelTab extends Tab {

	private String							displayName		= "CHANNELTAB";
	private Button							sendMessage		= new Button();
	private TextField						messageInput	= new TextField();
	private Label							text			= new Label();
	private ArrayList<BaseIncomingMessage>	messages		= new ArrayList<BaseIncomingMessage>();

	public ChannelTab(String channel, TabPane parentObject) {
		super();
		displayName = channel;
		setText(displayName);
		setClosable(false);
		messageInput.setText("");
		messageInput.prefWidthProperty().bind(parentObject.widthProperty());
		sendMessage.setText("Message IRC");
		sendMessage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String text = messageInput.getText();
				OutgoingIRCChatMessage messageOut = new OutgoingIRCChatMessage(text, channel);
				ChannelState.getMessageToIRC(channel).offer(messageOut);
				messageInput.clear();
			}
		});
		BorderPane border = new BorderPane();
		setContent(border);
		ScrollBar sp = new ScrollBar();
		sp.setOrientation(Orientation.VERTICAL);
		sp.setMax(100.0);
		sp.setMin(0.0);
		sp.setValue(20);
		border.setRight(sp);
		text.setWrapText(false);
		text.setText("test\nt\nt\nt");
		text.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		text.setMaxHeight(Double.MAX_VALUE);
		text.setMaxWidth(Double.MAX_VALUE);
		text.setAlignment(Pos.BOTTOM_LEFT);
		text.setPadding(new Insets(5));
		border.setCenter(text);
		border.setTop(sendMessage);
		border.setBottom(messageInput);
		parentObject.getTabs().add(this);
	}

	public void updateText(ArrayList<BaseIncomingMessage> messages) {
		this.messages.addAll(messages);
		if (this.messages.size() > 20) {
			for (int i = 0; i < messages.size(); i++) {
				this.messages.remove(i);
			}
		}
		String listString = "";
		for (BaseIncomingMessage m : this.messages) {
			listString += m.toString();
		}
		final String finalListString = listString;
		Platform.runLater(() -> {
			text.setText(finalListString);
		});
	}
}