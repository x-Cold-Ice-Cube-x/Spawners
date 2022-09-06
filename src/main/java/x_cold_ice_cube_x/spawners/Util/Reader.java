package x_cold_ice_cube_x.spawners.Util;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import x_cold_ice_cube_x.spawners.Spawners;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Reader {

    public static String translateString(String path) {
        return ChatColor.translateAlternateColorCodes('&', Spawners.getInstance().getConfig().getString(path));
    }

    public static String translateString(String path , Map<String, String> placeholders) {
        return StringUtils.replaceEach(translateString(path), placeholders.keySet().toArray(new String[0]), placeholders.values().toArray(new String[0]));
    }

    public static String translateListToString(String path) {
        List<String> messages = Spawners.getInstance().getConfig().getStringList(path);
        StringBuilder result = new StringBuilder();
        for (String message: messages) {
            result.append(ChatColor.translateAlternateColorCodes('&', message)).append('\n');
        }
        return result.toString();
    }

    public static List<String> translateListToList(String path) {
        List<String> result = new java.util.ArrayList<>(Collections.emptyList());
        List<String> messages = Spawners.getInstance().getConfig().getStringList(path);
        for (String message: messages) {
            result.add(ChatColor.translateAlternateColorCodes('&', message));
        }
        return result;
    }

    public static Boolean translateBoolean(String path) {
        return Spawners.getInstance().getConfig().getBoolean(path);
    }


}
