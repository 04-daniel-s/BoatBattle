package de.lecuutex.boatbattle.minigame.ingame.teams;

import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;


/**
 * A class created by yi.dnl - 23.04.2022 / 17:38
 */

@Getter
@Setter
public abstract class Team {

    private final TeamsEnum teamsEnum;

    private final Location teamSpawn;

    private final Location sheepSpawn;

    private final PlaceholderEnum path;

    private final DyeColor color;

    private final TeamSheep teamSheep;

    public Team(TeamsEnum teamsEnum, Location sheepSpawn, PlaceholderEnum path, DyeColor color) {
        this.teamsEnum = teamsEnum;
        this.teamSpawn = (Location) ConfigManager.get(ConfigEnum.LOCATIONS, path);
        this.sheepSpawn = sheepSpawn;
        this.path = path;
        this.color = color;

        teamSheep = new TeamSheep(this, sheepSpawn);
    }
}
