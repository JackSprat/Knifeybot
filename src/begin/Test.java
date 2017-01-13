package begin;

import data.DataManager;

public class Test {
	public static void main (String[] args) {
		
		DataManager.initialiseDB();
		DataManager.addQuote("testchannel2", "testalias", "testuser", "testquote");
		
	}
}
