package processing.roll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import processing.CommandBase;
import users.PermissionClass;

public class CommandRoll extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		String rollString = "";
		String message = getToken("+");

		List<String> list = new ArrayList<String>(Arrays.asList(message));
		list.removeAll(Arrays.asList("", null));
		
		int sum = 0;
		int tokenSum = 0;
		List<Character> intChars = new ArrayList<Character>();
		
		for(int i = 0, n = message.length() ; i < n ; i++) { 
			
		    Character c = message.charAt(i);
		    
		    if (Character.isDigit(c)) {
		    	
		    } else if (c.equals('+')) {
		    	
		    }
		}
		
		return true;
		
	}

	@Override public String getPermissionString() 			{ return "roll.roll"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "kroll +"; }
	@Override public String getHelpString() 				{ return "This command rolls dice and outputs them."; }
	
}
