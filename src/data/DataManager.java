package data;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import utils.DirectoryUtils;

public class DataManager {
	
	private static MysqlDataSource dataSource = new MysqlDataSource();
	
	public static synchronized void initialiseDB() {
		dataSource.setUser("knifeybottest");
		dataSource.setPassword("celticrno1");
		dataSource.setServerName("192.168.1.68");
		dataSource.setDatabaseName("knifeybot");
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
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM quotes WHERE channel=? AND alias=? ORDER BY RAND() LIMIT 1");
			pstmt.setString(1, channel);
			pstmt.setString(2, alias);
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
	
	public static synchronized void addCommand(String channel, String alias, String reply, boolean counter, boolean perGame) {
		try {
			Connection conn = dataSource.getConnection();
			
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO commands (channel, alias, reply, counter, pergame) VALUES ( ?, ?, ?, ?, ?)");
			pstmt.setString(1, channel);
			pstmt.setString(2, alias);
			pstmt.setString(3, reply);
			pstmt.setBoolean(4, counter);
			pstmt.setBoolean(5, perGame);
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
			
			pstmt = conn.prepareStatement("DELETE FROM counters WHERE alias=? AND channel=?");
			pstmt.setString(1, alias);
			pstmt.setString(2, channel);
			pstmt.executeUpdate();
			
			pstmt.close();
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
				s = s.replace("%counter", "" + counter);
				s = s.replace("%gamecounter", "" + counter);
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
	
	public static synchronized void setCounter(String channel, String alias, String game, int value) {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM commands WHERE channel=? AND alias=?");
			pstmt.setString(1, channel);
			pstmt.setString(2, alias);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if (rs.getBoolean("counter")) {
					PreparedStatement pstmt2 = conn.prepareStatement("REPLACE INTO counters (channel, alias, game, value)");
					if (rs.getBoolean("pergame")) {
						pstmt2.setString(3, "ALL");
					} else {
						pstmt2 = conn.prepareStatement("SELECT * FROM counters WHERE channel=? AND alias=?");
						pstmt2.setString(3, game);
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