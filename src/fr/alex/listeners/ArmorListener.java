package fr.alex.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.alex.ArmorEvent;
import fr.alex.events.ArmorEquipEvent;
import fr.alex.events.ArmorUnequipEvent;

public class ArmorListener implements Listener {

	public HashMap<Player, ItemStack[]> armors = new HashMap<Player, ItemStack[]>();
	
	public HashMap<Player, ItemStack[]> getArmors() {return armors;}

	public ArmorListener() {
		Bukkit.getOnlinePlayers().stream().forEach(p -> getArmors().put(p, p.getEquipment().getArmorContents()));
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		getArmors().put(e.getPlayer(), e.getPlayer().getEquipment().getArmorContents());
		verify(e.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		getArmors().remove(e.getPlayer());
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			InventoryType inv = e.getInventory().getType();
			if(inv == InventoryType.CRAFTING || inv == InventoryType.PLAYER) 
				if(e.getSlotType() == SlotType.ARMOR || e.isShiftClick())
					verify(p);	
		}
	}
	
	@EventHandler
	public void onBreakItem(PlayerItemBreakEvent e) {
		verify(e.getPlayer());
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		verify(e.getEntity());
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = e.getItem();
			if(item == null) return;
			String name = item.getType().name();
			if(name.contains("_HELMET") || name.contains("_CHESTPLATE") || name.contains("_LEGGINGS") || name.contains("_BOOTS"))
				verify(p);
		}
	}
	
	public void verify(Player p) {
		new BukkitRunnable() {		
			@Override
			public void run() {
				ItemStack[] current = p.getEquipment().getArmorContents();
				ItemStack[] old = getArmors().get(p);
				
				for(int i = 0; i < current.length; i++) {
					if(current[i].getType() != Material.AIR && old[i].getType() == Material.AIR) {
						Bukkit.getPluginManager().callEvent(new ArmorEquipEvent(p, current[i]));
					}else if(current[i].getType() == Material.AIR && old[i].getType() != Material.AIR) {
						Bukkit.getPluginManager().callEvent(new ArmorUnequipEvent(p, old[i]));
					}
				}
				
				getArmors().put(p, current);
			}
		}.runTaskLater(ArmorEvent.getInstance(), 1L);
	}	
	
}
