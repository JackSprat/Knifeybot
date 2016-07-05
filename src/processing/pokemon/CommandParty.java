package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import logger.Logger;
import messaging.OutgoingMessage;
import processing.CommandBase;
import processing.ProcBase;
import users.PermissionClass;

public class CommandParty extends CommandBase {

	public CommandParty() {
		this.setCommand("kbattle").setKeyword("party").setTokenCount(2,3);
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		try {
			if (tokenList.length() == 3) {
				int testInt = Integer.parseInt(tokenList.getToken(2));
				return ((ProcPokemon)parent).printParty(in.getUser(), testInt);
			}
		} catch (NumberFormatException nfe) {
			Logger.STACK("", nfe);
		}
		
		return ((ProcPokemon)parent).printParty(tokenList.length() == 3 ? tokenList.getToken(2) : in.getUser());
		
	}
	
	@Override public String getPermissionString() 			{ return "battle.viewparty"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kbattle party [partyID]"; }
	@Override public String getHelpString() 				{ return "view entire party, or only partyID"; }
}
