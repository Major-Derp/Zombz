package me.Iqbal.Zombz;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

import net.minecraft.server.v1_5_R3.EntityZombie;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class ZombieListener implements Listener {
	
	public HashMap<UUID, ItemStack[]> hashmap = new HashMap<UUID, ItemStack[]>();
	
	Zombz plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public ZombieListener(Zombz instance) {
		plugin = instance;
	}
}
		
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		String WorldName = event.getLocation().getWorld().getName();
		if(plugin.getConfig().getStringList("Enabled_Worlds").contains(WorldName)){
			Entity entity = event.getEntity();
			Location location = event.getLocation();
			World world = location.getWorld();
			net.minecraft.server.v1_5_R3.World mcWorld = ((CraftWorld) world).getHandle();
    		net.minecraft.server.v1_5_R3.Entity mcEntity = (((CraftEntity) entity).getHandle());
    		if(entity.getType() != EntityType.ZOMBIE){
    			if(plugin.getConfig().getBoolean("Only_Zombies") == true){
    				event.setCancelled(true);
    				EntityZombie zombie = new EntityZombie(mcWorld);
    				zombie.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);
    				mcWorld.removeEntity((net.minecraft.server.v1_5_R3.Entity) mcEntity);
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
/* 

Edited to remove all functions except group spawning.
As of 1.5 Zombies now do variable damage based on their HP and this needs to be retained.

*/
