package pl.everfree.mc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;

import pl.everfree.util.Database;
import pl.everfree.util.Web;

public class Events implements Listener{
	
	private PlayerMap playerMap;
	
	public Events(Everfree plugin, PlayerMap playerMap) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.playerMap = playerMap;
	}

	/*Called when a player attemps to connect to server. It is used for authorization*/
	@EventHandler
	public void onPreLogin(AsyncPlayerPreLoginEvent player){
		if(!Database.isAllowed(player.getName()))
			player.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, "You are not on the whitelist or the server is in development mode");
	}
	
	/*Called when a player connects to server*/
	@EventHandler
	public void onLogin(PlayerLoginEvent player){
		//TODO: read stats from json file
		playerMap.addPlayer(player.getPlayer().getName());
	}
	
	/*Called when a player disconnects from server*/
	@EventHandler
	public void onQuit(PlayerQuitEvent player){
		playerMap.removePlayer(player.getPlayer().getName());
	}
	
	/**Player statistics related events*/
	
	/*Called when a player breaks a block*/
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		playerMap.getPlayer(event.getPlayer().getName()).addBrokenBlocks(1);
	}
	
	/*Called when a player enchants an item*/
	@EventHandler
	public void onEnchantItem(EnchantItemEvent event){
		playerMap.getPlayer(event.getEnchanter().getName()).addEnchantedItems(1);
	}
	
	/*Called when a player dies
	 *NOTE: In future this event may have other purposes than statistics*/
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		playerMap.getPlayer(event.getEntity().getName()).addDeaths(1);
	}
	
	/*Called when a player takes item out of furnance*/
	@EventHandler
	public void onFurnanceExtract(FurnaceExtractEvent event){
		playerMap.getPlayer(event.getPlayer().getName()).addDeaths(event.getItemAmount());
	}
	
	/*Called when player's level changes*/
	@EventHandler
	public void onPlayerLevelChange(PlayerLevelChangeEvent event){
		playerMap.getPlayer(event.getPlayer().getName()).setLevel(event.getNewLevel());;
	}
	
	/**Server statistic related events*/
	
	/*Called when a tree grows. It tells how ecological our players are.
	 * It is propably possible to track how many trees planted each player
	 * but there is risk of getting null when certain tree was not planted
	 * by a player. Too dangerous for now. Lets get back to it in future
	 * 
	 * Idea: It is possible to check if a tree is grown using bonemeal and
	 * then count it as player statistick. It would not count trees planted
	 * but grown by itself.*/
	@EventHandler
	public void onTreeGrow(StructureGrowEvent event){
		//TODO: Increment amount of planted trees
	}
	
	/*Called when weather on server changes*/
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event){
		//TODO: Lets show current server weather on the website
	}
	
	/*Called when lightning strikes*/
	@EventHandler
	public void onLightning(LightningStrikeEvent event){
		//TODO: Increment amount of lightings
		event.getLightning();
	}
	
	/*Called when a block is being ignited*/
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event){
		if(event.getIgnitingEntity().getType() == EntityType.LIGHTNING){
			//TODO: Send position where lighting caused fire
		}
	}	
}
