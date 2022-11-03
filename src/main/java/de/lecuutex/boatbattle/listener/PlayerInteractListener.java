package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.PlayerStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamsEnum;
import de.lecuutex.boatbattle.minigame.lobby.adminkit.AdminItemsEnum;
import de.lecuutex.boatbattle.utils.GameHandler;
import de.lecuutex.boatbattle.utils.Utils;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.PermissionsEnum;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import de.lecuutex.boatbattle.utils.enums.config.SpecialItemEnum;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * A class created by yi.dnl - 19.04.2022 / 21:59
 */

public class PlayerInteractListener implements Listener {

    private final GameHandler gameHandler = BoatBattle.getInstance().getGameHandler();

    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        ItemStack item = null;

        if (event.getItem() != null) {
            if (event.getItem().getType() == Material.AIR) return;
            item = event.getItem();
        }

        Player player = event.getPlayer();
        MinigamePlayer minigamePlayer = gameHandler.getPlayer(player);

        if (minigamePlayer.getPlayerStateEnum() == PlayerStateEnum.SPECTATOR) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
            }
        }

        if (item == null) return;

        if (minigamePlayer.getPlayerStateEnum() == PlayerStateEnum.SPECTATOR) {
            if (event.getAction() != Action.RIGHT_CLICK_AIR) {
                event.setCancelled(true);
            }

            String itemType = item.getType().toString();
            if (!event.getAction().isRightClick()) return;

            if (item.getType() == Material.RED_DYE) player.performCommand("hub");
            if (item.getType() == Material.BOOK) BoatBattle.getInstance().getGuiManager().getKitGUI().open(player);
            if (itemType.substring(itemType.length() - 4).equals(Material.WHITE_WOOL.toString().substring(Material.WHITE_WOOL.toString().length() - 4))) {
                BoatBattle.getInstance().getGuiManager().getTeamsGUI().open(player);
            }
        }

        if (gameHandler.getGameState() == GameStateEnum.INGAME) {
            Location location = player.getLocation();
            for (SpecialItemEnum specialItem : SpecialItemEnum.values()) {
                if (specialItem == SpecialItemEnum.NONE) continue;
                if (item.getType() != specialItem.getMaterial()) continue;
                specialItem.getFunction().accept(event);
                event.setCancelled(true);
                if (specialItem == SpecialItemEnum.WORKBENCH) return;
                item.setAmount(item.getAmount() - 1);
            }

            if (!gameHandler.isTest()) return;
            if (!player.hasPermission(PermissionsEnum.ADMIN.getPermission())) return;

            for (AdminItemsEnum itemsEnum : AdminItemsEnum.values()) {
                if (item.getType() != itemsEnum.getMaterial()) continue;

                if (itemsEnum == AdminItemsEnum.HEAL_ITEM_SPAWN) {
                    ConfigManager.set(ConfigEnum.LOCATIONS, PlaceholderEnum.HEAL_ITEM_SPAWNPOINT, location);
                } else if (itemsEnum == AdminItemsEnum.REVIVE_ITEM_SPAWN) {
                    ConfigManager.set(ConfigEnum.LOCATIONS, PlaceholderEnum.REVIVE_ITEM_SPAWNPOINT, location);
                } else if (itemsEnum == AdminItemsEnum.MAP_CENTER_SPAWN) {
                    ConfigManager.set(ConfigEnum.LOCATIONS, PlaceholderEnum.MAP_CENTER_SPAWNPOINT, location);
                }

                Utils.sendMessage(player, "Der Spawnpunkt §e" + itemsEnum.getItemName().substring(8) + " §7wurde gesetzt!");

                if (itemsEnum.getSpawnPointsAmount() == 1) continue;

                BoatBattle.getInstance().getGuiManager().getKitGUI().getAdminKit().getCurrentSpawnPoint().put(player.getUniqueId().toString(), itemsEnum);

                Utils.sendMessage(player, "Folgende Teams stehen zur Auswahl: ");
                for (TeamsEnum teamsEnum : TeamsEnum.values()) {
                    if (teamsEnum == TeamsEnum.RANDOM) continue;
                    Utils.sendMessage(player, teamsEnum.getName());
                }
                Utils.sendMessage(player, "Bitte gebe ein Team ein: ");
            }
        }
    }
}
