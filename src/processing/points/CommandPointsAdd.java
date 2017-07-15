package processing.points;

import data.DataManager;
import processing.CommandBase;

public class CommandPointsAdd extends CommandBase {

	@Override
	public void execute() {
		try{
			int points = Integer.parseInt(getToken("pointsvalue"));
			DataManager.addPoints(parent.channel, getToken("user"), points);
			sendChatReply(getUser() + ", points added pokketGOOD");
		} catch (NumberFormatException nfe) {
			sendChatReply(getUser() + ", " + getToken("pointsvalue") + " is not a valid points value");
		}
		
		
	}
	
	@Override public String getPermissionString() 			{ return "points.add"; }
	@Override public String getFormatTokens() 				{ return "points add @user @pointsvalue"; }
	@Override public String getHelpString() 				{ return "This command adds <pointsvalue> points to <user>"; }

}
