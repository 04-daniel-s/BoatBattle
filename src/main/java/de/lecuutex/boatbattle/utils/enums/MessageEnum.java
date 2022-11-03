package de.lecuutex.boatbattle.utils.enums;

import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import lombok.Getter;

/**
 * A class created by yi.dnl - 19.04.2022 / 14:32
 */

public enum MessageEnum {

    NO_PERMISSIONS("§cDu hast dazu keine Berechtigung!"),
    NOT_ENOUGH_PLAYER("§cDer Start benötigt mindestens " + ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.LOBBY_MIN_PLAYER) + " Spieler!"),
    START("§7Das Spiel hat nun begonnen!");

    @Getter
    private final String message;

    MessageEnum(String message) {
        this.message = message;
    }
}
