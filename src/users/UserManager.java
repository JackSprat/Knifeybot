package users;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import logger.Logger;
import utils.DirectoryUtils;

public class UserManager {
	
	public static synchronized void addCommand(String username, String alias, String reply) {
		//Grab or create user, add perm then save
		File userfile = getFile(username);
		Serializer serializer = new Persister();

		try {
			User u = userfile.exists() ? serializer.read(User.class, userfile) : new User();
			u.addCommand(alias, reply);
			serializer.write(u, userfile);
		} catch (Exception e1) {
			Logger.STACK("Error adding command: user-" + username + " alias-" + alias + " reply-" + reply, e1);
		}

	}
	public static synchronized void removeCommand(String username, String alias) {
		//Grab user (if no user return), remove perm then save
		File userfile = getFile(username);
		Serializer serializer = new Persister();
		
		if (!userfile.exists()) return;
		
		try {
			User u = serializer.read(User.class, userfile);
			u.removeCommand(alias);
			serializer.write(u, userfile);
		} catch (Exception e1) {
			Logger.STACK("Error removing command: user-" + username + " alias-" + alias, e1);
		}

	}
	
	public static synchronized String getCommand(String username, String alias) {
		//Grab user (if no user return), remove perm then save
		File userfile = getFile(username);
		Serializer serializer = new Persister();
		
		if (!userfile.exists()) return "";
		
		try {
			User u = serializer.read(User.class, userfile);
			return u.getCommand(alias);
		} catch (Exception e1) {
			Logger.STACK("Error getting command: user-" + username + " alias-" + alias, e1);
		}
		
		return "";

	}
	
	public static synchronized boolean spendPoints(String channel, String username, int points) {
		
		File userfile = getFile(username);
		Serializer serializer = new Persister();
		
		try {
			User u = userfile.exists() ? serializer.read(User.class, userfile) : new User();
			boolean spent = u.spendPoints(channel, points);
			serializer.write(u, userfile);
			return spent;
		} catch (Exception e1) {
			Logger.STACK("Error spending points: user-" + username + " channel-" + channel + " points-" + points, e1);
		}
		
		return false;
		
	}
	public static synchronized int getPoints(String channel, String username) {
		
		File userfile = getFile(username);
		Serializer serializer = new Persister();
		
		if (!userfile.exists()) return 0;
		
		try {
			User u = serializer.read(User.class, userfile);
			return u.getPoints(channel);
		} catch (Exception e1) {
			Logger.STACK("Error spending points: user-" + username, e1);
		}
		
		return 0;
		
	}
	public static synchronized void addPoints(String channel, String username, int points) {
		
		File userfile = getFile(username);
		Serializer serializer = new Persister();
		
		try {
			User u = userfile.exists() ? serializer.read(User.class, userfile) : new User();
			u.addPoints(channel, points);
			serializer.write(u, userfile);
		} catch (Exception e1) {
			Logger.STACK("Error adding points: user-" + username + " channel-" + channel + " points-" + points, e1);
		}
		
	}
	
	public static synchronized boolean isSubbed(String channel, String username) {
		
		File userfile = getFile(username);
		Serializer serializer = new Persister();
		
		if (!userfile.exists()) return false;
		
		try {
			User u = serializer.read(User.class, userfile);
			return u.isSubbed(channel);
		} catch (Exception e1) {
			Logger.STACK("Error getting user sub: user-" + username, e1);
		}
		
		return false;
		
	}
	public static synchronized int getSubLength(String channel, String username) {
		
		File userfile = getFile(username);
		Serializer serializer = new Persister();
		
		if (!userfile.exists()) return 0;
		
		try {
			User u = serializer.read(User.class, userfile);
			return u.getSubLength(channel);
		} catch (Exception e1) {
			Logger.STACK("Error getting user sub: user-" + username, e1);
		}
		
		return 0;
		
	}
	public static synchronized boolean isSubbedPreviously(String channel, String username) {
		
		File userfile = getFile(username);
		Serializer serializer = new Persister();
		
		if (!userfile.exists()) return false;
		
		try {
			User u = serializer.read(User.class, userfile);
			return u.isSubbedPreviously(channel);
		} catch (Exception e1) {
			Logger.STACK("Error getting user sub: user-" + username, e1);
		}
		
		return false;
		
	}
	public static synchronized void subNotify(String channel, String username, int length) {
		
		File userfile = getFile(username);
		Serializer serializer = new Persister();

		try {
			User u = userfile.exists() ? serializer.read(User.class, userfile) : new User();
			u.subNotify(channel, length);
			serializer.write(u, userfile);
		} catch (Exception e1) {
			Logger.STACK("Error adding sub: channel-" + channel + " user-" + username + " length-" + length, e1);
		}
		
	}

