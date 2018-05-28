package pl.everfree.mc;

/*This class will held statistics of a player for given time
 * Those will be sent to database by other class*/
public class PlayerStatistics {
	
	PlayerMap playerList;
	
	String playerName;
	
	private int brokenBlocks;
	private int enchantedItems;
	private int deaths;
	private int furnance;
	private int level;
	private int level_record;
	
	public PlayerStatistics(PlayerMap playerList, String playerName){
		this.playerList = playerList;
		
		this.playerName = playerName;
		
		//TODO: Take those values from database
		brokenBlocks = 0;
		enchantedItems = 0;
		deaths = 0;
		furnance = 0;
		level = 0;
		level_record = 0;
	}
	
	public void remove(){
		playerList.removePlayer(playerName);
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
	
	public int getLevelRecord(){
		return level_record;
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
