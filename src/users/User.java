package users;

import java.util.HashMap;
import java.util.Map;

import org.simpleframework.xml.*;

@Root
public class User {
	
	@ElementMap(entry="channel", key="name", attribute=true, inline=false, required = false)
	private Map<String, UserChannelData> channelMap = new HashMap<String, UserChannelData>();
	
	@ElementMap(entry="reply", key="alias", attribute=true, inline=false, required = false)
	private Map<String, String> commandMap = new HashMap<String, String>();
	
	public void addPermission(String channel, String perm, boolean grant) {
		
		if (!channelMap.containsKey(channel)) {
			channelMap.put(channel, new UserChannelData());
		}
		
		channelMap.get(channel).permMap.put(perm, grant);
	}
	public void resetPermission(String channel, String perm) {
		
		if (channelMap.containsKey(channel)) {
			channelMap.get(channel).permMap.remove(perm);
		}
		
	}
	
	public boolean isSubbed(String channel) {
		
		if (channelMap.get(channel).lastSub + ((long)1000)*60*60*24*45 > System.currentTimeMillis()) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean isSubbedPreviously(String channel) {
		
		if (channelMap.get(channel).lastSub > 0) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public void subNotify(String channel, int length) {
		
		if (!channelMap.containsKey(channel)) {
			channelMap.put(channel, new UserChannelData());
		}
		
		channelMap.get(channel).lastSub = System.currentTimeMillis();
		channelMap.get(channel).subLength = length;
		
	}
	
	public int getSubLength(String channel) {
		if (isSubbed(channel)) {
			return channelMap.get(channel).subLength;
		}
		
		return 0;
	}

	public void setPermLevel(String channel, int level) {
		
		if (!channelMap.containsKey(channel)) {
			channelMap.put(channel, new UserChannelData());
		}
		
		channelMap.get(channel).permLevel = level;
	}
	public int hasPerm(String channel, String perm) {

		if (channelMap.containsKey(channel)) {
			if (channelMap.get(channel).permMap.containsKey(perm)) {
				return channelMap.get(channel).permMap.get(perm) ? 1 : 0;
			}
		}
		return -1;
	}
	public int getPermLevel(String channel) {
		if (channelMap.containsKey(channel)) {
			return channelMap.get(channel).permLevel;
		}
		return PermissionClass.User.ordinal();
	}
	
	public void addCommand(String alias, String reply) {
		
		commandMap.put(alias, reply);
		
	}
	public void removeCommand(String alias) {
		
		commandMap.remove(alias);
		
	}
	public String getCommand(String alias) {
		// TODO Auto-generated method stub
		return commandMap.get(alias);
	}
	public boolean spendPoints(String channel, int points) {
		
		if (!channelMap.containsKey(channel)) {
			channelMap.put(channel, new UserChannelData());
		}
		
		if (channelMap.get(channel).points >= points) {
			channelMap.get(channel).points -= points;
			return true;
		}
		
		return false;
	}
	public int getPoints(String channel) {
		if (!channelMap.containsKey(channel)) {
			channelMap.put(channel, new UserChannelData());
		}

		return channelMap.get(channel).points;
	}
	public void addPoints(String channel, int points) {
		
		if (!channelMap.containsKey(channel)) {
			channelMap.put(channel, new UserChannelData());
		}
		if (channelMap.get(channel).totalPoints == 0) channelMap.get(channel).totalPoints = channelMap.get(channel).points;
		channelMap.get(channel).points += points;
		channelMap.get(channel).totalPoints += points;
		

	}
	
	
	
}