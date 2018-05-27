package pl.everfree.util;

/*This class will send statistics to api or database or whaterever it will be
 * connected to in the Internet.*/
public class Web {
	
	/*Player related stats*/
	public static void onBlockBreak(String playerName, int amount){
		//TODO: Increment amount of breaked blocks
	}
	
	public static void onEnchantItem(String playerName, int amount){
		//TODO: Increment amount of enchanted items and spend EXP.
	}
	

	public static void onPlayerDeath(String playerName, int amount){
		//TODO: Increment death count. Update highest LVL and highest EXP.
	}

	public static void onFurnanceExtract(String playerName, int amount){
		//TODO: Increment amount of smelted items
	}

	public static void onPlayerLevelChange(String playerName, int level){
		//TODO: Set new player level in database
	}
	
	/*Server related stats*/
	public static void onTreeGrow(int amount){
		//TODO: Increment amount of planted trees
	}
	
	public static void onWeatherChange(String weatherType){
		//TODO: Lets show current server weather on the website
	}
	
	public static void onLightning(int x, int z, boolean fire){
		//TODO: Send location where lightning striken and if it caused fire
	}
	
	public static void onFire(){
		
	}

}
