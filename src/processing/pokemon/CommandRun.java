package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.CommandBase;
import users.PermissionClass;

public class CommandRun extends CommandBase {

	public CommandRun() {
		this.setCommand("kbattle").setKeyword("run").setTokenCount(2,2);
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		((ProcPokemon)parent).run(in.getUser());
		return false;
		
	}
	
	@Override public String getPermissionString() 			{ return "battle.run"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kbattle run"; }
	@Override public String getHelpString() 				{ return "If there is a pokemon encountered runs"; }

}