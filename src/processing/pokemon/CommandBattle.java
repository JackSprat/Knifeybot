package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.CommandBase;
import users.PermissionClass;

public class CommandBattle extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		return ((ProcPokemon)parent).addUserToEncounter(in.getUser());
		
	}
	
	@Override public String getPermissionString() 			{ return "battle.battle"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kbattle"; }
	@Override public String getHelpString() 				{ return "If there is a pokemon encountered, battle it"; }
}
