package de.lecuutex.boatbattle.utils.enums;

import lombok.Getter;

/**
 * A class created by yi.dnl - 19.04.2022 / 14:27
 */


public enum PermissionsEnum {
    DEFAULT("spieler", "minigame.default"),
    PREMIUM("premium", "minigame.premium"),
    ADMIN("admin", "minigame.admin");

    @Getter
    private final String path;

    @Getter
    private final String permission;

    PermissionsEnum(String path, String permission) {
        this.path = path;
        this.permission = permission;
    }
}
