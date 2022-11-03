package de.lecuutex.boatbattle.utils.configs;

import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import org.bukkit.Material;

import java.util.Collections;

/**
 * A class created by yi.dnl - 18.04.2022 / 16:45
 */

public class MainConfig extends AbstractConfig {

    public MainConfig() {
        super(ConfigEnum.CONFIG);
    }

    @Override
    public void setDefaults() {
        set(PlaceholderEnum.PREFIX, "§cBoatBattle §7|");

        set(PlaceholderEnum.LOBBY_COUNTDOWN, 60);
        set(PlaceholderEnum.LOBBY_MIN_PLAYER, 4);
        set(PlaceholderEnum.TEAM_SIZE, 4);

        set(PlaceholderEnum.BLOCK_WHITELIST, Collections.singletonList(Material.BEDROCK.toString()));

        set(PlaceholderEnum.REVIVE_ITEM_SPAWNTIME_IN_SECONDS, 5);
        set(PlaceholderEnum.REVIVE_ITEM_HEARTS_IN_PERCENTAGE, 20);

        set(PlaceholderEnum.HEAL_ITEM_SPAWNTIME_IN_SECONDS, 5);
        set(PlaceholderEnum.HEAL_ITEM_HEALING_IN_HEARTS, 5);

        set(PlaceholderEnum.SHEEP_HEARTS, 100);
        set(PlaceholderEnum.SHEEP_START_DIEING_AFTER_X_SECONDS, 180);
        set(PlaceholderEnum.SHEEP_MINUS_HEARTS_PER_MINUTE, 10);

        set(PlaceholderEnum.INGAME_PLAYTIME, 360);
        set(PlaceholderEnum.RESTART_COUNTDOWN, 15);

        for (PlaceholderEnum placeholderEnum : PlaceholderEnum.values()) {
            if(placeholderEnum.toString().endsWith("RADIUS")) {
                set(placeholderEnum,5);
            }
        }
    }
}