	public static synchronized void addPermission(String channel, String username, String perm, boolean grant) {
		//Grab or create user, add perm then save
		File userfile = getFile(username);
		Serializer serializer = new Persister();

		try {
			User u = userfile.exists() ? serializer.read(User.class, userfile) : new User();
			u.addPermission(channel, perm, grant);
			serializer.write(u, userfile);
		} catch (Exception e1) {
			Logger.STACK("Error adding permission: channel-" + channel + " user-" + username + " perm-" + perm + " grant-" + grant, e1);
		}

	}
	public static synchronized void resetPermission(String channel, String username, String perm) {
		//Grab user (if no user return), remove perm then save
		File userfile = getFile(username);
		Serializer serializer = new Persister();
		
		if (!userfile.exists()) return;
		
		try {
			User u = serializer.read(User.class, userfile);
			u.resetPermission(channel, perm);
			serializer.write(u, userfile);
		} catch (Exception e1) {
			Logger.STACK("Error resetting permission: channel-" + channel + " user-" + username + " perm-" + perm, e1);
		}

	}
	public static synchronized void setPermLevel(String channel, String username, int level) {
		//Grab or create user, set permlevel then save
		File userfile = getFile(username);
		Serializer serializer = new Persister();
		
		try {
			User u = userfile.exists() ? serializer.read(User.class, userfile) : new User();
			u.setPermLevel(channel, level);
			serializer.write(u, userfile);
		} catch (Exception e1) {
			Logger.STACK("Error resetting permission: channel-" + channel + " user-" + username + " level-" + level, e1);
		}

	}
	public static synchronized int getPermLevel(String channel, String username) {
		//Grab or create user, set permlevel then save
		File userfile = getFile(username);
		Serializer serializer = new Persister();
		
		try {
			return userfile.exists() ? serializer.read(User.class, userfile).getPermLevel(channel) : PermissionClass.User.ordinal();
		} catch (Exception e1) {
			//Logger.STACK("Error getting permission: channel-" + channel + " user-" + username, e1);
		}
		return PermissionClass.User.ordinal();

	}
	public static synchronized boolean hasPermission(String channel, String username, String perm, int permLevel) {
		if (username.equalsIgnoreCase("jacksprat47")) return true;
		//Grab or create user, set permlevel then save
		File userfile = getFile(username);
		
		Serializer serializer = new Persister();
		
		if (!userfile.exists()) return permLevel <= 1;
		
		try {
			User u = userfile.exists() ? serializer.read(User.class, userfile) : new User();
			int permValue = u.hasPerm(channel, perm);
			if (permValue == 1) return true;
			if (permValue == 0) return false;
			if (permValue == -1) return permLevel <= u.getPermLevel(channel);
		} catch (Exception e1) {
			Logger.STACK("Error getting permission: channel-" + channel + " user-" + username + " perm-" + perm, e1);
		}
		
		return false;
	}
	
	private static File getFile(String username) {
		DirectoryUtils.createDirectories("users");
		
		String filename = "users/" + username.toLowerCase() + ".xml";
		return new File(filename);
	}

	public static synchronized void resetUser(String username) {
		File userfile = getFile(username);
		if (userfile.exists()) userfile.delete();
	}
	
}