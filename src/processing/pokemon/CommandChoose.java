package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.OutgoingMessage;
import processing.CommandBase;
import users.PermissionClass;

public class CommandChoose extends CommandBase {

	public CommandChoose() {
		this.setCommand("kbattle").setKeyword("choose").setTokenCount(2,3);
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		try {
			int testInt = Integer.parseInt(tokenList.getToken(2));
			return ((ProcPokemon)parent).choosePokemon(in.getUser(), testInt);
		} catch (NumberFormatException nfe) {
			Logger.STACK("", nfe);
		}
		return false;
	}
	
	@Override public String getPermissionString() 			{ return "battle.choose"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kbattle choose <partyID>"; }
	@Override public String getHelpString() 				{ return "Choose a pokemon to battle with!"; }
}
