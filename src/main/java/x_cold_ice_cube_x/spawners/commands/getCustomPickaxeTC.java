package x_cold_ice_cube_x.spawners.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class getCustomPickaxeTC implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList(sender.getName());
        }
        else if (args.length == 2) {
            return Arrays.asList("WOODEN_PICKAXE", "STONE_PICKAXE", "IRON_PICKAXE", "GOLDEN_PICKAXE", "DIAMOND_PICKAXE", "NETHERITE_PICKAXE");
        }

        return Collections.emptyList();
    }
}
