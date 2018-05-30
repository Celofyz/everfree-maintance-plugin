package pl.everfree.mc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import pl.everfree.mc.util.Database;

public class PlayerMap {
	Map<String, PlayerStatistics> map = new HashMap<String, PlayerStatistics>();
	
	public void addPlayer(String playerName){
		map.put(playerName, new PlayerStatistics(this, playerName));
	}
	
	public PlayerStatistics getPlayer(String playerName){
		return map.get(playerName);
	}
	
	public void removePlayer(String playerName){
		Database.send_stats(map.get(playerName));
		map.remove(playerName);
	}

	public Map<String, PlayerStatistics> getMap() {
		return map;
	}
}
