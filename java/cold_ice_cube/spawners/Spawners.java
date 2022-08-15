package cold_ice_cube.spawners;

import cold_ice_cube.spawners.commands.SpawnersCMD;
import cold_ice_cube.spawners.commands.SpawnersTC;
import cold_ice_cube.spawners.events.Events;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Spawners extends JavaPlugin {

    public Logger logger = Bukkit.getLogger();


    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        logger.info("[Spawners] Enabling Spawners 1.0!");

        new Events(this); // implements Listener
        new SpawnersCMD(this); // implements CommandExecutor
        new SpawnersTC(this); // implements TabCompleter


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("[Spawners] Disabling Spawners 1.0!");
    }
}
