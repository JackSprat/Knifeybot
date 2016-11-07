package processing.time;

import java.util.concurrent.BlockingQueue;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandTimeRequest extends CommandBase {

	public CommandTimeRequest() {
		this.setCommand("ktime").setTokenCount(5, 5);
	}

	@Override
	public boolean isValid() {

		TimeZone from = ((ProcTimeConverter)parent).getTimezone(tokenList.getToken(2));
		TimeZone to = ((ProcTimeConverter)parent).getTimezone(tokenList.getToken(4));		
		
		if (from == null || to == null) {
			listOut.add(new OutgoingMessage(OutType.CHAT, "Unknown Timezone: " + (to == null ? from.getName() : to.getName()), ((ProcTimeConverter)parent).channel));
			return false;
		}

		return true;
		
	}

	@Override
	public void execute() {
		
		TimeZone from = ((ProcTimeConverter)parent).getTimezone(tokenList.getToken(2));
		TimeZone to = ((ProcTimeConverter)parent).getTimezone(tokenList.getToken(4));

		String[] ints = tokenList.getToken(1).split(":");
		int hours = Integer.parseInt(ints[0]);
		int minutes = Integer.parseInt(ints[1]);

		hours -= from.getHours();
		minutes -= from.getMinutes();

		hours += to.getHours();
		minutes += to.getMinutes();

		hours = ((hours % 24) + 24) % 24;
		minutes = ((minutes % 60) + 60) % 60;

		String hh = hours < 10 ? "0" + hours : "" + hours;
		String mm = minutes < 10 ? "0" + minutes : "" + minutes;
		
		listOut.add(new OutgoingMessage(OutType.CHAT, "That time is " + hh + ":" + mm + " in " + to.getName() + ".", ((ProcTimeConverter)parent).channel));
		return true;
		
	}
	
	@Override public String getPermissionString() { return "time.request"; }
	@Override public PermissionClass getPermissionClass() { return PermissionClass.User; }
	@Override public String getFormatString() 				{ return ":ktime hh:mm <timezone1> to <timezone2>"; }
	@Override public String getHelpString() 				{ return "This command converts hh:mm in timezone1 to timezone2"; }

}
