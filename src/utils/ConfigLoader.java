package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigLoader {

	private static String nick = "";
	private static String pass = "";
	
	public static String getNick() {
		
		if (nick.equals("")) { 
			loadVariables(); 
		}
		
		return nick;
	}
	
	public static String getPass() {
		
		if (nick.equals("")) { 
			loadVariables(); 
		}
		
		return pass;
	}
	
	public static String getOAuth() {
		
		if (nick.equals("")) { 
			loadVariables(); 
		}
		
		return "oauth:" + pass;
	}
	
	private static void loadVariables() {
		
		DirectoryUtils.createDirectories("config");
		
		BufferedReader br = null;

		try {

			br = new BufferedReader(new FileReader("config\\login.cfg"));

			nick = br.readLine();
			pass = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try { if (br != null) br.close(); } catch (IOException ex) { ex.printStackTrace(); }
		
	}
	
}