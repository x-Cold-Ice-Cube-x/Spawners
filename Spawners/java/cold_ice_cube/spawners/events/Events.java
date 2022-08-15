package cold_ice_cube.spawners.events;


import cold_ice_cube.spawners.Replacer;
import cold_ice_cube.spawners.Spawners;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class Events implements Listener {

    Spawners instance;

    public Events(Spawners instance) {
        this.instance = instance;
        instance.getServer().getPluginManager().registerEvents(this, instance);

    }

    @EventHandler
    public void onBreakSpawner(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if ((block.getType() == Material.SPAWNER) && (player.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH))) {
            if (player.hasPermission("spawners.break")) {
                // Если сломанный блок - спавнер и если преедмет зачаровн на шелковое касание
                CreatureSpawner state = (CreatureSpawner) block.getState(); // Получение объекта спавнера
                EntityType entity = state.getSpawnedType(); // Получение объекта EntityType

                event.setExpToDrop(0); // отключение получения опыта
                event.setDropItems(false); // отключение дропа обычного лута (хотя его и нет)

                ItemStack spawner = new ItemStack(Material.SPAWNER, 1); // Создание спавнера с определенной метой
                spawner.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1); // добавление зачарования
                ItemMeta spawner_meta = spawner.getItemMeta();
                spawner_meta.getPersistentDataContainer().set(new NamespacedKey(instance, "Entity"), PersistentDataType.STRING, entity.toString()); // добаление данных в контейнер
                spawner_meta.setDisplayName(ChatColor.RESET + "Рассадник моба - " + ChatColor.AQUA + entity.toString().substring(0,1).toUpperCase() + entity.toString().substring(1).toLowerCase());
                spawner_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS); // удаление описанния (т.е остается только эффект)
                spawner.setItemMeta(spawner_meta);
                block.getWorld().dropItemNaturally(block.getLocation(), spawner); // Выпадение айтема с определенными мета данными
            }
            else{
                // вывести сообщение из конфига
                event.setCancelled(false);

            }

        }
    }

    @EventHandler
    public void onPlaceSpawner(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (block.getType() == Material.SPAWNER) {
            if (player.hasPermission("spawners.place")) {
                ItemStack spawner_itemstack = player.getInventory().getItemInMainHand(); // Получение айтемстака спавнера
                ItemMeta spawner_meta = spawner_itemstack.getItemMeta(); // Получение меты спавнера
                try {
                    EntityType entity = EntityType.fromName(spawner_meta.getPersistentDataContainer().get(new NamespacedKey(instance, "Entity"), PersistentDataType.STRING));  // Получение EntityType entity
                    CreatureSpawner spawner = (CreatureSpawner) block.getState(); // Получение объекта CreatureSpawner
                    spawner.setSpawnedType(entity); // Установка entity
                    spawner.update();
                } catch (IllegalArgumentException ignored) {

                }
            }
            else {
                event.setCancelled(true);
                player.sendMessage(Replacer.getMessageFromConfig(instance,"messages.place_spawner_exception"));
            }
        }
    }
}










