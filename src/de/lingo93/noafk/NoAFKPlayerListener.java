package de.lingo93.noafk;


//import org.bukkit.Location;
import org.bukkit.event.player.*;
import org.bukkit.entity.Player;

/**
 * NoAFK for Bukkit
 * @author Lingo93
 */
public class NoAFKPlayerListener extends PlayerListener {
	
	private final NoAFK plugin;
	private final NoAFKWorker worker;

	public NoAFKPlayerListener(NoAFK instance) {
		plugin = instance;
		worker = new NoAFKWorker(plugin);
	}
	
	/**
	 * handles player join event
	 */
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		plugin.users.put(playerName, (System.currentTimeMillis()));
		if(NoAFK.perm((Player)player, "noafk.admin")) plugin.checkVersion(player);
	}
	
	/**
	 * handles player quit event
	 */
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		plugin.users.remove(playerName);
	}
	
	/**
	 * handles player move event
	 */
	public void onPlayerMove(PlayerMoveEvent event) {
		worker.playerActive((PlayerEvent)event);
	}
	
	/**
	 * handles player chat event
	 */
	public void onPlayerChat(PlayerChatEvent event) {
		worker.playerActive((PlayerEvent)event);
	}
	
	/**
	 * handles player interact event
	 */
	public void onPlayerInteract(PlayerInteractEvent event) {
		worker.playerActive((PlayerEvent)event);
	}
}
