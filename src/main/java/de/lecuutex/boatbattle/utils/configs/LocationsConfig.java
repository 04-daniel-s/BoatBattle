package de.lecuutex.boatbattle.utils.configs;

import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Arrays;

/**
 * A class created by yi.dnl - 19.04.2022 / 14:01
 */

public class LocationsConfig extends AbstractConfig {

    public LocationsConfig() {
        super(ConfigEnum.LOCATIONS);
    }

    @Override
    public void setDefaults() {
        Location defaultLoc = new Location(Bukkit.getWorld("world"), 0, 0, 100, 0, 0);
        Arrays.stream(PlaceholderEnum.values()).filter(p -> p.toString().endsWith("_SPAWNPOINT")).forEach(p -> set(p, defaultLoc));
    }
}
