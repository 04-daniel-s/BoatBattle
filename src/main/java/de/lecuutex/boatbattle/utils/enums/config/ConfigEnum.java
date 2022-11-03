package de.lecuutex.boatbattle.utils.enums.config;

import lombok.Getter;

/**
 * A class created by yi.dnl - 19.04.2022 / 14:26
 */

public enum ConfigEnum {
    CONFIG("config"),
    KITS("kits"),
    LOCATIONS("locations"),
    PERMISSIONS("permissions");

    @Getter
    private final String name;

    ConfigEnum(String name) {
        this.name = name;
    }
}
