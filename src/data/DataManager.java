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
import utils.ConfigLoader;


public class DataManager {

	private static MysqlDataSource dataSource = new MysqlDataSource();

	public static synchronized void addCommand(String channel, String alias, String reply) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"REPLACE INTO commands (channel, alias, reply, counter, pergame) VALUES ( ?, ?, ?, ?, ?)")) {
			pstmt.setString(1, channel);
			pstmt.setString(2, alias);
			pstmt.setString(3, reply);
			pstmt.setBoolean(4, reply.contains("%counter"));
			pstmt.setBoolean(5, reply.contains("%counterpg"));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			Logger.STACK("Error adding command", e);
		}
	}

	public static synchronized void addPoints(String channel, String username, int value) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO usersubdata (channel, username, months, points, totalpoints) VALUES ( ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE points=points+"
								+ value + ",totalpoints=totalpoints+" + value + ";")) {
			pstmt.setString(1, channel);
			pstmt.setString(2, username);
			pstmt.setInt(3, 0);
			pstmt.setInt(4, value);
			pstmt.setInt(5, value);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			Logger.STACK("Error adding points", e);
		}
	}

	public static synchronized void addPointsMultipleUsers(String channel, String[] usernames, int value) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO usersubdata (channel, username, months, points, totalpoints) VALUES ( ?, ?, 0, ?, ?) ON DUPLICATE KEY UPDATE points=points+"
								+ value + ",totalpoints=totalpoints+" + value + ";")) {
			for (String name : usernames) {
				pstmt.setString(1, channel);
				pstmt.setString(2, name);
				pstmt.setInt(3, value);
				pstmt.setInt(4, value);
			}
			pstmt.executeBatch();
		} catch (SQLException e) {
			Logger.STACK("Error adding points", e);
		}
	}

	public static synchronized void addQuote(String channel, String alias, String username, String quote) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id + 1) FROM quotes WHERE channel = ? ");) {
			pstmt.setString(1, channel);
			int nextFree = 0;
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					nextFree = rs.getInt("MAX(id + 1)");
				}
			}
			try (PreparedStatement pstmt2 = conn.prepareStatement(
					"INSERT INTO quotes (channel, id, username, alias, quote) VALUES ( ?, ?, ?, ?, ?)")) {
				pstmt2.setString(1, channel);
				pstmt2.setInt(2, nextFree);
				pstmt2.setString(3, username);
				pstmt2.setString(4, alias);
				pstmt2.setString(5, quote);
				pstmt2.executeUpdate();
			}
		} catch (SQLException e) {
			Logger.STACK("Error adding quote", e);
		}
	}

	public static synchronized List<String> getActiveChannels() {
		List<String> channels = new ArrayList<String>();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM streams WHERE active=?");) {
			pstmt.setBoolean(1, true);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					channels.add(rs.getString("channel"));
				}
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
		return channels;
	}

	public static synchronized String getCommand(String channel, String alias, String game) {
		String s = "";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM commands WHERE channel=? AND alias=?")) {
			pstmt.setString(1, channel);
			pstmt.setString(2, alias);
			try (ResultSet rs = pstmt.executeQuery()) {
				int counter = 0;
				while (rs.next()) {
					if (rs.getBoolean("counter")) {
						String statementString = "SELECT * FROM counters WHERE channel=? AND alias=?";
						if (rs.getBoolean("pergame")) {
							statementString += " AND game=?";
						}
						try (PreparedStatement pstmt2 = conn.prepareStatement(statementString)) {
							pstmt2.setString(1, channel);
							pstmt2.setString(2, alias);
							if (rs.getBoolean("pergame")) {
								pstmt2.setString(3, game);
							}
							try (ResultSet rs2 = pstmt2.executeQuery()) {
								while (rs2.next()) {
									counter = rs2.getInt("value");
								}
							}
						}
					}
					s = rs.getString("reply");
					s = s.replace("%counterpg", "" + counter);
					s = s.replace("%counter", "" + counter);
				}
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
		return s;
	}

	public static synchronized int getCounter(String channel, String alias, String game) {
		int counter = 0;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM commands WHERE channel=? AND alias=?")) {
			pstmt.setString(1, channel);
			pstmt.setString(2, alias);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					if (rs.getBoolean("counter")) {
						String statementString = "SELECT * FROM counters WHERE channel=? AND alias=?";
						if (rs.getBoolean("pergame")) {
							statementString += " AND game=?";
						}
						try (PreparedStatement pstmt2 = conn.prepareStatement(statementString)) {
							pstmt2.setString(1, channel);
							pstmt2.setString(2, alias);
							if (rs.getBoolean("pergame")) {
								pstmt2.setString(3, game);
							}
							try (ResultSet rs2 = pstmt2.executeQuery()) {
								while (rs2.next()) {
									counter = rs2.getInt("value");
								}
							}
						}
					}
				}
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
		return counter;
	}

	public static synchronized String getOauthKey(int userID, String scopeRequired) {
		String oauthKey = "";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM userdata WHERE id=?")) {
			pstmt.setInt(1, userID);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					userID = rs.getInt("id");
					if (rs.getString("scopes").contains(scopeRequired)) {
						oauthKey = rs.getString("oauth");
					}
				}
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
		return oauthKey;
	}

	public static synchronized PermissionClass getPermissionClass(String channel, String permission) {
		int permlevel = 0;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT * FROM channelpermissions WHERE channel=? AND permission=?")) {
			pstmt.setString(1, channel);
			pstmt.setString(2, permission);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					permlevel = rs.getInt("level");
				}
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
		return PermissionClass.getPermissionClass(permlevel);
	}

	public static synchronized String getQuote(String channel, int ID) {
		// Grab user (if no user return), remove perm then save
		String s = "";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT * FROM quotes WHERE channel=? AND id=? ORDER BY RAND() LIMIT 1")) {
			pstmt.setString(1, channel);
			pstmt.setInt(2, ID);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					s = "(" + rs.getInt("id") + " - " + rs.getString("alias") + ") " + rs.getString("username") + ": "
							+ rs.getString("quote");
				}
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
		return s;
	}

	public static synchronized String getQuote(String channel, String alias) {
		String s = "";
		// Grab user (if no user return), remove perm then save
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"SELECT * FROM quotes WHERE channel=? AND (alias=? or username=?) ORDER BY RAND() LIMIT 1")) {
			pstmt.setString(1, channel);
			pstmt.setString(2, alias);
			pstmt.setString(3, alias);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					s = "(" + rs.getInt("id") + " - " + rs.getString("alias") + ") " + rs.getString("username") + ": "
							+ rs.getString("quote");
				}
			}
		} catch (SQLException e) {
			Logger.STACK("Error getting quote", e);
		}
		return s;
	}

	public static synchronized String getRandomQuote(String channel) {
		String s = "";
		// Grab user (if no user return), remove perm then save
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT * FROM quotes WHERE channel=? ORDER BY RAND() LIMIT 1")) {
			pstmt.setString(1, channel);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					s = "(" + rs.getInt("id") + " - " + rs.getString("alias") + ") " + rs.getString("username") + ": "
							+ rs.getString("quote");
				}
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
		return s;
	}

	public static synchronized int getSubLength(String channel, String username) {
		int months = 0;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT * FROM usersubdata WHERE channel=? AND username=?")) {
			pstmt.setString(1, channel);
			pstmt.setString(2, username);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					months = rs.getInt("months");
				}
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
		return months;
	}

	public static synchronized String getSubMessage(String channel, int months) {
		// Grab user (if no user return), remove perm then save
		String specificSubMessage = "";
		String defaultSubMessage = "";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT * FROM submessages WHERE channel=? ORDER BY minmonths")) {
			pstmt.setString(1, channel);
			if (months == 0) {
				months++;
			}
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					if (rs.getInt("minmonths") == months) {
						specificSubMessage = rs.getString("submessage");
						break;
					} else if (rs.getInt("minmonths") == -1) {
						defaultSubMessage = rs.getString("submessage");
					}
				}
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
		return specificSubMessage.equals("")
				? (defaultSubMessage.equals("") ? "Thanks for the sub!" : defaultSubMessage) : specificSubMessage;
	}

	public static synchronized int getUserID(String username) {
		int userID = 0;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM userdata WHERE name=?")) {
			pstmt.setString(1, username.toLowerCase());
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					userID = rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
		return userID;
	}

	public static synchronized PermissionClass getUserLevel(String channel, String username) {
		PermissionClass permLevel = PermissionClass.User;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT * FROM userpermissions WHERE channel=? AND username=?")) {
			pstmt.setString(1, channel);
			pstmt.setString(2, username);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					permLevel = PermissionClass.getPermissionClass(rs.getInt("level"));
				}
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
		return permLevel;
	}

	public static synchronized boolean hasPermission(String channel, String username, String perm) {
		if (username.equalsIgnoreCase("jacksprat47")) { return true; }
		if (username.equalsIgnoreCase(channel)) { return true; }
		int userlevel = 1;
		boolean valueExists = false;
		int permlevel = 1;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT * FROM userpermissions WHERE channel=? AND username=?")) {
			pstmt.setString(1, channel);
			pstmt.setString(2, username);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					userlevel = rs.getInt("level");
				}
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT * FROM channelpermissions WHERE channel=? AND permission=?")) {
			pstmt.setString(1, channel);
			pstmt.setString(2, perm);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					permlevel = rs.getInt("level");
					valueExists = true;
				}
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
		if (!valueExists) {
			try (Connection conn = dataSource.getConnection();
					PreparedStatement pstmt = conn
							.prepareStatement("SELECT * FROM channelpermissions WHERE channel=? AND permission=?")) {
				pstmt.setString(1, "default");
				pstmt.setString(2, perm);
				try (ResultSet rs = pstmt.executeQuery()) {
					while (rs.next()) {
						permlevel = rs.getInt("level");
						valueExists = true;
					}
				}
			} catch (SQLException e) {
				Logger.STACK("", e);
			}
		}
		if (!valueExists) {
			Logger.ERROR("Default permission not found for " + perm);
			return false;
		}
		return userlevel >= permlevel;
	}

	public static synchronized void initialiseDB() {
		dataSource.setUser(ConfigLoader.getDBUsername());
		dataSource.setPassword(ConfigLoader.getDBPassword());
		dataSource.setServerName(ConfigLoader.getDBServer());
		dataSource.setDatabaseName(ConfigLoader.getDBName());
	}

	public static synchronized void removeCommand(String channel, String alias) {
		// Grab user (if no user return), remove perm then save
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM commands WHERE alias=? AND channel=?")) {
			pstmt.setString(1, alias);
			pstmt.setString(2, channel);
			pstmt.executeUpdate();
			try (PreparedStatement pstmt2 = conn.prepareStatement("DELETE FROM counters WHERE alias=? AND channel=?")) {
				pstmt2.setString(1, alias);
				pstmt2.setString(2, channel);
				pstmt2.executeUpdate();
			}
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
	}

	public static synchronized void removeQuote(String channel, int ID) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM quotes WHERE id=? AND channel=?")) {
			pstmt.setInt(1, ID);
			pstmt.setString(2, channel);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			Logger.STACK("Error removing quote", e);
		}
	}

	public static synchronized void setCounter(String channel, String alias, int value) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM commands WHERE channel=? AND alias=?")) {
			pstmt.setString(1, channel);
			pstmt.setString(2, alias);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					if (rs.getBoolean("counter")) {
						try (PreparedStatement pstmt2 = conn.prepareStatement(
								"REPLACE INTO counters (channel, alias, game, value) VALUES ( ?, ?, ?, ?)")) {
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
				}
			}
		} catch (SQLException e) {
			Logger.STACK("Error setting counter", e);
		}
	}

	public static synchronized void setSubMessage(String channel, int months, String message) {
		if (months == 0) {
			months++;
		}
		// Grab user (if no user return), remove perm then save
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt2 = conn.prepareStatement(
						"REPLACE INTO submessages (channel, minmonths, submessage) VALUES ( ?, ?, ?)")) {
			pstmt2.setString(1, channel);
			pstmt2.setInt(2, months);
			pstmt2.setString(3, message);
			pstmt2.executeUpdate();
		} catch (SQLException e) {
			Logger.STACK("", e);
		}
	}

	public static synchronized void setUserLevel(String channel, String username, PermissionClass level) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"REPLACE INTO userpermissions (channel, username, level) VALUES ( ?, ?, ?)")) {
			int setLevel = level.getLevelID();
			pstmt.setString(1, channel);
			pstmt.setString(2, username);
			pstmt.setInt(3, setLevel);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			Logger.STACK("Error setting userlevel", e);
		}
	}

	public static synchronized boolean usePoints(String channel, String username, int value) {
		boolean hasPoints = false;
		int currentPoints = 0;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("SELECT * FROM usersubdata WHERE channel=? AND username=?")) {
			pstmt.setString(1, channel);
			pstmt.setString(2, username);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					currentPoints = rs.getInt("points");
				}
			}
		} catch (SQLException e) {
			Logger.STACK("Error getting points", e);
		}
		if (currentPoints >= value) {
			hasPoints = true;
		}
		boolean noError = true;
		if (hasPoints) {
			try (Connection conn = dataSource.getConnection();
					PreparedStatement pstmt = conn
							.prepareStatement("UPDATE usersubdata SET points=? WHERE channel=? AND username=?")) {
				pstmt.setInt(1, currentPoints - value);
				pstmt.setString(2, channel);
				pstmt.setString(3, username);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				noError = false;
				Logger.STACK("Error setting points", e);
			}
		}
		return noError && hasPoints;
	}
}