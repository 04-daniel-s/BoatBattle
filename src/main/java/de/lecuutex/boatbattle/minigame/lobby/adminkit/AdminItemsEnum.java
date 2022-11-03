package de.lecuutex.boatbattle.minigame.lobby.adminkit;

import lombok.Getter;
import org.bukkit.Material;

/**
 * A class created by yi.dnl - 21.04.2022 / 23:35
 */

@Getter
public enum AdminItemsEnum {

    TEAM_SPAWN("§cSetze Teamspawns", 2, Material.NAME_TAG),
    TEAM_MOB_SPAWN("§cSetze Teamschafe", 2, Material.DIAMOND_HORSE_ARMOR),
    REVIVE_ITEM_SPAWN("§cSetze Wiederbelebungitemspawn", 1, Material.BLAZE_POWDER),
    HEAL_ITEM_SPAWN("§cSetze Heilitemspawn", 1, Material.GHAST_TEAR),
    MAP_CENTER_SPAWN("§cSetze Mapmittespawn", 1, Material.END_PORTAL_FRAME);

    private String itemName;

    private int spawnPointsAmount;

    private Material material;

    AdminItemsEnum(String itemName, int spawnPointsAmount, Material material) {
        this.itemName = itemName;
        this.spawnPointsAmount = spawnPointsAmount;
        this.material = material;
    }
}
