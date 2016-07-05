package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.CommandBase;
import users.PermissionClass;

public class CommandChallenge extends CommandBase {

	public CommandChallenge() {
		this.setCommand("kbattle").setKeyword("challenge").setTokenCount(3,3);
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		String token = tokenList.getToken(2);
		if (token.equalsIgnoreCase("accept")) {
			((ProcPokemon)parent).joinBattle(in.getUser());
		}
		((ProcPokemon)parent).startBattle(in.getUser(), tokenList.getToken(2));
		return true;
		
	}
	
	@Override public String getPermissionString() 			{ return "battle.challenge"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kbattle challenge <user>"; }
	@Override public String getHelpString() 				{ return "Battle another user"; }

}