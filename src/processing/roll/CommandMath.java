package processing.roll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.mariuszgromada.math.mxparser.Expression;

import messaging.OutgoingMessage;
import messaging.OutgoingMessage.OutType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandMath extends CommandBase {

	@Override
	public boolean execute(BlockingQueue<OutgoingMessage> listOut) {
		
		String message = getToken("+");

		List<String> list = new ArrayList<String>(Arrays.asList(message));
		list.removeAll(Arrays.asList("", null));
		
		Expression e = new Expression(message);
		
		listOut.add(new OutgoingMessage(OutType.CHAT, "Result: " + e.getExpressionString() + " = " + e.calculate(), parent.channel));
		return true;
		
	}

	@Override public String getPermissionString() 			{ return "roll.math"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "kmath +"; }
	@Override public String getHelpString() 				{ return "This command evaluates a mathematical expression."; }
	
}
