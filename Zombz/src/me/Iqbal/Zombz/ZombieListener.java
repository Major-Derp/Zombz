package me.Iqbal.Zombz;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

import net.minecraft.server.v1_4_R1.EntityZombie;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.craftbukkit.v1_4_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_4_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ZombieListener implements Listener {
	
	public HashMap<UUID, ItemStack[]> hashmap = new HashMap<UUID, ItemStack[]>();
	
	Zombz plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public ZombieListener(Zombz instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
		String WorldName = event.getEntity().getLocation().getWorld().getName();
		if(plugin.getConfig().getStringList("Enabled_Worlds").contains(WorldName)){
			Entity e = event.getEntity();
			Entity damager = event.getDamager();
		
			if(e instanceof Player) {
				if(damager instanceof Zombie){
					event.setDamage((plugin.getConfig().getInt("General_Zombie_Modifications.Zombie_Damage")) * 2);
					Player player = (Player) e;
					//PotionEffectType effect1 = PotionEffectType.BLINDNESS;
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (plugin.getConfig().getInt("General_Zombie_Modifications.Zombie_Potion_Effect(Seconds)")) * 25, 1));
			}
		}
	}
}
		
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		String WorldName = event.getEntity().getLocation().getWorld().getName();
		if(plugin.getConfig().getStringList("Enabled_Worlds").contains(WorldName)){
			if((plugin.getConfig().getBoolean("Zombie_Spawn.Zombie_Spawn_On_Death")) == true){
			
				Player player = event.getEntity();
				EntityDamageEvent Damager = player.getLastDamageCause();
				if(Damager instanceof EntityDamageByEntityEvent){
					EntityDamageByEntityEvent edbe = (EntityDamageByEntityEvent) Damager;
					Entity killer = edbe.getDamager();
					if(killer instanceof Zombie){
						ItemStack[] items = player.getInventory().getContents();
						Entity zombie = player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
						((Zombie) zombie).getEquipment().setItemInHand(new ItemStack(268,1));
						((Zombie) zombie).getEquipment().setChestplate(new ItemStack(303,1));
						UUID zid = zombie.getUniqueId();
						hashmap.put(zid, items);
						event.getDrops().clear();
						/* if(plugin.getConfig().getBoolean("Custom_Death_Message") == true){
							event.setDeathMessage(plugin.getConfig().getString("Zombie_Death_Message"));
					} */
				}
			}	
		}
	}
}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		String WorldName = event.getEntity().getLocation().getWorld().getName();
		if(plugin.getConfig().getStringList("Enabled_Worlds").contains(WorldName)){
			Entity entity = event.getEntity();
			UUID ZombId = entity.getUniqueId();
			if(hashmap.containsKey(ZombId)){
				ItemStack[] items = hashmap.get(ZombId);
				for(ItemStack item : items){
					event.getDrops().add(item);
				}
				hashmap.remove(ZombId);
				event.setDroppedExp(plugin.getConfig().getInt("General_Zombie_Modifications.Zombie_Exp_Drop"));
			}
			else if(entity instanceof Zombie){
				event.getDrops().clear();
				List<String> setdrops = plugin.getConfig().getStringList("General_Zombie_Modifications.Zombie_Drops");
				for(String drops : setdrops){
					if(drops.contains(",")){
						String[] string = drops.split(",");
						ItemStack items = new ItemStack(Integer.parseInt(string[0]), 1, (byte)Integer.parseInt(string[1]));
						items.setTypeId(Integer.parseInt(string[1]));
						event.getDrops().add(items);
					}else{
						ItemStack test = new ItemStack(Integer.parseInt(drops));
						event.getDrops().add(test);
					}
				}
				event.setDroppedExp(plugin.getConfig().getInt("General_Zombie_Modifications.Zombie_Exp_Drop"));
			}
		}
	}
	
	@EventHandler
	public void onEntityCombust(EntityCombustEvent event) {
		String WorldName = event.getEntity().getLocation().getWorld().getName();
		if(plugin.getConfig().getStringList("Enabled_Worlds").contains(WorldName)){
			if((plugin.getConfig().getBoolean("General_Zombie_Modifications.Zombie_Death_During_Day")) == false){
			
				if ((Bukkit.getWorld(event.getEntity().getWorld().getName()).getTime() >= 0L) && (Bukkit.getWorld(event.getEntity().getWorld().getName()).getTime() <= 24000L) && 
						(event.getEntityType() == EntityType.ZOMBIE)){
					event.setCancelled(true);
			}
		}
	}
}
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		String WorldName = event.getLocation().getWorld().getName();
		if(plugin.getConfig().getStringList("Enabled_Worlds").contains(WorldName)){
			Entity entity = event.getEntity();
			Location location = event.getLocation();
			World world = location.getWorld();
			net.minecraft.server.v1_4_R1.World mcWorld = ((CraftWorld) world).getHandle();
    		net.minecraft.server.v1_4_R1.Entity mcEntity = (((CraftEntity) entity).getHandle());
    		if(entity.getType() != EntityType.ZOMBIE){
    			if(plugin.getConfig().getBoolean("Only_Zombies") == true){
    				event.setCancelled(true);
    				EntityZombie zombie = new EntityZombie(mcWorld);
    				zombie.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);
    				mcWorld.removeEntity((net.minecraft.server.v1_4_R1.Entity) mcEntity);
    				mcWorld.addEntity(zombie, SpawnReason.NATURAL);
    			}
    		}
				if((plugin.getConfig().getBoolean("Zombie_Group_Spawn.Group_Spawn")) == true){
					SpawnReason reason = event.getSpawnReason();
					if(reason != SpawnReason.CUSTOM){
						if(entity instanceof Zombie){
							if(plugin.getConfig().getBoolean("Zombie_Group_Spawn.Zombie_Random_Group_Spawn") == true){
								Random rand = new Random();
								int pickednumber = rand.nextInt(plugin.getConfig().getInt("Zombie_Group_Spawn.Zombie_Random_Group_Spawn_Number")) + 1;
								for(int i = 1; i < pickednumber; i++){
								entity.getWorld().spawnEntity(entity.getLocation(), EntityType.ZOMBIE);
								}
							}
							else if(plugin.getConfig().getBoolean("Zombie_Group_Spawn.Zombie_Random_Group_Spawn") == false){
								int number = plugin.getConfig().getInt("Zombie_Group_Spawn.Zombie_Group_Spawn_Number");
								for(int i = 1; i < number; i++){
									entity.getWorld().spawnEntity(entity.getLocation(), EntityType.ZOMBIE);
							}
						}
					}
				}
			}
		}
	}
}
