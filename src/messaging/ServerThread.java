package messaging;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import state.ChannelState;

public class ServerThread implements Runnable {

	Socket p;
	public ServerThread(Socket p) {
		this.p = p;
	}

	@Override
	public void run() {
		
		try {
			logger.Logger.INFO("Sending thread created");
			String text = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			DataOutputStream out = new DataOutputStream(p.getOutputStream());
			
	        String textIn = "";
	        int lines = 0;
	        while (lines < 4 && (textIn = in.readLine()) != null) {
	        	logger.Logger.INFO("read text: " + textIn);
	        	if (textIn.isEmpty()) {
	                break;
	            }
	            text += textIn + "\n";
	            lines++;
	        }
	        IncomingMessage m = new IncomingMessage(text);
	        
	        String ID = m.getID();
	        logger.Logger.INFO("Created message: " + ID);
	        
	        ChannelState.getMessageToProc(m.getChannel()).add(m);
	        logger.Logger.INFO("Message sent to Proc:" + m.getChannel() + ", " + String.join(" ", m.getTokenList()));
	        boolean replyReceived = false;
	        while(!replyReceived) {
	        	OutgoingMessage head = ChannelState.getMessageToWeb(m.getChannel()).peek();

	        	if (head != null && head.ID.equals(ID)) {
	        		logger.Logger.INFO("Message sent to Web " + head.ID);
	        		
	        		BlockingQueue<OutgoingMessage> outMsg = ChannelState.getMessageToWeb(m.getChannel());
	        		OutgoingMessage mOut = outMsg.take();
	        		out.write(mOut.toString().getBytes());
	        		//out.writeBytes(mOut.toString());
	        		out.flush();
	        		replyReceived = true;

	        	}
	        	try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        out.close();
		} catch (IOException | InterruptedException e) {

			e.printStackTrace();
		}
		
		try {
			p.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
