package x_cold_ice_cube_x.spawners;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import x_cold_ice_cube_x.spawners.commands.SpawnersCMD;
import x_cold_ice_cube_x.spawners.commands.SpawnersTC;
import x_cold_ice_cube_x.spawners.commands.getCustomPickaxeCMD;
import x_cold_ice_cube_x.spawners.commands.getCustomPickaxeTC;
import x_cold_ice_cube_x.spawners.events.Events;

import java.util.logging.Logger;

public final class Spawners extends JavaPlugin {

    public Logger logger = Bukkit.getLogger();
    private static Spawners instance;

    public static Spawners getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();

        logger.info("[Spawners] Enabling Spawners 1.0!");

        getServer().getPluginManager().registerEvents(new Events(), this);
        getCommand("spawners").setExecutor(new SpawnersCMD());
        getCommand("spawners").setTabCompleter(new SpawnersTC());

        getCommand("getCustomPickaxe").setExecutor(new getCustomPickaxeCMD());
        getCommand("getCustomPickaxe").setTabCompleter(new getCustomPickaxeTC());




    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
        logger.info("[Spawners] Disabling Spawners 1.0!");
    }
}
