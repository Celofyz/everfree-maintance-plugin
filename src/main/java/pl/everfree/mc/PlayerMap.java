package pl.everfree.mc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PlayerMap {
	Map<String, PlayerStatistics> map = new HashMap<String, PlayerStatistics>();
	
	public void addPlayer(String playerName){
		map.put(playerName, new PlayerStatistics(this, playerName));
	}
	
	public PlayerStatistics getPlayer(String playerName){
		return map.get(playerName);
	}
	
	public void removePlayer(String playerName){
		//TODO:send playerdata to server before removing
		map.remove(playerName);
	}

	public Map<String, PlayerStatistics> getMap() {
		return map;
	}
}
