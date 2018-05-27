package pl.everfree.mc;

/*This class will held statistics of a player for given time
 * Those will be sent to database by other class*/
public class PlayerStatistics {
	
	PlayerList playerList;
	
	String playerName;
	
	private int brokenBlocks;
	private int enchantedItems;
	private int deaths;
	private int furnance;
	private int level;
	
	public PlayerStatistics(PlayerList playerList, String playerName, int brokenBlocks, int enchantedItems, int deaths, int blocksFromFurnance, int level){
		this.playerList = playerList;
		
		this.playerName = playerName;
		
		this.brokenBlocks = brokenBlocks;
		this.enchantedItems = enchantedItems;
		this.deaths = deaths;
		this.furnance = blocksFromFurnance;
		this.level = level;
	}
	
	public void remove(){
		playerList.remove(this);
	}
	
	public String getName(){
		return playerName;
	}
	
	public int getBrokenBlocks() {
		return brokenBlocks;
	}

	public int getEnchantedItems() {
		return enchantedItems;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getFurnance() {
		return furnance;
	}

	public int getLevel() {
		return level;
	}

	public void addBrokenBlocks(int brokenBlocks) {
		this.brokenBlocks = this.brokenBlocks + brokenBlocks;
	}

	public void addEnchantedItems(int enchantedItems) {
		this.enchantedItems = this.enchantedItems + enchantedItems;
	}

	public void addDeaths(int deaths) {
		this.deaths = this.deaths + deaths;
	}

	public void addFurnance(int furnance) {
		this.furnance = this.furnance + furnance;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
