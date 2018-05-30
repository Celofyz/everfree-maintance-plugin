package pl.everfree.mc;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import pl.everfree.mc.util.Config;
import pl.everfree.mc.util.Database;

public final class Everfree extends JavaPlugin {
	
	private Config config;
	private PlayerMap playerMap;

	@Override
	public void onEnable(){
		config = new Config(this);
		playerMap = new PlayerMap();
		new Database(config);		
		new Events(this, playerMap);
		
		setScheduler();
	}
	
	@Override
	public void onDisable() {
		Database.send_stats(playerMap);
	}
	
	@SuppressWarnings("deprecation")
	private void setScheduler(){
		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleAsyncRepeatingTask(this, new Runnable(){

			@Override
			public void run() {
				Database.send_stats(playerMap);
			}
			
		}, 20L*60L*2L, 20L*60L*2L); //20 ticks = 1s, scheduler set for 2 minutes
	}
}
