package processing.quoter;

import messaging.IncomingMessage.InType;
import processing.CommandBase;
import users.PermissionClass;

public class CommandQuoteViewMulti extends CommandBase {

	@Override
	public void execute() {
		
		String message = "";
		
		int page = Integer.parseInt(getToken("page"));
		int count = Integer.parseInt(getToken("count"));
		System.out.println(page + " " + count);
		for (int i = page * count; i < (page + 1) * count; i++) {
			String q1 = ((ProcQuoter)parent).getQuote(i + "");
			message += q1 + "/n";
			System.out.println(q1);
		}

		sendReply(message);
		
	}
	
	@Override public InType[] getValidInputs() 				{ return new InType[]{InType.COMMAND}; }
	@Override public String getPermissionString() 			{ return "quoter.viewmultiquote"; }
	@Override public PermissionClass getPermissionClass() 	{ return PermissionClass.User; }
	@Override public String getFormatTokens() 				{ return "kquote viewmulti @page @count"; }
	@Override public String getHelpString() 				{ return ""; }
	
}
