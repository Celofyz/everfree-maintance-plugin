package pl.everfree.mc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import pl.everfree.util.Config;
import pl.everfree.util.Database;

public final class Everfree extends JavaPlugin {
	
	private Config config;
	private PlayerMap playerMap;
	private Database db;
	
	@Override
	public void onEnable(){
		config = new Config(this);
		playerMap = new PlayerMap();
		new Events(this, playerMap);
		db = new Database(config);
	}
}
