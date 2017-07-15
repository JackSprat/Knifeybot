package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import logger.Logger;


public class ConfigLoader {

	private static String	username	= "";
	private static String	pass		= "";
	private static String	dbuser		= "";
	private static String	dbpass		= "";
	private static String	dbserver	= "";
	private static String	dbname		= "";

	public static String getDBName() {
		return dbname;
	}

	public static String getDBPassword() {
		return dbpass;
	}

	public static String getDBServer() {
		return dbserver;
	}

	public static String getDBUsername() {
		return dbuser;
	}

	public static String getOAuth() {
		return "oauth:" + pass;
	}

	public static String getPass() {
		return pass;
	}

	public static String getUsername() {
		return username;
	}

	public static void initialize() {
		DirectoryUtils.createDirectories("config");
		Properties prop = new Properties();
		String fileName = "config\\login.cfg";
		try (InputStream is = new FileInputStream(fileName)) {
			prop.load(is);
			username = prop.getProperty("nickname", "nickname");
			pass = prop.getProperty("password", "password");
			dbuser = prop.getProperty("dbuser", "dbuser");
			dbpass = prop.getProperty("dbpass", "dbpass");
			dbserver = prop.getProperty("dbserver", "dbserver");
			dbname = prop.getProperty("dbname", "dbname");
		} catch (FileNotFoundException e1) {
			try (OutputStream os = new FileOutputStream(fileName)) {
				prop.setProperty("nickname", "nickname");
				prop.setProperty("password", "password");
				prop.setProperty("dbuser", "dbuser");
				prop.setProperty("dbpass", "dbpass");
				prop.setProperty("dbserver", "dbserver");
				prop.setProperty("dbname", "dbname");
				prop.store(os, "");
			} catch (FileNotFoundException e) {
				Logger.STACK("", e);
			} catch (IOException e) {
				Logger.STACK("", e);
			}
		} catch (IOException e1) {
			Logger.STACK("", e1);
		}
	}
}