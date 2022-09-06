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
import java.util.List;

public class getCustomPickaxeCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            if (Bukkit.getServer().getPlayer(args[0]) != null) {
                // игрок существует
                if (Material.getMaterial(args[1]) != null) {
                    // если тип существует
                    // numberFormatException
                    // IllegalArgumentException
                    try {
                        ItemStack pickaxe = setEnchantments(new ItemStack(Material.getMaterial(args[1])), Reader.translateListToList("pickaxe.enchantments"));

                        ItemMeta pickaxeItemMeta = pickaxe.getItemMeta();
                        pickaxeItemMeta.setDisplayName(Reader.translateString("pickaxe.display_name"));
                        pickaxeItemMeta.setLore(Reader.translateListToList("pickaxe.description"));
                        pickaxeItemMeta.getPersistentDataContainer().set(new NamespacedKey(Spawners.getInstance(), "Verify"), PersistentDataType.STRING, "Verify");
                        if (Reader.translateBoolean("pickaxe.hide_enchantments")) {
                            pickaxeItemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        }
                        pickaxe.setItemMeta(pickaxeItemMeta);

                        Bukkit.getServer().getPlayer(sender.getName()).getInventory().addItem(pickaxe);
                    }
                    catch (Exception exception) {
                        // NumberFormatException - человек неправильно ввел уровень зачарования
                        // IllegalArgumentException - человек неправильно ввел само зачарование
                        sender.sendMessage(Reader.translateString("messages.invalid_pickaxe_data"));
                    }
                }
                else {
                    sender.sendMessage(Reader.translateString("messages.invalid_itemStack"));
                }
            }
            else {
                sender.sendMessage(Reader.translateString("messages.invalid_player"));
            }
        }
        else {
            // неправильный набор параметров в функции
            sender.sendMessage(Reader.translateString("messages.incorrect_parameters_exception"));
        }
        return true;
    }


    public ItemStack setEnchantments(ItemStack item, List<String> enchantments) {
        for (String enchantment: enchantments) {
            String[] itemInfo = enchantment.split(";");
            item.addUnsafeEnchantment(Enchantment.getByName(itemInfo[0].toUpperCase()), Integer.parseInt(itemInfo[1].replace(" ", "")));
        }
        return item;
    }
}
