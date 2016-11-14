package channel;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import logger.Logger;
import utils.DirectoryUtils;

public class DataManager {
	
	public static synchronized String getGameAttribute(String channel, String game, String attribute) {
		// TODO Auto-generated method stub
		File channelfile = getFile(channel);
		Serializer serializer = new Persister();
		
		if (!channelfile.exists()) return "";
		
		try {
			ChannelData c = channelfile.exists() ? serializer.read(ChannelData.class, channelfile) : new ChannelData();
			return c.getGameAttribute(game, attribute);
		} catch (Exception e1) {
			Logger.STACK("Error getting gameinfo: channel-" + channel + " game-" + game + " attribute-" + attribute, e1);
		}
		
		return "";
	}
	public static synchronized void setGameAttribute(String channel, String game, String attribute, String value) {
		// TODO Auto-generated method stub
		File channelfile = getFile(channel);
		Serializer serializer = new Persister();

		try {
			ChannelData c = channelfile.exists() ? serializer.read(ChannelData.class, channelfile) : new ChannelData();
			c.addGameAttribute(game, attribute, value);
			serializer.write(c, channelfile);
		} catch (Exception e1) {
			Logger.STACK("Error adding game attribute: channel-" + channel, e1);
		}
	}
	
	public static synchronized void addQuote(String channel, String alias, String user, String quote) {
		//Grab or create user, add perm then save
		File channelfile = getFile(channel);
		Serializer serializer = new Persister();

		try {
			ChannelData c = channelfile.exists() ? serializer.read(ChannelData.class, channelfile) : new ChannelData();
			c.addQuote(alias, user, quote);
			serializer.write(c, channelfile);
		} catch (Exception e1) {
			Logger.STACK("Error adding command: channel-" + channel + " alias-" + alias + " user-" + user + " quote-" + quote, e1);
		}

	}
	public static synchronized void removeQuote(String channel, Long ID) {
		//Grab user (if no user return), remove perm then save
		File channelfile = getFile(channel);
		Serializer serializer = new Persister();
		
		if (!channelfile.exists()) return;
		
		try {
			ChannelData c = channelfile.exists() ? serializer.read(ChannelData.class, channelfile) : new ChannelData();
			c.removeQuote(ID);
			serializer.write(c, channelfile);
		} catch (Exception e1) {
			Logger.STACK("Error removing command: channel-" + channel + " ID-" + ID, e1);
		}

	}
	public static synchronized String getQuote(String channel, String alias) {
		//Grab user (if no user return), remove perm then save
		File channelfile = getFile(channel);
		Serializer serializer = new Persister();
		
		if (!channelfile.exists()) return "";
		
		try {
			ChannelData c = channelfile.exists() ? serializer.read(ChannelData.class, channelfile) : new ChannelData();
			return c.getQuote(alias);
		} catch (Exception e1) {
			Logger.STACK("Error getting quote: channel-" + channel + " alias-" + alias, e1);
		}
		
		return "";

	}
	public static synchronized String getQuote(String channel, Long ID) {
		//Grab user (if no user return), remove perm then save
		File channelfile = getFile(channel);
		Serializer serializer = new Persister();
		
		if (!channelfile.exists()) return "";
		
		try {
			ChannelData c = channelfile.exists() ? serializer.read(ChannelData.class, channelfile) : new ChannelData();
			return c.getQuote(ID);
		} catch (Exception e1) {
			Logger.STACK("Error getting quote: channel-" + channel + " ID-" + ID, e1);
		}
		
		return "";

	}
	public static synchronized String getRandomQuote(String channel) {
		//Grab user (if no user return), remove perm then save
		File channelfile = getFile(channel);
		Serializer serializer = new Persister();
		
		if (!channelfile.exists()) return "";
		
		try {
			ChannelData c = channelfile.exists() ? serializer.read(ChannelData.class, channelfile) : new ChannelData();
			return c.getQuote();
		} catch (Exception e1) {
			Logger.STACK("Error getting random quote: channel-" + channel, e1);
		}
		
		return "";

	}
	
	public static synchronized void addCommand(String channel, String alias, String reply) {
		//Grab or create user, add perm then save
		File channelfile = getFile(channel);
		Serializer serializer = new Persister();

		try {
			ChannelData c = channelfile.exists() ? serializer.read(ChannelData.class, channelfile) : new ChannelData();
			c.addCommand(alias, reply);
			serializer.write(c, channelfile);
		} catch (Exception e1) {
			Logger.STACK("Error adding command: channel-" + channel + " alias-" + alias + " reply-" + reply, e1);
		}

	}
	public static synchronized void removeCommand(String channel, String alias) {
		//Grab user (if no user return), remove perm then save
		File channelfile = getFile(channel);
		Serializer serializer = new Persister();
		
		if (!channelfile.exists()) return;
		
		try {
			ChannelData c = channelfile.exists() ? serializer.read(ChannelData.class, channelfile) : new ChannelData();
			c.removeCommand(alias);
			serializer.write(c, channelfile);
		} catch (Exception e1) {
			Logger.STACK("Error removing command: channel-" + channel + " alias-" + alias, e1);
		}

	}
	public static synchronized String getCommand(String channel, String alias) {
		//Grab user (if no user return), remove perm then save
		File channelfile = getFile(channel);
		Serializer serializer = new Persister();
		
		if (!channelfile.exists()) return "";
		
		try {
			ChannelData c = channelfile.exists() ? serializer.read(ChannelData.class, channelfile) : new ChannelData();
			return c.getCommand(alias);
		} catch (Exception e1) {
			Logger.STACK("Error getting command: channel-" + channel + " alias-" + alias, e1);
		}
		
		return "";

	}
	
	private static synchronized File getFile(String channel) {
		DirectoryUtils.createDirectories("channels");
		
		String filename = "channels/" + channel.toLowerCase() + ".xml";
		return new File(filename);
	}

	public static synchronized void resetChannel(String channel) {
		File channelfile = getFile(channel);
		if (channelfile.exists()) channelfile.delete();
	}
	
}