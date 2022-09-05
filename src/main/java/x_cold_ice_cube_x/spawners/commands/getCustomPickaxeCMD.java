package x_cold_ice_cube_x.spawners.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import x_cold_ice_cube_x.spawners.Spawners;
import x_cold_ice_cube_x.spawners.Util.Reader;

import java.util.HashMap;

public class getCustomPickaxeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            if (Bukkit.getServer().getPlayer(args[0]) != null) {
                // игрок существует
                if (Material.getMaterial(args[1]) != null) {
                    // если тип существует
                    ItemStack pickaxe = new ItemStack(Material.getMaterial(args[1]), 1);
                    pickaxe.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);

                    ItemMeta pickaxeItemMeta = pickaxe.getItemMeta();
                    pickaxeItemMeta.setDisplayName(Reader.translateString("pickaxe.display_name"));
                    pickaxeItemMeta.setLore(Reader.translateListToList("pickaxe.description"));
                    pickaxeItemMeta.getPersistentDataContainer().set(new NamespacedKey(Spawners.getInstance(), "Verify"), PersistentDataType.STRING, "Verify");
                    pickaxe.setItemMeta(pickaxeItemMeta);

                    Bukkit.getServer().getPlayer(sender.getName()).getInventory().addItem(pickaxe);
                }
                else {
                    sender.sendMessage(Reader.translateString("messages.invalid_itemStack"));
                }
            }
            else {
                sender.sendMessage(Reader.translateString("messages.invalid_player"));
            }
        }
        return true;
    }
}
