package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import pokemon.PokemonUser;
import processing.CommandBase;
import users.PermissionClass;

public class CommandTick extends CommandBase {

	public CommandTick() {
		this.setCommand("kbattle").setKeyword("tick").setTokenCount(2,2);
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		PokemonUser.tick();
		return false;
		
	}
	
	@Override public String getPermissionString() 			{ return "battle.tick"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatString() 				{ return ":kbattle tick"; }
	@Override public String getHelpString() 				{ return "Runs a manual tick on all pokemon users"; }

}