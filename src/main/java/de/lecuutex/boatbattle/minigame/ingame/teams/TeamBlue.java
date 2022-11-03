package de.lecuutex.boatbattle.minigame.ingame.teams;

import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import org.bukkit.DyeColor;
import org.bukkit.Location;


/**
 * A class created by yi.dnl - 23.04.2022 / 17:42
 */

public class TeamBlue extends Team {
    public TeamBlue() {
        super(TeamsEnum.BLUE, (Location) ConfigManager.get(ConfigEnum.LOCATIONS, PlaceholderEnum.TEAM_BLUE_SHEEP_SPAWNPOINT),
                PlaceholderEnum.TEAM_BLUE_SPAWNPOINT, DyeColor.BLUE);

    }
}
