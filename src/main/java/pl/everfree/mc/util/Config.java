package pl.everfree.mc.util;

import org.bukkit.configuration.file.FileConfiguration;

import pl.everfree.mc.Everfree;

public class Config {
	
	final FileConfiguration config;
	
	public Config(Everfree main){
		this.config = main.getConfig();
		
		config.addDefault("dbIP", "127.0.0.1");
		config.addDefault("dbName", "name");
		config.addDefault("dbUsername", "127.0.0.1");
		config.addDefault("dbPassword", "127.0.0.1");
		config.addDefault("dbTableName", "table");
		config.addDefault("dbNickname", "nickname");
		config.addDefault("dbBrokenBlocks", "brokenBlocks");
		config.addDefault("dbEnchantedItems", "enchantedItems");
		config.addDefault("dbDeath", "death");
		config.addDefault("dbFurnance", "furnance");
		config.addDefault("dbLevel", "level");
		config.addDefault("dbLevelRecord", "levelRecord");
	}

	public String getDatabaseIP() {
		return config.getString("dbIP");
	}

	public String getDatabaseName() {
		return config.getString("dbName");
	}

	public String getDatabaseUsername() {
		return config.getString("dbUsername");
	}

	public String getDatabasePassword() {
		return config.getString("dbPassword");
	}
	
	public String getTableName(){
		return config.getString("dbTableName");
	}
	
	public String getNickname(){
		return config.getString("dbNickname");
	}
	
	public String dbBrokenBlocks(){
		return config.getString("dbBrokenBlocks");
	}
	
	public String dbEnchantedItems(){
		return config.getString("dbEnchantedItems");
	}
	
	public String dbDeath(){
		return config.getString("dbDeath");
	}
	
	public String dbFurnance(){
		return config.getString("dbFurnance");
	}
	
	public String dbLevel(){
		return config.getString("dbLevel");
	}
	
	public String dbLevelRecord(){
		return config.getString("dbLevelRecord");
	}
	
}
