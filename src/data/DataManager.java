package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import logger.Logger;
import state.ChannelState;
import users.PermissionClass;

public class DataManager {
	
	private static MysqlDataSource dataSource = new MysqlDataSource();
	
	public static synchronized void initialiseDB() {
		dataSource.setUser("knifeybottest");
		dataSource.setPassword("celticrno1");
		dataSource.setServerName("192.168.1.68");
		dataSource.setDatabaseName("knifeybot");
	}
	
	public static synchronized List<String> getActiveChannels() {
		
		try {
			Connection conn = dataSource.getConnection();
			//Get max+1 quote ID
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM streams WHERE active=?");
			pstmt.setBoolean(1, true);
			ResultSet rs = pstmt.executeQuery();
			
			List<String> channels = new ArrayList<String>();
			
			while(rs.next()){
				channels.add(rs.getString("channel"));
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			
			return channels;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public static synchronized void addQuote(String channel, String alias, String username, String quote) {
		
		try {
			Connection conn = dataSource.getConnection();
			//Get max+1 quote ID
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id + 1) FROM quotes WHERE channel = ? ");
			pstmt.setString(1, channel);
			ResultSet rs = pstmt.executeQuery();
			
			int nextFree = 0;
			
			while(rs.next()){
				nextFree = rs.getInt("MAX(id + 1)");
			}
			
			pstmt = conn.prepareStatement("INSERT INTO quotes (channel, id, username, alias, quote) VALUES ( ?, ?, ?, ?, ?)");
			pstmt.setString(1, channel);
			pstmt.setInt(2, nextFree);
			pstmt.setString(3, username);
			pstmt.setString(4, alias);
			pstmt.setString(5, quote);
			pstmt.executeUpdate();
			
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static synchronized void removeQuote(String channel, int ID) {
		
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM quotes WHERE id=? AND channel=?");
			pstmt.setInt(1, ID);
			pstmt.setString(2, channel);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static synchronized String getQuote(String channel, String alias) {
		//Grab user (if no user return), remove perm then save
		try {
			Connection conn = dataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM quotes WHERE channel=? AND (alias=? or username=?) ORDER BY RAND() LIMIT 1");
			pstmt.setString(1, channel);
			pstmt.setString(2, alias);
			pstmt.setString(3, alias);
			ResultSet rs = pstmt.executeQuery();
			
			String s = "";
			while(rs.next()){
				s = "(" + rs.getInt("id") + 
						" - " + rs.getString("alias") + ") " +
						rs.getString("username") + ": " + rs.getString("quote");
			}

			rs.close();
			pstmt.close();
			conn.close();
			return s;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public static synchronized String getQuote(String channel, int ID) {
		//Grab user (if no user return), remove perm then save
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM quotes WHERE channel=? AND id=? ORDER BY RAND() LIMIT 1");
			pstmt.setString(1, channel);
			pstmt.setInt(2, ID);
			ResultSet rs = pstmt.executeQuery();
			String s = "";
			while(rs.next()){
				s = "(" + rs.getInt("id") + 
						" - " + rs.getString("alias") + ") " +
						rs.getString("username") + ": " + rs.getString("quote");
			}

			rs.close();
			pstmt.close();
			conn.close();
			return s;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}
	public static synchronized String getRandomQuote(String channel) {
		//Grab user (if no user return), remove perm then save
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM quotes WHERE channel=? ORDER BY RAND() LIMIT 1");
			pstmt.setString(1, channel);
			ResultSet rs = pstmt.executeQuery();
			
			String s = "";
			while(rs.next()){
				s = "(" + rs.getInt("id") + 
						" - " + rs.getString("alias") + ") " +
						rs.getString("username") + ": " + rs.getString("quote");
			}

			rs.close();
			pstmt.close();
			conn.close();
			return s;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}
	
	public static synchronized void addCommand(String channel, String alias, String reply) {
		try {
			Connection conn = dataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement("REPLACE INTO commands (channel, alias, reply, counter, pergame) VALUES ( ?, ?, ?, ?, ?)");
			pstmt.setString(1, channel);
			pstmt.setString(2, alias);
			pstmt.setString(3, reply);
			pstmt.setBoolean(4, reply.contains("%counter"));
			pstmt.setBoolean(5, reply.contains("%counterpg"));
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static synchronized void removeCommand(String channel, String alias) {
		//Grab user (if no user return), remove perm then save
		try {
			Connection conn = dataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM commands WHERE alias=? AND channel=?");
			pstmt.setString(1, alias);
			pstmt.setString(2, channel);
			pstmt.executeUpdate();
			
			PreparedStatement pstmt2 = conn.prepareStatement("DELETE FROM counters WHERE alias=? AND channel=?");
			pstmt2.setString(1, alias);
			pstmt2.setString(2, channel);
			pstmt2.executeUpdate();
			
			pstmt.close();
			pstmt2.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public static synchronized String getCommand(String channel, String alias, String game) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM commands WHERE channel=? AND alias=?");
			pstmt.setString(1, channel);
			pstmt.setString(2, alias);
			ResultSet rs = pstmt.executeQuery();
			
			String s = "";
			int counter = 0;
			
			while(rs.next()) {
				if (rs.getBoolean("counter")) {
					PreparedStatement pstmt2;
					if (rs.getBoolean("pergame")) {
						pstmt2 = conn.prepareStatement("SELECT * FROM counters WHERE channel=? AND alias=? AND game=?");
						pstmt2.setString(3, game);
					} else {
						pstmt2 = conn.prepareStatement("SELECT * FROM counters WHERE channel=? AND alias=?");
					}
					pstmt2.setString(1, channel);
					pstmt2.setString(2, alias);
					ResultSet rs2 = pstmt2.executeQuery();
					
					while (rs2.next()) {
						counter = rs2.getInt("value");
					}
					
					rs2.close();
					pstmt2.close();
					
				}
				s = rs.getString("reply");
				s = s.replace("%counterpg", "" + counter);
				s = s.replace("%counter", "" + counter);
				
			}

			rs.close();
			pstmt.close();
			conn.close();
			return s;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}
	
	public static synchronized int getCounter(String channel, String alias, String game) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM commands WHERE channel=? AND alias=?");
			pstmt.setString(1, channel);
			pstmt.setString(2, alias);
			ResultSet rs = pstmt.executeQuery();
			
			int counter = 0;
			
			while(rs.next()) {
				if (rs.getBoolean("counter")) {
					PreparedStatement pstmt2;
					if (rs.getBoolean("pergame")) {
						pstmt2 = conn.prepareStatement("SELECT * FROM counters WHERE channel=? AND alias=? AND game=?");
						pstmt2.setString(3, game);
					} else {
						pstmt2 = conn.prepareStatement("SELECT * FROM counters WHERE channel=? AND alias=?");
						pstmt2.setString(1, channel);
						pstmt2.setString(2, alias);
					}
					ResultSet rs2 = pstmt2.executeQuery();
					
					while (rs2.next()) {
						counter = rs2.getInt("value");
					}
					
					rs2.close();
					pstmt2.close();
					
				}
			}

			rs.close();
			pstmt.close();
			conn.close();
			return counter;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}
	public static synchronized void setCounter(String channel, String alias, int value) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM commands WHERE channel=? AND alias=?");
			pstmt.setString(1, channel);
			pstmt.setString(2, alias);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if (rs.getBoolean("counter")) {
					PreparedStatement pstmt2 = conn.prepareStatement("REPLACE INTO counters (channel, alias, game, value) VALUES ( ?, ?, ?, ?)");
					if (rs.getBoolean("pergame")) {
						pstmt2.setString(3, ChannelState.getCurrentGame(channel));
					} else {
						pstmt2.setString(3, "ALL");
					}
					pstmt2.setString(1, channel);
					pstmt2.setString(2, alias);
					pstmt2.setInt(4, value);
					pstmt2.executeUpdate();

				}
			}

			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static synchronized boolean hasPermission(String channel, String username, String perm) {
		if (username.equalsIgnoreCase("jacksprat47")) return true;
		if (username.equalsIgnoreCase(channel)) return true;
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM userpermissions WHERE channel=? AND username=?");
			pstmt.setString(1, channel);
			pstmt.setString(2, username);
			ResultSet rs = pstmt.executeQuery();
			
			int userlevel = 1;
			while(rs.next()){
				userlevel = rs.getInt("level");
			}
			rs.close();
			
			pstmt = conn.prepareStatement("SELECT * FROM channelpermissions WHERE channel=? AND permission=?");
			pstmt.setString(1, channel);
			pstmt.setString(2, perm);
			ResultSet rs2 = pstmt.executeQuery();
			
			int permlevel = 1;
			boolean valueExists = false;
			while(rs2.next()){
				permlevel = rs2.getInt("level");
				valueExists = true;
			}
			
			rs2.close();
			
			if (!valueExists) {
				pstmt = conn.prepareStatement("SELECT * FROM channelpermissions WHERE channel=? AND permission=?");
				pstmt.setString(1, "default");
				pstmt.setString(2, perm);
				ResultSet rs3 = pstmt.executeQuery();
				while(rs3.next()){
					permlevel = rs3.getInt("level");
					valueExists = true;
				}
				rs3.close();
				if (!valueExists) {
					Logger.ERROR("Default permission not found for " + perm);
					return false;
				}
			}
			
			pstmt.close();
			conn.close();
			return userlevel >= permlevel;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static synchronized void setUserLevel(String channel, String username, PermissionClass level) {
		
		try {
			
			int setLevel = level.getLevelID();
			
			Connection conn = dataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement("REPLACE INTO userpermissions (channel, username, level) VALUES ( ?, ?, ?)");
			pstmt.setString(1, channel);
			pstmt.setString(2, username);
			pstmt.setInt(3, setLevel);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static synchronized PermissionClass getUserLevel(String channel, String username) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM userpermissions WHERE channel=? AND username=?");
			pstmt.setString(1, channel);
			pstmt.setString(2, username);
			ResultSet rs = pstmt.executeQuery();
			
			PermissionClass permLevel = PermissionClass.User;
			
			while(rs.next()) {
				 permLevel = PermissionClass.getPermissionClass(rs.getInt("level"));
			}

			rs.close();
			pstmt.close();
			conn.close();
			return permLevel;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PermissionClass.Banned;
	}
	public static synchronized PermissionClass getPermissionClass(String channel, String permission) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM channelpermissions WHERE channel=? AND permission=?");
			pstmt.setString(1, channel);
			pstmt.setString(2, permission);
			ResultSet rs = pstmt.executeQuery();
			
			int permlevel = 0;
			while(rs.next()){
				permlevel = rs.getInt("level");
			}
			rs.close();
			pstmt.close();
			conn.close();
			return PermissionClass.getPermissionClass(permlevel);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PermissionClass.SuperAdmin;
	}

	public static synchronized int getSubLength(String channel, String username) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM usersubdata WHERE channel=? AND username=?");
			pstmt.setString(1, channel);
			pstmt.setString(2, username);
			ResultSet rs = pstmt.executeQuery();
			
			int months = 0;
			
			while(rs.next()) {
				months = rs.getInt("months");
			}

			rs.close();
			pstmt.close();
			conn.close();
			return months;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}