package pl.everfree.mc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Everfree extends JavaPlugin {
	
	private PlayerMap playerMap;
	
	@Override
	public void onEnable(){
		playerMap = new PlayerMap();
		new Events(this, playerMap);
	}
}
