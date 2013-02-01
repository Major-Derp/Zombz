Zombz Readme File

-- Zombz can be just a drag and drop plugin, but it should be configured to your needs.

-- There is now Automatic Config Updating. There is an option in the Config to enable it or not.
   With Automatic config updating, When there is a new change to the config, it will automatically update it, and
   Overwrite it with a new config file.

-- This Readme Can be deleted and regenerated with each new plugin version.

Config Layout:
----------------------------
Zombie_Damage: Goes by damage in hearts 1-10 hearts damage
----------------------------
Zombie_Spawn_On_Death: Configures if you want a zombie to spawn on death with your inventory.
----------------------------
Zombie_Death_During_Day: Configures if you want zombies to die during the day or not.
----------------------------
Zombie_Potion_Effect(Seconds): How Many Seconds you want the Potion Effect to last.
----------------------------
Zombie_Group_Spawn: Enable/Disable if you want Zombies to spawn in Groups.
----------------------------
Zombie_Group_Spawn_Number: How many zombies should spawn in a group if it group spawning is enabled
----------------------------
Zombie_Random_Group_Spawn: Set this to true if you want zombies to spawn in random amounts in groups.
----------------------------
**Note, When Random group spawn is True, The Zombie_Group_Spawn_Number does not effect zombie spawning.
----------------------------
Zombie_Random_Group_Spawn_Number: The number to choose from for random spawning, it will choose from 0 to This number for random spawning.
----------------------------
Only_Zombies: This Mode makes it so only zombies spawn. No other entities will spawn other than zombies.
----------------------------
Auto_Config_Update: This is an option if you want the config to update automatically. 
----------------------------
Enabled_Worlds:
  - world
  - whatever
**This is the list for the enabled worlds. By default it includes the default world, but you should change it to your needs.
**NOTE: If you don't want Zombz enabled in any worlds, then in the enabled worlds, Put NONE or some other unused world name.
----------------------------
--Note, Do not edit the Version, as this is used to tell if the config needs updating or not.
End of Readme, Now go out and Battle those Dang Zombies!