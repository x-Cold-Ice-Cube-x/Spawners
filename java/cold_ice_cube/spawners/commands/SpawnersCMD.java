package cold_ice_cube.spawners.commands;

import cold_ice_cube.spawners.Replacer;
import cold_ice_cube.spawners.Spawners;
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

public class SpawnersCMD implements CommandExecutor {
    Spawners instance;

    public SpawnersCMD(Spawners instance) {
        this.instance = instance;
        instance.getCommand("spawners").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {

            // ---------reload----------
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("spawners.spawners.reload")) {
                    // если есть право на команду релоад
                    instance.reloadConfig();
                    sender.sendMessage(Replacer.getMessageFromConfig(
                            instance, "messages.reload_command"));
                }
                else {
                    // нет права на команду релоад
                    sender.sendMessage(Replacer.getMessageFromConfig(
                            instance, "messages.command_exception", command.getName() + " " + args[0]));
                }
            }

            //---------help-------------
            else if (args[0].equalsIgnoreCase("help")) {
                if (sender.hasPermission("spawners.spawners.help")) {
                    // отправить меню
                    sender.sendMessage(Replacer.getListMessageFromConfig(
                            instance, "messages.help_menu"));
                }
                else {
                    sender.sendMessage(Replacer.getMessageFromConfig(
                            instance, "messages.command_exception", command.getName() + " " + args[0]));
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
                                    sender.sendMessage(Replacer.getMessageFromConfig_give(instance,
                                            "messages.give_command", getString(entity) + "_spawner",
                                            sender.getName(), number.toString()));

                                }
                                catch (NumberFormatException exception) {
                                    // неправильный набор параметров в функции
                                    sender.sendMessage(Replacer.getMessageFromConfig(
                                            instance, "messages.incorrect_parameters_exception"));
                                }

                            }
                            else {
                                // моб не найден
                                sender.sendMessage(Replacer.getMessageFromConfig(
                                        instance, "messages.invalid_entity"));
                            }

                        }
                        else {
                            // игрок не найден
                            sender.sendMessage(Replacer.getMessageFromConfig(
                                    instance, "messages.invalid_player"));
                        }


                    }
                    else {
                        // неправильный набор параметров в функции
                        sender.sendMessage(Replacer.getMessageFromConfig(
                                instance, "messages.incorrect_parameters_exception"));
                    }

                }
                else {
                    sender.sendMessage(Replacer.getMessageFromConfig(
                            instance, "messages.command_exception", command.getName() + " " + args[0]));
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
                                sender.sendMessage(Replacer.getMessageFromConfig_set(instance,
                                        "messages.set_command", getString(entity) + ChatColor.RESET));
                            }
                            else {
                                // в руке не спавнер
                                sender.sendMessage(
                                        Replacer.getMessageFromConfig(instance, "messages.invalid_itemstack"));
                            }
                        }
                        else {
                            sender.sendMessage(
                                    Replacer.getMessageFromConfig(instance, "messages.invalid_entity"));
                        }
                    }
                    else {
                        // неправильный набор параметров в функции
                        sender.sendMessage(
                                Replacer.getMessageFromConfig(instance, "messages.incorrect_parameters_exception"));
                    }
                }
                else {
                    // нет права на команду
                    sender.sendMessage(
                            Replacer.getMessageFromConfig(instance, "messages.command_exception",
                                    command.getName() + " " + args[0]));
                }

            }


        }
        else {
            // неправильный набор параметров в функции
            sender.sendMessage(Replacer.getMessageFromConfig(instance, "messages.incorrect_parameters_exception"));
        }

        return true;
    }

    public ItemStack setItemMeta(ItemStack spawner, String entity) {
        spawner.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemMeta spawner_meta = spawner.getItemMeta();
        spawner_meta.getPersistentDataContainer().set(new NamespacedKey(instance, "Entity"),
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
        return  instance.getServer().getPlayer(player_name).getInventory();
    }

}


