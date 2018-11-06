package me.noeffort.namenotifier;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.noeffort.namenotifier.actionbar.Actionbar;

public class Main extends JavaPlugin {

	private static Main instance;
	private Actionbar actionbar;
	
	@Override
	public void onEnable() {
		instance = this;
		enableHandlers();
		setupActionbar();
	}
	
	private void enableHandlers() {
		getServer().getPluginManager().registerEvents(new ChatHandler(), this);
	}
	
	private void setupActionbar() {
		
		Actionbar.plugin = this;
		Actionbar.nmsver = Bukkit.getServer().getClass().getPackage().getName();
		Actionbar.nmsver = Actionbar.nmsver.substring(Actionbar.nmsver.lastIndexOf(".") + 1);
		
	}
	
	public static Main get() {
		return instance;
	}
	
	public Actionbar getActionbar() {
		return actionbar;
	}
	
}
