package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.CommandBase;
import users.PermissionClass;

public class CommandCatch extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		((ProcPokemon)parent).attemptCatch(in.getUser());
		return false;
		
	}
	
	@Override public String getPermissionString() 			{ return "battle.catch"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kbattle catch"; }
	@Override public String getHelpString() 				{ return "If there is a pokemon encountered, uses pokeball to capture it"; }

}