package de.lecuutex.boatbattle.utils.configs;

import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.SpecialItemEnum;
import de.lecuutex.boatbattle.minigame.lobby.Kit;
import de.lecuutex.boatbattle.minigame.lobby.KitItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.Collections;

/**
 * A class created by yi.dnl - 18.04.2022 / 16:46
 */

public class KitsConfig extends AbstractConfig {

    public KitsConfig() {
        super(ConfigEnum.KITS);
    }

    @Override
    public void setDefaults() {
        set("kits", Collections.singletonList(new Kit(Collections.singletonList(new KitItem("§ctest", Material.BONE, 1, Enchantment.KNOCKBACK, 1, 0, true, false, true)), "§ctest", Material.BARREL, 1)));
    }
}
