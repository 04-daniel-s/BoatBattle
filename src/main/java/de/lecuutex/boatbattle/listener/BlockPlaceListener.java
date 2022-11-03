package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import io.papermc.paper.event.player.PlayerChangeBeaconEffectEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;

/**
 * A class created by yi.dnl - 20.04.2022 / 01:11
 */

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onEvent(BlockPlaceEvent event) {
        if (BoatBattle.getInstance().getGameHandler().getGameState() != GameStateEnum.INGAME) event.setCancelled(true);
        if(!BoatBattle.getInstance().canPlaceAndBreakBlocks(event.getBlockPlaced().getLocation())) event.setCancelled(true);
    }
}
