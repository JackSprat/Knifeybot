package messaging;

import java.net.URI;
import java.util.concurrent.BlockingQueue;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import logger.Logger;
import messaging.incoming.BaseIncomingMessage;
import utils.TextUtils;


@ClientEndpoint
public class WebsocketClientEndpoint {

	Session										userSession	= null;
	private BlockingQueue<BaseIncomingMessage>	messageList;

	public WebsocketClientEndpoint(URI endpointURI, BlockingQueue<BaseIncomingMessage> messageList) {
		try {
			Logger.ERROR("Connection: " + endpointURI.toString());
			this.messageList = messageList;
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(this, endpointURI);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		Logger.INFO("closing websocket");
		this.userSession = null;
	}

	@OnMessage
	public void onMessage(String message) {
		Logger.INFO(TextUtils.setLength("Rec: " + "WSS", 15) + " - " + message);
		messageList.add(BaseIncomingMessage.constructMessage(message));
	}

	@OnOpen
	public void onOpen(Session userSession) {
		Logger.INFO("Opening websocket");
		this.userSession = userSession;
	}

	public void sendMessage(String message) {
		userSession.getAsyncRemote().sendText(message);
	}
}