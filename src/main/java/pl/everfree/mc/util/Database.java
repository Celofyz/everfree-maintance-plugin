package pl.everfree.mc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import pl.everfree.mc.PlayerMap;
import pl.everfree.mc.PlayerStatistics;

public class Database {
	
	private static String connect;
	
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	private static Connection conn;
	
	private static int whitelistMode = 1;

	public Database(Config config){
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
	private static void query_builder(PlayerMap playerMap) throws SQLException{
		
		String query = "";
		
		/*Its a prototype. Tables names will be different or will be taken from config*/
		
		for(Map.Entry<String, PlayerStatistics> entry : playerMap.getMap().entrySet()){
			query = query + "UPDATE players SET"
					+ " brokenBlocks = " + entry.getValue().getBrokenBlocks()
					+ ", enchantedItems = " + entry.getValue().getEnchantedItems()
					+ ", deaths = " + entry.getValue().getDeaths()
					+ ", furnance = " + entry.getValue().getFurnance()
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
	
	private static void query_builder(PlayerStatistics playerStats) throws SQLException{
		
		String query = "";
		
		/*Its a prototype. Tables names will be different or will be taken from config*/
		
		query = query + "UPDATE players SET"
				+ " brokenBlocks = " + playerStats.getBrokenBlocks()
				+ ", enchantedItems = " + playerStats.getEnchantedItems()
				+ ", deaths = " + playerStats.getDeaths()
				+ ", furnance = " + playerStats.getFurnance()
				+ ", level = " + playerStats.getLevel()
				+ ", levelRecord = " + playerStats.getLevel()
				+ " WHERE username = ?; ";
		
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, playerStats.getName());
	}
	
	/*Sends player stats to database*/
	public static void send_stats(PlayerMap playerMap){
		
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
	
	/*Sends player stats to database*/
	public static void send_stats(PlayerStatistics playerStats){
		
		try{
			/*Connect to database*/
			conn = DriverManager.getConnection(connect);
			
			/*Send query*/
			query_builder(playerStats);
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
	
	static public int[] get_stats(String playerName){
		
		int[] stats = {0,0,0,0,0,0};
		
		try {
			/*Connect to database*/
			conn = DriverManager.getConnection(connect);
			
			/*Query*/
			String q = "SELECT brokenBlocks, enchantedItems, deaths, furnance, level, levelRecord FROM players WHERE username = ?";
			pstmt = conn.prepareStatement(q);
			pstmt.setString(1, playerName);
			rs = pstmt.executeQuery();
			
			/*read settings from database*/
			if(rs.next()){
				stats[0] = rs.getInt("brokenBlocks");
				stats[1] = rs.getInt("enchantedItems");
				stats[2] = rs.getInt("deaths");
				stats[3] = rs.getInt("furnance");
				stats[4] = rs.getInt("level");
				stats[5] = rs.getInt("levelRecord");
			}
			
		} catch (SQLException e) {
			
			try{
				/*Connect to database*/
				conn = DriverManager.getConnection(connect);
				
				/*Query*/
				String q = "INSERT INTO players (brokenBlocks, enchantedItems, deaths, furnance, level, levelRecord)"
						+ " VALUES (0,0,0,0,0,0)";
				pstmt = conn.prepareStatement(q);
				pstmt.setString(1, playerName);
				rs = pstmt.executeQuery();
				
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
			
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
		return stats;
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
