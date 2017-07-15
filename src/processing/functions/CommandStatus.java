package processing.functions;

import processing.CommandBase;
import state.ChannelState;

public class CommandStatus extends CommandBase {

	@Override
	public void execute() {
		
		boolean isLive = ChannelState.isStreamLive(parent.channel);
		String message = isLive ? "Stream is live! \\ MrDestructoid /" : "Stream is not live pokketFeels";
		sendChatReply(message);
		
	}
	
	@Override public String getPermissionString() 			{ return "functions.status"; }
	@Override public String getFormatTokens() 				{ return "status"; }
	@Override public String getHelpString() 				{ return "This command checks stream live status"; }

}
