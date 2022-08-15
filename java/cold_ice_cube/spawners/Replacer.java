package cold_ice_cube.spawners;


import java.util.List;

public class Replacer {

    public static String getMessageFromConfig(Spawners instance, String path){
        return instance.getConfig().getString(path)
                .replace('&', '§');
    }
    public static String getMessageFromConfig(Spawners instance, String path, String command) {
        return instance.getConfig().getString(path)
                .replace('&', '§')
                .replace("%command%", command);
    }
    public static String getListMessageFromConfig(Spawners instance, String path) {
        String result = "";
        List<String> messages = (List<String>) instance.getConfig().getList(path);

        for (String message:messages) {
            result += message.replace('&', '§') + "\n";
        }
        return result;
    }

    // ---------give-----------
    public static String getMessageFromConfig_give(Spawners instance,
                                                   String path, String item, String player, String number) {
        return instance.getConfig().getString(path)
                .replace('&', '§')
                .replace("%item%", item)
                .replace("%player%", player)
                .replace("%number%", number);

    }

    //---------set------------
    public static String getMessageFromConfig_set(Spawners instance, String path, String entity_type) {
        return instance.getConfig().getString(path)
                .replace('&', '§')
                .replace("%entity_type%", entity_type);
    }


}
