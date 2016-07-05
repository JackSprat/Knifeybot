package processing.roll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandRoll extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		String rollString = "";
		String message = getToken("...");
		String[] tokens = message.split(" ");
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(tokens));
		list.removeAll(Arrays.asList("", null));
		
		int sum = 0;
		
		for (int idx = 0; idx < list.size(); idx+=2) {
			if (list.get(idx).contains("d")) {
				
				int[] rolls = parseRoll(list.get(idx));
				
				String[] rollsText = new String[rolls.length];
				for (int i = 0; i < rollsText.length; i++) {
					rollsText[i] = Integer.toString(rolls[i]);
				}
				
				rollString += list.get(idx) + "{" + String.join(", ", rollsText) + "} ";
				
				int subRoll = 0;
				for (int i : rolls) {
					subRoll += i;
				}
				
				if (idx == 0) {
					sum = subRoll;
				} else {
					sum += list.get(idx - 1).equals("+") ? subRoll : -subRoll;
				}
				
			}
		}
		
		rollString = getUser() + " rolled a " + sum + "! rolls: " + rollString;
		double rand = Math.random();
		System.out.println(rand);
		if (getUser().equalsIgnoreCase("DaShadow")) {
			rollString = getUser() + " rodar una " + sum + "! tiradas de dados: " + rollString;
		} else if (rand > 0.99d) {
			rollString = "Whoops, the dice fell off the table. Unlucky. pokketFAT";
		} 
		
		
		listOut.add(new OutgoingMessage(OutType.CHAT, rollString, ((ProcRoll)parent).channel));

		return true;
	}
	
	private int[] parseRoll(String token) {
		
		int[] returnRolls;
			
		String[] tokens = token.split("d");
		
		for (String s : tokens) {
			if (s.length() > 3) return null;
		}
		
		int dice = tokens[0].equals("") ? 1 : Integer.parseInt(tokens[0]);
		int diceSize = Integer.parseInt(tokens[1]);
		if (dice > 10 || diceSize > 100) return null;
		returnRolls = new int[dice];
		
		for (int i = 0; i < dice; i++) {
			int roll = (int)(Math.random() * diceSize) + 1;
			returnRolls[i] = roll;
		}
		
		return returnRolls;
		
	}

	@Override public String getPermissionString() 			{ return "roll.roll"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":kroll ..."; }
	@Override public String getHelpString() 				{ return "This command rolls dice and outputs them."; }
	
}
