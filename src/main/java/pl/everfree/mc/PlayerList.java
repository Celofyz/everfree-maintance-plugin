package pl.everfree.mc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PlayerList {
	Map<String, PlayerStatistics> map = new HashMap<String, PlayerStatistics>();
	
	public void addPlayer(String playerName, int brokenBlocks, int enchantedItems, int deaths, int blocksFromFurnance, int level){
		map.put(playerName, new PlayerStatistics(this, playerName, brokenBlocks, enchantedItems, deaths, blocksFromFurnance, level));
	}
	
	public PlayerStatistics getPlayer(String playerName){
		return map.get(playerName);
	}
	
	public void removePlayer(String playerName){
		map.remove(playerName);
	}

	public Map<String, PlayerStatistics> getMap() {
		return map;
	}
}
