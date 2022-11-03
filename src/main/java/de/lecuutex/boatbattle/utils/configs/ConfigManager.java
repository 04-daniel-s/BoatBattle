package de.lecuutex.boatbattle.utils.configs;

import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import de.lecuutex.boatbattle.minigame.lobby.Kit;
import org.bukkit.Material;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class created by yi.dnl - 18.04.2022 / 16:33
 */

public class ConfigManager {

    private static final HashMap<ConfigEnum, AbstractConfig> CONFIGS = new HashMap<>();

    public ConfigManager() {
        loadConfigs();
    }

    public static void loadConfigs() {
        new KitsConfig();
        new MainConfig();
        new LocationsConfig();
    }

    public static void addConfig(ConfigEnum configEnum, AbstractConfig config) {
        ConfigManager.CONFIGS.put(configEnum, config);
    }

    public static Object get(ConfigEnum configEnum, PlaceholderEnum placeHolder) {
        return CONFIGS.get(configEnum).getConfig().get(placeHolder.getName());
    }

    public static void set(ConfigEnum config, String path, Object o) {
        CONFIGS.get(config).set(path, o);

        try {
            CONFIGS.get(config).getConfig().save(CONFIGS.get(config).getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void set(ConfigEnum config, PlaceholderEnum placeholderEnum, Object o) {
        CONFIGS.get(config).set(placeholderEnum, o);

        try {
            CONFIGS.get(config).getConfig().save(CONFIGS.get(config).getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Kit> getKits() {
        return (List<Kit>) CONFIGS.get(ConfigEnum.KITS).getConfig().get("kits");
    }

    public static Kit getKit(String name) {
        return getKits().stream().filter(v -> v.getName().equals(name)).findFirst().get();
    }

    public static List<Material> getWhitelistedBlocks() {
        List<String> list = (List<String>) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.BLOCK_WHITELIST);
        List<Material> blocks = new ArrayList<>();

        for (String s : list) {
            blocks.add(Material.getMaterial(s));
        }

        return blocks;
    }
}
