# ArmorEvents 1.8.8

Permet de vérifier via les évènement ArmorEquipEvent et ArmorUnequipEvent si des pièces d'armures on été ajoutées ou retirées

# Exemple d'utilisation

```
	@EventHandler
	public void onEquip(ArmorEquipEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		
		if(item.getType() == Material.DIAMOND_CHESTPLATE) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 1));
		}
	}
	
	@EventHandler
	public void onUnequip(ArmorUnequipEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		
		if(item.getType() == Material.DIAMOND_CHESTPLATE) {
			p.removePotionEffect(PotionEffectType.SPEED);
		}
	}
```
