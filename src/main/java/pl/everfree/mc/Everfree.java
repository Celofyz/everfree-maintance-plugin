package pl.everfree.mc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Everfree extends JavaPlugin {
	
	@Override
	public void onEnable(){
		new Events(this);
	}

}
