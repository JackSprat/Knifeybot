package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.CommandBase;
import users.PermissionClass;

public class CommandStartEncounter extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		return ((ProcPokemon)parent).createEncounter();
		
	}
	
	@Override public String getPermissionString() 			{ return "battle.createencounter"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatString() 				{ return ":kbattle encounter"; }
	@Override public String getHelpString() 				{ return "Admin command to start encounter"; }
}
