package processing.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mariuszgromada.math.mxparser.Expression;

import processing.CommandBase;

public class CommandMath extends CommandBase {

	@Override
	public void execute() {
		
		String message = getToken("+");

		List<String> list = new ArrayList<String>(Arrays.asList(message));
		list.removeAll(Arrays.asList("", null));
		
		Expression e = new Expression(message);
		
		sendReply("Result: " + e.getExpressionString() + " = " + e.calculate());
		
	}

	@Override public String getPermissionString() 			{ return "functions.math"; }
	@Override public String getFormatTokens() 				{ return "kmath +"; }
	@Override public String getHelpString() 				{ return "This command evaluates a mathematical expression."; }
	
}
