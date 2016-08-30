package processing.server;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import state.ChannelState;
import users.PermissionClass;

public class CommandDeaths extends CommandBase {

	
	
	@Override
	public boolean isValid(BlockingQueue<OutgoingMessage> listOut) {
			
		if (!((ProcInfo)parent).getChannelLive()) return false;
		if (((ProcInfo)parent).getCurrentGame() == "") return false;
		return true;
	}
	
	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		String game = ((ProcInfo)parent).getCurrentGame();
		int deaths = ChannelState.getDeaths(parent.channel);
		
		String extraMessage = "";
		
		switch (deaths) {
		case 1:
			extraMessage = "WELCOME TO DARK SOULS"; break;
		case 2:
			extraMessage = "Are you supposed to die in the tutorial area this often?"; break;
		case 5:
			extraMessage = parent.channel + "'s getting the hang of this!"; break;
		case 10:
			extraMessage = "Double digits already? I predict this is to Iudex."; break;
		case 13:
			extraMessage = "Lucky number. Sort of."; break;
		case 16:
			extraMessage = "If only I could be so grossly incandescent..."; break;
		case 22:
			extraMessage = "I DUNNO ABOUT YOU, BUT I'M FEELIN 22"; break;
		case 25:
			extraMessage = "pokketFAT"; break;
		case 29:
			extraMessage = "Carry on my wayward son *sings to himself for a few minutes*"; break;
		case 46:
			extraMessage = "AND 2, HotPokket"; break;
		case 47:
			extraMessage = "SoonerLater"; break;
		case 50:
			extraMessage = "Still going? Damn."; break;
		case 65:
			extraMessage = "DA BA DEE, DA BA you died."; break;
		case 69:
			extraMessage = "pokketLEWD"; break;
		case 88:
			extraMessage = "pokektFEEWLKS"; break;
		case 100:
			extraMessage = "Error, too many deaths."; break;
		}
		
		String message = parent.channel + " has died " + deaths + (deaths == 1 ? " time " : " times ") + " playing " + game + (extraMessage.equals("") ? "." : ", " + extraMessage);
		
		listOut.add(new OutgoingMessage(OutType.CHAT, message, parent.channel));
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "server.deaths"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "deaths"; }
	@Override public String getHelpString() 				{ return "This command shows how many deaths there has been per game"; }

}
