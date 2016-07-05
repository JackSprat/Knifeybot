package processing.pokemon;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.CommandBase;
import users.PermissionClass;

public class CommandGift extends CommandBase {

	public CommandGift() {
		this.setCommand("kbattle").setKeyword("gift").setTokenCount(5,5);
	}

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		return ((ProcPokemon)parent).giftItem(tokenList.getToken(2), tokenList.getToken(3), tokenList.getToken(4));
		
	}
	
	@Override public String getPermissionString() 			{ return "battle.gift"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.Mod; }
	@Override public String getFormatString() 				{ return ":kbattle gift <user> <item> <count>"; }
	@Override public String getHelpString() 				{ return "Gifting items"; }
}
