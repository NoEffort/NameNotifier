package me.noeffort.namenotifier;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener {

	Main plugin = Main.get();
	
	public ChatHandler() {}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		for(Player target : Bukkit.getOnlinePlayers()) {
		
			String message = event.getMessage();
			
			if(message.toLowerCase().contains(target.getName().toLowerCase())) {
				String color = ChatColor.getLastColors(message);
				if(color == "") {
					event.setMessage(message.replaceAll("(?i)" + target.getName(), ChatColor.YELLOW + target.getName() + ChatColor.getByChar("f")));
					target.playSound(target.getLocation(), Sound.BLOCK_NOTE_CHIME, 4.0F, 1.5F);
					return;
				}
				event.setMessage(message.replaceAll("(?i)" + target.getName().toLowerCase(), ChatColor.YELLOW + target.getName() + color));
				target.playSound(target.getLocation(), Sound.BLOCK_NOTE_CHIME, 4.0F, 1.5F);
				return;
			}
		}
	}
}
