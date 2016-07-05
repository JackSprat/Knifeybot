package begin;

import java.io.File;
import java.nio.file.Files;
import java.util.Date;

import users.UserManager;

public class Test {
	public static void main (String[] args) {
		int counter = 0;
		long starttime = new Date().getTime();
		File dir = new File("users");
		 File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File child : directoryListing) {
		    	String name = child.getName().replace(".xml", "");
		    	int sublength = UserManager.getSubLength("pokket", name);
		      if (sublength > 0) {
		    	  System.out.println(name + ": " + UserManager.getSubLength("pokket", name));
		    	  counter++;
		      }
		    }
		  }
		System.out.println(counter);
	}
}
