package pl.everfree.mc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import pl.everfree.mc.Everfree;
import pl.everfree.mc.PlayerMap;
import pl.everfree.mc.PlayerStatistics;

public class Database {
	
	private static String connect;
	
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	private static Connection conn;
	
	private Config config;
	
	private static int whitelistMode = 1;

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
	
	/*Prepares a query that will be send to update the statistics database*/
	private void query_builder(PlayerMap playerMap) throws SQLException{
		
		String query = "";
		
		/*Its a prototype. Tables names will be different or will be taken from config*/
		
		for(Map.Entry<String, PlayerStatistics> entry : playerMap.getMap().entrySet()){
			query = query + "UPDATE Players SET"
					+ " brokenBlocks = " + entry.getValue().getBrokenBlocks()
					+ ", enchantedItems = " + entry.getValue().getEnchantedItems()
					+ ", deaths = " + entry.getValue().getDeaths()
					+ ", level = " + entry.getValue().getLevel()
					+ ", levelRecord = " + entry.getValue().getLevel()
					+ " WHERE username = ?; ";
		}
		
		pstmt = conn.prepareStatement(query);
		
		for(Map.Entry<String, PlayerStatistics> entry : playerMap.getMap().entrySet()){
			int i = 1;
			pstmt.setString(i, entry.getValue().getName());
			i++;
		}
	}
	
	/*Sends player stats to database*/
	public void send_stats(PlayerMap playerMap){
		
		try{
			/*Connect to database*/
			conn = DriverManager.getConnection(connect);
			
			/*Send query*/
			query_builder(playerMap);
			rs = pstmt.executeQuery();
			
		} catch(SQLException e){
			e.printStackTrace();
		} finally{
			try{
				/*Disconnect from server*/
				conn.close();
				pstmt.close();
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}		
	}
	
	/*Obsolete*/
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
