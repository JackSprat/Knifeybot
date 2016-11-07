package processing.roll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import processing.CommandBase;
import users.PermissionClass;

public class CommandRoll extends CommandBase {

	@Override
	public void execute() {
		
		String rollString = "";
		String message = getToken("+");

		List<String> list = new ArrayList<String>(Arrays.asList(message));
		list.removeAll(Arrays.asList("", null));
		
		int sum = 0;
		int diceCount = 0;
		int diceValue = 0;
		boolean tokenSeenD = false;
		boolean tokenAdd = true;
		
		for(int i = 0, n = message.length() ; i < n ; i++) { 
			
		    Character c = message.charAt(i);
		    System.out.println(i + c.toString());
		    if (Character.isDigit(c)) {
		    	if (tokenSeenD) {
		    		diceValue *= 10;
		    		diceValue += Integer.parseInt(c.toString());
		    	} else {
		    		diceCount *= 10;
		    		diceCount += Integer.parseInt(c.toString());
		    	}
		    } else if (c.equals('-') || c.equals('+')) {
		    	
		    	rollString += (tokenAdd ? "+ " : "- ") + diceCount + (diceValue > 1 ? "d" + diceValue + " {" : " ");
				
				int tokenTotal = 0;
				
				for (int j = 0; j < diceCount; j++) {
					
					double rand = Math.random();
					
					int value = (int) (rand * diceValue) + 1;
					
					tokenTotal += tokenAdd ? value : -value;
					
					rollString += (diceValue > 1 ? value + (j + 1 == diceCount ? "" : ", ") : "");
					
				}
				
				rollString += (diceValue > 1 ? "} (" + Math.abs(tokenTotal) + ") " : "");
		    	
		    	tokenSeenD = false;
				tokenAdd = c.equals('+');
				
				sum += tokenTotal;
				
				diceCount = 0;
				diceValue = 0;
				
		    } else if (c.equals('d') && !tokenSeenD) {
		    	tokenSeenD = true;
		    } else if (c.equals(' ')) {
		    	continue;
		    }
		    
		}
		
		rollString += (tokenAdd ? "+ " : "- ") + diceCount + (diceValue > 1 ? "d" + diceValue + " {" : " ");
		
		int tokenTotal = 0;
		
		for (int j = 0; j < diceCount; j++) {
			
			double rand = Math.random();
			
			int value = (int) (rand * diceValue) + 1;
			
			tokenTotal += tokenAdd ? value : -value;
			
			rollString += (diceValue > 1 ? value + (j + 1 == diceCount ? "" : ", ") : "");
			
		}
		
		rollString += (diceValue > 1 ? "} (" + Math.abs(tokenTotal) + ") " : "");
		
		sum += tokenTotal;
		
		rollString = getUser() + " rolled a " + sum + "! Rolls:" + rollString.substring(2);
		if (rollString.length() > 150) rollString = rollString.substring(0, 150) + " ...";
		sendReply(rollString);
		
	}

	@Override public String getPermissionString() 			{ return "roll.roll"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "kroll +"; }
	@Override public String getHelpString() 				{ return "This command rolls dice and outputs them."; }
	
}
