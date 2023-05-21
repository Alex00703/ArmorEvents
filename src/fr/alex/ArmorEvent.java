package fr.alex;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.alex.listeners.ArmorListener;

public class ArmorEvent extends JavaPlugin {
	
	public static ArmorEvent instance;
	
	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(new ArmorListener(), this);
	}

	public static ArmorEvent getInstance() {
		return instance;
	}
	
	

}
