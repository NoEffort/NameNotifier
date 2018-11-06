package me.noeffort.namenotifier;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.noeffort.namenotifier.actionbar.Actionbar;

public class ChatHandler implements Listener {

	Main plugin = Main.get();
	
	public ChatHandler() {}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		for(Player target : Bukkit.getOnlinePlayers()) {
		
			String message = event.getMessage();
			
			if(message.toLowerCase().contains(target.getName().toLowerCase())) {
				Actionbar.sendActionbar(target, 
						ChatColor.translateAlternateColorCodes('&', "&eYou have been pinged by: &6" + event.getPlayer().getName()), 100);
				target.playSound(target.getLocation(), Sound.BLOCK_NOTE_CHIME, 4.0F, 1.5F);
				return;
			}
		}
	}
}
