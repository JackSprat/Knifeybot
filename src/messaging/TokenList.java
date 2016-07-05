package messaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TokenList {

	private List<String> tokens = new ArrayList<String>();
	private int counter = -1;
	
	public TokenList(String s) {
		
		/*String[] tokenStrings = s.split("\"");
		
		if (tokenStrings.length % 2 == 1 && tokenStrings.length > 1) {
			
			boolean oddString = true;
			
			for (String str : tokenStrings) {
				
				if (oddString) {
					
					String[] subTokens = s.split(" ");
					
					for (String token : subTokens) {
						
						if (token.contains(""))
						
					}
					
				}
				
			}
			
		}
		*/
		String[] tokenStrings = s.split(" ");
		tokens = Arrays.asList(tokenStrings);
	}
	
	public String next() {
		counter++;
		if (tokens != null && tokens.size() > counter) {
			return tokens.get(counter);
		}
		return null;
	}
	public void reset() {
		counter = -1;
	}
	public int length() {
		return tokens == null ? 0 : tokens.size();
	}
	public String getToken(int i) {
		if (tokens != null) return tokens.get(i);
		return null;
	}
	
	@Override
	public String toString() {
		String output = "";
		for (String t : tokens) {
			output += t + " ";
		}
		return output.trim();
	}
	
}