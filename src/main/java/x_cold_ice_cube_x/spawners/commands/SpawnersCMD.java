package x_cold_ice_cube_x.spawners.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import x_cold_ice_cube_x.spawners.Spawners;
import x_cold_ice_cube_x.spawners.Util.Reader;

import java.util.HashMap;

public class SpawnersCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {

            // ---------reload----------
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("spawners.spawners.reload")) {
                    // если есть право на команду релоад
                    Spawners.getInstance().reloadConfig();
                    sender.sendMessage(Reader.translateString("messages.reload_command"));
                }
                else {
                    // нет права на команду релоад
                    HashMap<String, String> placeholders = new HashMap<>();
                    placeholders.put("%command%", command.getName());
                    sender.sendMessage(Reader.translateString("messages.command_exception", placeholders));
                }
            }

            //---------help-------------
            else if (args[0].equalsIgnoreCase("help")) {
                if (sender.hasPermission("spawners.spawners.help")) {
                    // отправить меню
                    sender.sendMessage(Reader.translateListToString("messages.help_menu"));
                }
                else {
                    HashMap<String, String> placeholders = new HashMap<>();
                    placeholders.put("%command%", command.getName());
                    sender.sendMessage(Reader.translateString("messages.command_exception", placeholders));
                }
            }

            //---------give-------------
            else if (args[0].equalsIgnoreCase("give")) {
                if (sender.hasPermission("spawners.spawners.give")) {
                    if (args.length > 3) {
                        if (sender.getServer().getPlayer(args[1]) != null) {
                            // игрок настоящий
                            if (EntityType.fromName(args[2]) != null) {
                                // настоящая сущность
                                try {
                                    // извлечение данных
                                    Integer number = Integer.parseInt(args[3]);
                                    String entity = args[2];

                                    ItemStack spawner = new ItemStack(Material.SPAWNER, number); // Создание спавнера с определенной метой
                                    setItemMeta(spawner, entity);
                                    getInventory(args[1]).addItem(spawner); // добавление спавнера в инвентарь

                                    // "&6Выдано &c%item% &6игроку &c%player% &6в количестве &c%number%&6!"
                                    HashMap<String, String> placeholders = new HashMap<>();
                                    placeholders.put("%item%", getString(entity) + "_spawner");
                                    placeholders.put("%player%", sender.getName());
                                    placeholders.put("%number%", number.toString());
                                    sender.sendMessage(Reader.translateString("messages.give_command", placeholders));

                                }
                                catch (NumberFormatException exception) {
                                    // неправильный набор параметров в функции
                                    sender.sendMessage(Reader.translateString("messages.incorrect_parameters_exception"));
                                }

                            }
                            else {
                                // моб не найден
                                sender.sendMessage(Reader.translateString("messages.invalid_entity"));
                            }

                        }
                        else {
                            // игрок не найден
                            sender.sendMessage(Reader.translateString("messages.invalid_player"));
                        }


                    }
                    else {
                        // неправильный набор параметров в функции
                        sender.sendMessage(Reader.translateString("messages.incorrect_parameters_exception"));
                    }

                }
                else {
                    HashMap<String, String> placeholders = new HashMap<>();
                    placeholders.put("%command%", command.getName());
                    sender.sendMessage(Reader.translateString("messages.command_exception", placeholders));
                }
            }

            //---------set-------------
            else if (args[0].equalsIgnoreCase("set")) {
                if (sender.hasPermission("spawners.spawners.set")) {
                    if (args.length > 1) {
                        if (EntityType.fromName(args[1]) != null) {
                            // настоящая сущность
                            if (getInventory(sender.getName()).getItemInMainHand().getType() == Material.SPAWNER) {
                                // если в руке именно спавнер

                                ItemStack spawner_in_hand = getInventory(sender.getName()).getItemInMainHand();
                                String entity = args[1];
                                ItemStack spawner = setItemMeta(spawner_in_hand, entity);

                                getInventory(sender.getName()).remove(spawner_in_hand); // удаление старого спавнера
                                getInventory(sender.getName()).addItem(spawner); // добавление нового

                                HashMap<String, String> placeholders = new HashMap<>();
                                placeholders.put("%entity_type%", getString(entity));
                                sender.sendMessage(Reader.translateString("messages.set_command", placeholders));
                            }
                            else {
                                // в руке не спавнер
                                sender.sendMessage(
                                        Reader.translateString("messages.invalid_itemstack"));
                            }
                        }
                        else {
                            sender.sendMessage(
                                    Reader.translateString("messages.invalid_entity"));
                        }
                    }
                    else {
                        // неправильный набор параметров в функции
                        sender.sendMessage(
                                Reader.translateString("messages.incorrect_parameters_exception"));
                    }
                }
                else {
                    // нет права на команду
                    HashMap<String, String> placeholders = new HashMap<>();
                    placeholders.put("%command%", command.getName());
                    sender.sendMessage(
                            Reader.translateString("messages.command_exception", placeholders));

                }

            }
            else {
                // неправильный набор параметров в функции
                sender.sendMessage(Reader.translateString("messages.incorrect_parameters_exception"));
            }


        }
        else {
            // неправильный набор параметров в функции
            sender.sendMessage(Reader.translateString("messages.incorrect_parameters_exception"));
        }

        return true;
    }

    public ItemStack setItemMeta(ItemStack spawner, String entity) {
        spawner.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemMeta spawner_meta = spawner.getItemMeta();
        spawner_meta.getPersistentDataContainer().set(new NamespacedKey(Spawners.getInstance(), "Entity"),
                PersistentDataType.STRING, entity.toUpperCase()); // добавление entity в контейнер
        spawner_meta.setDisplayName(ChatColor.RESET + "Рассадник моба - " + ChatColor.AQUA + getString(entity)); // добавление displayname
        spawner_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS); // удаление описанния (т.е остается только эффект)
        spawner.setItemMeta(spawner_meta);
        return spawner;
    }
    public String getString(String entity){
        return entity.substring(0, 1).toUpperCase() + entity.substring(1).toLowerCase();
    }
    public PlayerInventory getInventory(String player_name) {
        return Spawners.getInstance().getServer().getPlayer(player_name).getInventory();
    }

}


