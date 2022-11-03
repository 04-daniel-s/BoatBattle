package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.PlayerStateEnum;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * A class created by yi.dnl - 20.04.2022 / 01:12
 */

public class BlockBreakListener implements Listener {
    private final List<Material> whitelistedBlocks = ConfigManager.getWhitelistedBlocks();

    @EventHandler
    public void onEvent(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (BoatBattle.getInstance().getGameHandler().getGameState() != GameStateEnum.INGAME) {
            event.setCancelled(true);
            return;
        }

        if (BoatBattle.getInstance().getGameHandler().getPlayer(event.getPlayer()).getPlayerStateEnum() == PlayerStateEnum.SPECTATOR) {
            event.setCancelled(true);
        }

        if (whitelistedBlocks.contains(block.getType())) {
            event.setDropItems(true);

            if (block.getState() instanceof InventoryHolder) {
                for (ItemStack itemStack : ((InventoryHolder) block.getState()).getInventory()) {
                    if (itemStack == null) continue;
                    if (itemStack.getType() == Material.AIR) continue;

                    block.getLocation().getWorld().dropItem(block.getLocation(), itemStack);
                }
            }
        } else {
            event.setDropItems(false);
        }

        if (!BoatBattle.getInstance().canPlaceAndBreakBlocks(event.getBlock().getLocation())) event.setCancelled(true);
    }
}
