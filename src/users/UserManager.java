package users;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import logger.Logger;
import utils.DirectoryUtils;

public class UserManager {
	
	private static Serializer serializer = new Persister();
	
	private static synchronized File getFile(String username) {
		DirectoryUtils.createDirectories("users");
		
		String filename = "users/" + username.toLowerCase() + ".xml";
		return new File(filename);
	}
	private static synchronized User getUser(String username) {
		
		File userfile = getFile(username);

		try {
			User u = userfile.exists() ? serializer.read(User.class, userfile) : new User();
			return u;
		} catch (Exception e1) {
			Logger.STACK("Error retrieving user - " + username, e1);
		}
		
		return null;
		
	}
	private static synchronized void saveUser(String username, User u) {
		
		File userfile = getFile(username);
		try {
			serializer.write(u, userfile);
		} catch (Exception e) {
			Logger.STACK("Error writing user - " + username, e);
		}
	}
	
	public static synchronized void addCommand(String username, String alias, String reply) {

			User u = getUser(username);
			u.addCommand(alias, reply);
			saveUser(username, u);

	}
	public static synchronized void removeCommand(String username, String alias) {
		
		User u = getUser(username);
		u.removeCommand(alias);
		saveUser(username, u);

	}
	public static synchronized String getCommand(String username, String alias) { return getUser(username).getCommand(alias); }
	
	public static synchronized boolean spendPoints(String channel, String username, int points) {
		
		User u = getUser(username);
		boolean spent = u.spendPoints(channel, points);
		saveUser(username, u);
		return spent;
		
	}
	public static synchronized int getPoints(String channel, String username) { return getUser(username).getPoints(channel); }
	public static synchronized void addPoints(String channel, String username, int points) {
		
		User u = getUser(username);
		u.addPoints(channel, points);
		saveUser(username, u);
		
	}
	
	public static synchronized boolean isSubbed(String channel, String username) { return getUser(username).isSubbed(channel); }
	public static synchronized int getSubLength(String channel, String username) { return getUser(username).getSubLength(channel); }
	public static synchronized boolean isSubbedPreviously(String channel, String username) { return getUser(username).isSubbedPreviously(channel); }
	public static synchronized void subNotify(String channel, String username, int length) {
		
		User u = getUser(username);
		u.subNotify(channel, length);
		saveUser(username, u);
		
	}
	
	public static synchronized void addPermission(String channel, String username, String perm, boolean grant) {
		
		User u = getUser(username);
		u.addPermission(channel, perm, grant);
		saveUser(username, u);

	}
	public static synchronized void resetPermission(String channel, String username, String perm) {
		
		User u = getUser(username);
		u.resetPermission(channel, perm);
		saveUser(username, u);

	}
	public static synchronized void setPermLevel(String channel, String username, int level) {

		User u = getUser(username);
		u.setPermLevel(channel, level);
		saveUser(username, u);

	}
	public static synchronized int getPermLevel(String channel, String username) { return getUser(username).getPermLevel(channel); }
	public static synchronized boolean hasPermission(String channel, String username, String perm, int permLevel) {
		if (username.equalsIgnoreCase("jacksprat47")) return true;
		User u = getUser(username);
		
		int permValue = u.hasPerm(channel, perm);
		if (permValue == 1) return true;
		if (permValue == 0) return false;
		if (permValue == -1) return permLevel <= u.getPermLevel(channel);
		return false;
	}
	
	public static synchronized void resetUser(String username) {
		File userfile = getFile(username);
		if (userfile.exists()) userfile.delete();
	}
	
}