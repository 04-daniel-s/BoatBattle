package de.lecuutex.boatbattle.minigame.ingame.teams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

/**
 * A class created by yi.dnl - 19.04.2022 / 21:35
 */

@AllArgsConstructor
@Getter
public enum TeamsEnum {
    RANDOM(8 + 9 + 9, "§eZufällig", Material.WHITE_WOOL, Material.WHITE_CONCRETE,NamedTextColor.GRAY),
    BLUE(8 + 3, "§9Team Blau", Material.BLUE_WOOL,Material.BLUE_CONCRETE, NamedTextColor.BLUE),
    RED(8 + 7, "§cTeam Rot", Material.RED_WOOL,Material.RED_CONCRETE,NamedTextColor.RED);

    private final int inventorySlot;

    private final String name;

    private final Material teamSelectItem;

    private final Material sheepMaterial;

    private NamedTextColor chatColor;
}
