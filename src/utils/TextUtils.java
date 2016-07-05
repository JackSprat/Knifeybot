package utils;

import java.util.Arrays;

public class TextUtils {
	
	public static String setLength(String s, int length) {
		String returnString = "";
		
		if (s.length() < length) {
			char[] chars = new char[length - s.length()];
			Arrays.fill(chars, ' ');
			returnString = s + new String(chars);	
		} else {
			returnString = s.substring(0, length);
		}
		
		return returnString;
		
	}
	
	private static String[] nameList = {"knifey", "knifeybot"};
	
	public static boolean matchName(String s) {
		
		for (String name : nameList) {
			if (s.contains(name)) { return true; }
		}
		
		return false;
		
	}
	
	public static String clean(String str) {
		str = str.replaceAll("\n|\t|\r|\f|", "");
		str = str.trim().replaceAll(" +", " ");
		return str;
		
	}
	
	public static String cleanNewlines(String str) {
		str = str.replaceAll("\n|\t|\r|\f|", "");
		return str;
		
	}
	
}