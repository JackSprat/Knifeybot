package begin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import data.DataManager;

public class Test {
	public static void main (String[] args) {
		
		DataManager.initialiseDB();
		DataManager.addQuote("testchannel2", "testalias", "testuser", "testquote");
		
	}
}
