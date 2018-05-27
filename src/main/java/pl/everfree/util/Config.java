package pl.everfree.util;

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

}
