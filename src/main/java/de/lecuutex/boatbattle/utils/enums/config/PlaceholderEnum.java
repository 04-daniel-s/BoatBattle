package de.lecuutex.boatbattle.utils.enums.config;

import lombok.Getter;

/**
 * A class created by yi.dnl - 19.04.2022 / 14:02
 */

public enum PlaceholderEnum {
    PREFIX("plugin.name"),
    LOBBY_COUNTDOWN("lobby.countdown"),
    LOBBY_MIN_PLAYER("lobby.min_player"),
    TEAM_SIZE("lobby.team_groesse"),

    MAP_CENTER_SPAWNPOINT("ingame.map-mitte"),
    MAP_BUILDING_ALLOWED_RADIUS("ingame.bauen-erlaubt-radius"),
    AREA_LEAVING_FORBIDDEN_RADIUS("ingame.verlassen-verboten-radius"),

    TEAM_BLUE_SPAWNPOINT("ingame.team-blue.spawnpoint"),
    TEAM_RED_SPAWNPOINT("ingame.team-red.spawnpoint"),
    TEAM_SPAWNPROTECTION_RADIUS("ingame.team.bauschutz-radius"),

    TEAM_BLUE_SHEEP_SPAWNPOINT("ingame.team-blue.schaf-spawnpoint"),
    TEAM_RED_SHEEP_SPAWNPOINT("ingame.team-red.schaf-spawnpoint"),
    SHEEP_SPAWNPROTECTION_RADIUS("ingame.schaf.bauschutz-radius"),

    REVIVE_ITEM_SPAWNPOINT("ingame.specialitem.wiederbelebungitem.spawnpoint"),
    REVIVE_ITEM_SPAWNTIME_IN_SECONDS("ingame.specialitem.wiederbelebungitem.spawnzeit-in-sekunden"),
    REVIVE_ITEM_SPAWNPROTECTION_RADIUS("ingame.specialitem.wiederbelebungitem.bauschutz-radius"),
    REVIVE_ITEM_HEARTS_IN_PERCENTAGE("ingame.specialitem.wiederbelebungitem.herzen-in-prozenten"),

    HEAL_ITEM_SPAWNPOINT("ingame.specialitem.heilitem.spawnpoint"),
    HEAL_ITEM_SPAWNPROTECTION_RADIUS("ingame.specialitem.heilitem.bauschutz-radius"),
    HEAL_ITEM_SPAWNTIME_IN_SECONDS("ingame.specialitem.heilitem.spawnzeit-in-sekunden"),
    HEAL_ITEM_HEALING_IN_HEARTS("ingame.specialitem.heilitem.heilung-in-ganzen-herzen"),

    BLOCK_WHITELIST("ingame.blockWhitelist"),

    SHEEP_HEARTS("ingame.schaf.ganze-herzen"),
    SHEEP_START_DIEING_AFTER_X_SECONDS("ingame.schaf.todesprozess-start-nach-x-sekunden"),
    SHEEP_MINUS_HEARTS_PER_MINUTE("ingame.schaf.verlust-ganzer-herzen-pro-minute"),

    INGAME_PLAYTIME("ingame.spielzeit-bis-spielende"),
    RESTART_COUNTDOWN("restart.countdown");

    @Getter
    private final String name;

    PlaceholderEnum(String name) {
        this.name = name;
    }
}
