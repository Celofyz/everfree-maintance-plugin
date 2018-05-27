package pl.everfree.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pl.everfree.mc.Everfree;
import pl.everfree.util.Config;

public class Database {
	
	static String connect;
	
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	static Connection conn;
	
	Config config;
	
	static int whitelistMode = 1;

	public Database(Config config){
		 this.config = config;
		 connect = "jdbc:mysql://" + config.getDatabaseIP()
		 	+ "/" + config.getDatabaseName()
		 	+ "?user=" + config.getDatabaseUsername()
		 	+ "&password=" + config.getDatabasePassword();
		 
		 /*Import settings from database*/
		 try {
			/*Connect to database*/
			conn = DriverManager.getConnection(connect);
			
			/*Query*/
			String q = "SELECT setting FROM settings WHERE setting_id = 1";
			pstmt = conn.prepareStatement(q);
			rs = pstmt.executeQuery();
			
			/*read settings from database*/
			if(rs.next()){
				whitelistMode = rs.getInt("setting");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				/*Disconnect from database*/
				pstmt.close();
				conn.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isAllowed(String user){
		boolean result = false;
		
		try {
			/*Connect to database*/
			conn = DriverManager.getConnection(connect);
			
			/*Query*/
			String q = "SELECT staff, launcher_auth FROM member WHERE username = ?";
			pstmt = conn.prepareStatement(q);
			pstmt.setString(1, user);
			rs = pstmt.executeQuery();
			
			/*Check if player is in database and if developer mode is on (int ev == 1) check if player is dev.*/
			if(rs.next()) {
				
				result = (whitelistMode == 0 && rs.getInt("launcher_auth") == 1) ||
						whitelistMode == 1 && rs.getInt("staff") == 1 && rs.getInt("launcher_auth") == 1;
				
				q = "UPDATE member SET launcher_auth = 0 WHERE username = ?";
				pstmt = conn.prepareStatement(q);
				pstmt.setString(1, user);
				pstmt.executeUpdate();
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			result =  false;
			
		} finally {
			try {
				/*Disconnect from database*/
				conn.close();
				pstmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
