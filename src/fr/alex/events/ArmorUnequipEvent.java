package fr.alex.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class ArmorUnequipEvent extends Event {

	public Player player;
	public ItemStack item;
	public static final HandlerList handlers = new HandlerList();
	
	public ArmorUnequipEvent(Player player, ItemStack item) {
		this.player = player;
		this.item = item;
	}
	
	public Player getPlayer() {
		return player;
	}

	public ItemStack getItem() {
		return item;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
