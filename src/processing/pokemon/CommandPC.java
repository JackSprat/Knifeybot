package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.OutgoingMessage;
import processing.CommandBase;
import users.PermissionClass;

public class CommandPC extends CommandBase {

	public CommandPC() {
		this.setCommand("kbattle").setKeyword("PC").setTokenCount(3,3);
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		try {
			if (tokenList.length() == 3) {
				int testInt = Integer.parseInt(tokenList.getToken(2));
				return ((ProcPokemon)parent).printPC(in.getUser(), testInt);
			}
		} catch (NumberFormatException nfe) {
			Logger.STACK("", nfe);
		}
		return false;
	}
	
	@Override public String getPermissionString() 			{ return "battle.viewpc"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kbattle PC <pcID>"; }
	@Override public String getHelpString() 				{ return "If there is a pokemon encountered, battle it"; }
}
