package processing.roll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mariuszgromada.math.mxparser.Expression;

import processing.CommandBase;
import users.PermissionClass;

public class CommandMath extends CommandBase {

	@Override
	public void execute() {
		
		String message = getToken("+");

		List<String> list = new ArrayList<String>(Arrays.asList(message));
		list.removeAll(Arrays.asList("", null));
		
		Expression e = new Expression(message);
		
		sendReply("Result: " + e.getExpressionString() + " = " + e.calculate());
		
	}

	@Override public String getPermissionString() 			{ return "roll.math"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "kmath +"; }
	@Override public String getHelpString() 				{ return "This command evaluates a mathematical expression."; }
	
}
