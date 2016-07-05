package processing.server;

import java.util.HashMap;
import java.util.Map;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;

public class GameObject {
	
	@Attribute
	private String game = "";
	
	@Element(required=false)
	private String shortName = "";
	
	@ElementMap(entry="value", key="attribute", attribute=true, inline=true, required=false)
	private Map<String, String> attributes = new HashMap<String, String>();
	
	public String getAttribute(String attribute) {
		if (attributes != null && attributes.containsKey(attribute)) return attributes.get(attribute);
		return "";
	}
	
	public void setAttribute(String attribute, String value) {
		if (attributes != null) attributes.put(attribute, value);
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}