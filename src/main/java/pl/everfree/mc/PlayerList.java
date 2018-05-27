package pl.everfree.mc;

import java.util.LinkedList;

public class PlayerList {
	LinkedList<PlayerStatistics> list = new LinkedList<PlayerStatistics>();
	
	public void addPlayer(String playerName, int brokenBlocks, int enchantedItems, int deaths, int blocksFromFurnance, int level){
		list.add(new PlayerStatistics(this, playerName, brokenBlocks, enchantedItems, deaths, blocksFromFurnance, level));
	}
	
	public void remove(PlayerStatistics player){
		list.remove(player);
	}

	public LinkedList<PlayerStatistics> getList() {
		return list;
	}
	
}
