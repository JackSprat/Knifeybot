package processing.quoter;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

public class Quote {

	@Attribute
	public String alias;
	
	@Attribute
	public String username;
	
	@Element
	public String quote;
	
	/*public Quote(@Attribute(name="username")String username, @Element(name="alias")String alias, @Element(name="quote")String quote) {

		this.username = username;
		this.alias = alias;
		this.quote = quote;
		
	}*/
	
	public String toString(long ID) {
		StringBuilder s = new StringBuilder();
								s.append("(" + ID);
		if (alias != null) { 	s.append(" - " + alias); }
								s.append(") " + username + ": " + quote);
		
		return s.toString();
		
	}
	
}