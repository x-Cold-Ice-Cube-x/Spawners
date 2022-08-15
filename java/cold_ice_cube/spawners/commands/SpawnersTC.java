package cold_ice_cube.spawners.commands;

import cold_ice_cube.spawners.Spawners;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SpawnersTC implements TabCompleter {

    Spawners instance;
    String[] mobs = new String[]{"axolotl", "bat", "bee", "blaze", "cat", "cave_spider", "chicken", "cod", "cow",
            "creeper", "dolphin", "donkey", "drowned", "elder_guardian", "enderman", "endermite", "evoker", "fox",
            "ghast", "guardian", "horse", "husk", "llama", "magma_cube", "mooshroom", "mule", "ocelot", "panda",
            "parrot", "phantom", "pig", "pillager", "polar_bear", "pufferfish", "rabbit", "ravager", "salmon",
            "sheep", "shulker", "silverfish", "skeleton", "skeleton_horse", "slime", "spider", "squid", "stray",
            "trader_llama", "tropical_fish", "turtle", "vex", "villager", "vindicator", "wandering_trader", "witch",
            "wither_skeleton", "wolf", "zombie", "zombie_horse", "zombie_pigman", "zombie_villager"};

    public SpawnersTC(Spawners instance) {
        this.instance = instance;
        instance.getCommand("spawners").setTabCompleter(this);

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String[] completers;

        if ((args.length == 1) && sender.hasPermission("spawners.spawners")) {
            // /spawners reload, /spawners give, /spawners set, /spawners help
            completers = new String[]{"reload", "give", "set", "help"};
            return Arrays.asList(completers);

        }

        else if (args.length == 2) {
            // ----give-----
            if (args[0].equalsIgnoreCase("give") && sender.hasPermission("spawners.spawners.give")) {
                completers = new String[]{sender.getName()};
                return Arrays.asList(completers);
            }

            //--------set---------
            else if (args[0].equalsIgnoreCase("set") && sender.hasPermission("spawners.spawners.set")) {
                return Arrays.asList(mobs);
            }
        }

        else if (args.length == 3) {
            // -----give-----
            if (args[0].equalsIgnoreCase("give") && sender.hasPermission("spawners.spawners.set")) {
                return Arrays.asList(mobs);

            }

        }

        else if (args.length == 4) {
            // -----give-----
            if (args[0].equalsIgnoreCase("give") && sender.hasPermission("spawners.spawners.set")) {
                completers = new String[]{"1", "8", "16", "32", "64"};
                return Arrays.asList(completers);
            }
        }

        return Collections.emptyList();
    }
}







