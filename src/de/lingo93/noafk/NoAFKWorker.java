package de.lingo93.noafk;

import java.io.FileOutputStream;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.event.player.*;
import org.bukkit.entity.Player;

/**
 * NoAFK  for Bukkit
 * @author Lingo93
 */
public class NoAFKWorker {
	
	public NoAFK plugin;
	
	public NoAFKWorker (NoAFK tplugin){
		plugin = tplugin;
	}
	
	/**
	 * updates the activities of players
	 */
	public void playerActive(PlayerEvent event){
		Player player = event.getPlayer();
		String playerName = player.getName();
		plugin.users.put(playerName, System.currentTimeMillis());
	}
	
	/**
	 * handle command: noafk
	 */
	public boolean noafkCommand(Player player, String commandName){	
		if(!NoAFK.perm(player, "noafk.admin")){
			player.sendMessage(ChatColor.RED + "You don't have Permissions to use this Command!");
			return true;
		}
		String version = plugin.getDescription().getVersion();
		String newestVersion = plugin.getNewestVersion();
		
		player.sendMessage(ChatColor.GOLD + "******************* version *******************");
		player.sendMessage(ChatColor.GOLD + "* currently installed version: "+version);
		player.sendMessage(ChatColor.GOLD + "* newest available version: "+newestVersion);
		player.sendMessage(ChatColor.GOLD + "******************* configs *******************");
		player.sendMessage(ChatColor.GOLD + "* kick after seconds away: " + (plugin.configTime/1000));
		player.sendMessage(ChatColor.GOLD + "* check interval: " + (plugin.configInterval/1000));
		player.sendMessage(ChatColor.GOLD + "***********************************************");
		
		return true;
	}
	
	/**
	 * handle command: cnoafk
	 */
	public boolean cnoafkCommand(Player player, String commandName, String[] args){	
		if(!NoAFK.perm(player, "noafk.admin.config")){
			player.sendMessage(ChatColor.RED + "You don't have Permissions to use this Command!");
			return true;
		}
		if(args.length == 0){
			player.sendMessage(ChatColor.RED + "Wrong count of parameters! Usage: /cnoafk <option> <value>");
			return true;
		}
		if(args[0].equals("time")){
			long value;
			try{
				value = Long.parseLong(args[1]);
			}catch(Exception e){
				player.sendMessage(ChatColor.RED + "Value must be an integer!");
				return true;
			}			
			if(NoAFK.configFile.exists()){ 
		        try { 
		        	NoAFK.configFile.createNewFile(); 
		            FileOutputStream out = new FileOutputStream(NoAFK.configFile); 
		            NoAFK.prop.put("time", Long.toString(value));
		            NoAFK.prop.put("interval", Long.toString(plugin.configInterval/1000));
		            NoAFK.prop.store(out, "Do NOT edit this config! Use the Ingame-Commands!");
		            out.flush();  
		            out.close(); 
		        } catch (IOException ex) { 
		        	player.sendMessage(ChatColor.RED + "Can't write config file!");
		            ex.printStackTrace(); //explained below.
		            return true;
		        }
		        plugin.configTime = value*1000;
		        player.sendMessage(ChatColor.GREEN + args[0] + " setting successfully changed!");
		        return true;			 
			}else{
				player.sendMessage(ChatColor.RED + "No config file found!");
				return true;
			}			
		}
		if(args[0].equals("interval")){
			long value;
			try{
				value = Long.parseLong(args[1]);
			}catch(Exception e){
				player.sendMessage(ChatColor.RED + "Value must be an integer!");
				return true;
			}			
			if(NoAFK.configFile.exists()){ 
		        try { 
		        	NoAFK.configFile.createNewFile(); 
		            FileOutputStream out = new FileOutputStream(NoAFK.configFile); 
		            NoAFK.prop.put("time", Long.toString(plugin.configTime/1000));
		            NoAFK.prop.put("interval", Long.toString(value));
		            NoAFK.prop.store(out, "Do NOT edit this config! Use the Ingame-Commands!");
		            out.flush();  
		            out.close(); 
		        } catch (IOException ex) { 
		        	player.sendMessage(ChatColor.RED + "Can't write config file!");
		            ex.printStackTrace(); //explained below.
		            return true;
		        }
		        player.sendMessage(ChatColor.GREEN + args[0] + " setting successfully changed!" + ChatColor.DARK_PURPLE + " Please reload to refresh the timer.");
		        return true;			 
			}else{
				player.sendMessage(ChatColor.RED + "No config file found!");
				return true;
			}
			
		}		
		return true;
	}
}
