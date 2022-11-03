package de.lecuutex.boatbattle.utils.configs;

import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * A class created by yi.dnl - 18.04.2022 / 16:30
 */

@Getter
public abstract class AbstractConfig {

    private YamlConfiguration config;

    private File file;

    private final ConfigEnum configEnum;

    public AbstractConfig(ConfigEnum configEnum) {
        this.configEnum = configEnum;
        createConfig();
    }

    private void createConfig() {
        String fileName = configEnum.getName();
        file = new File("plugins/Minigame/" + fileName + ".yml");
        config = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            new File("plugins/Minigame/").mkdir();
            file = new File("plugins/Minigame/" + fileName + ".yml");
            config = YamlConfiguration.loadConfiguration(file);

            setDefaults();

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bukkit.getConsoleSender().sendMessage("§a[INFO] Die Datei " + fileName + " wurde erstellt!");
        }

        ConfigManager.addConfig(configEnum, this);
    }

    public void set(String path, Object object) {
        config.set(path, object);
    }

    public void set(PlaceholderEnum path, Object object) {
        config.set(path.getName(), object);
    }

    public abstract void setDefaults();
}
