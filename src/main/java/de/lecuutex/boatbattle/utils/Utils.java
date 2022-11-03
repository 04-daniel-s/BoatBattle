package de.lecuutex.boatbattle.utils;

import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.MessageEnum;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import org.bukkit.entity.Player;

/**
 * A class created by yi.dnl - 19.04.2022 / 21:40
 */

public class Utils {
    public static String PREFIX;

    public Utils() {
        String prefix = (String) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.PREFIX);
        PREFIX = (prefix.charAt(prefix.length() - 1) == ' ' ? prefix : (prefix + " ")) + "§7";
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(PREFIX + message);
    }

    public static void sendMessage(Player player, MessageEnum message) {
        sendMessage(player,message.getMessage());
    }
}
