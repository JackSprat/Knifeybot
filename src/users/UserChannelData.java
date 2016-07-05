package users;

import java.util.HashMap;
import java.util.Map;

import org.simpleframework.xml.*;

public class UserChannelData {

	@Attribute
	public int permLevel = 1;
	
	@Attribute(required = false)
	public int subLength = 0;
	
	@Attribute(required = false)
	public long lastSub = 0;
	
	@Attribute(required = false)
	public int points = 0;
	
	@Attribute(required = false)
	public long totalPoints = 0;
	
	@ElementMap(entry="permission", key="permString", attribute=true, inline=true, required = false)
	public Map<String, Boolean> permMap = new HashMap<String, Boolean>();
	
}