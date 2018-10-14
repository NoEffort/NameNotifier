package me.noeffort.namenotifier;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		enableHandlers();
	}
	
	private void enableHandlers() {
		getServer().getPluginManager().registerEvents(new ChatHandler(), this);
	}
	
	public static Main get() {
		return instance;
	}
	
}
