package me.Iqbal.Zombz;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Zombz extends JavaPlugin{
	
	public static Zombz plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	
	public static File pluginFolder;
    public static File readme;
    //public static FileConfiguration playerDataConfig;
	
    @Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		getLogger().info(pdfFile.getName() + " is now Disabled.");
		// Make some Static variables Null
	}
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
	    PluginManager pm = getServer().getPluginManager();
	    pm.registerEvents(new ZombieListener(this), this);
	    readmecreate();
	    this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " is now Enabled.");
	    if (!new File(getDataFolder(), "config.yml").exists()) {
	      saveResource("config.yml", false);
	      getLogger().info("Generating a new config for you.");
	      saveDefaultConfig();
	    } else {
	      @SuppressWarnings("unused")
		  FileConfiguration config = getConfig();
	      boolean update = this.getConfig().getBoolean("Auto_Config_Update");
			if(update == true){
				getLogger().info("Automatic Config Update is set to True.");
				String configVersion = this.getConfig().getString("Do_Not_Edit_Below_This_Line.Version").toLowerCase();
				String pluginVersion = pdfFile.getVersion().toLowerCase();
				if(!(configVersion.equals(pluginVersion))){
					saveResource("config.yml", true);
					getLogger().info("Old Config File Found. Now Updating to new Config File");
				    saveDefaultConfig();
				}else{
					getLogger().info("Config found. Using it.");
				}
			}else{
				getLogger().info("Config found. Using it.");
				getLogger().info("Automatic config update is turned off.");
			}
	    }
	}

	/*private void configcheck() {
		if (new File(getDataFolder(), "config.yml").exists()) {
			boolean update = this.getConfig().getBoolean("Auto_Config_Update");
			if(update == true){
				getLogger().info("Automatic Config Update is set to True.");
				PluginDescriptionFile pdfFile = getDescription();
				String configVersion = this.getConfig().getString("Do_Not_Edit_Below_This_Line.Version").toLowerCase();
				String pluginVersion = pdfFile.getVersion().toString().toLowerCase();
				if(configVersion != pluginVersion){
					saveResource("config.yml", false);
					getLogger().info("Old Config File Found. Now Updating to new Config File");
				    saveDefaultConfig();
				}
			}
		}
		
	}*/

	private void readmecreate() {
		  pluginFolder = getDataFolder();
		  readme = new File(pluginFolder, "Readme.txt");
		  if(!pluginFolder.exists()){
			  try{
				  pluginFolder.mkdir();
			  }
			  catch(Exception ex){
				  ex.printStackTrace();
				  getLogger().info("Creation of folder:" + pluginFolder + "failed.");
			  }
		  }
		  if(!readme.exists()){
			  try{
				  readme.createNewFile();
				  saveResource("Readme.txt", true);
				  getLogger().info("Creating a new Readme.txt");
			  }
			  catch(Exception ex){
				  ex.printStackTrace();
				  getLogger().info("Creation of Readme.txt Failed");
			  }
		  }
		}
	}
