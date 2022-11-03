package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamSheep;
import de.lecuutex.boatbattle.utils.enums.config.SheepItems;
import de.lecuutex.boatbattle.utils.enums.config.SpecialItemEnum;
import jdk.jshell.EvalException;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * A class created by yi.dnl - 08.05.2022 / 18:51
 */

public class PlayerInteractAtEntityListener implements Listener {
    @EventHandler
    public void onEvent(PlayerInteractAtEntityEvent event) {
        ItemStack item = event.getPlayer().getItemInHand();

        for (SheepItems sheepItem : SheepItems.values()) {
            if (item.getType() != sheepItem.getMaterial()) continue;
            if (!item.getItemMeta().getDisplayName().equals(sheepItem.getName())) continue;

            System.out.println(event.getRightClicked().getType());

            Location location = event.getRightClicked().getLocation();
            MinigamePlayer minigamePlayer = BoatBattle.getInstance().getGameHandler().getPlayer(event.getPlayer());
            TeamSheep teamSheep = BoatBattle.getInstance().getTeamHandler()
                    .getTeams().values().stream().filter(team -> team.getTeamsEnum() == minigamePlayer.getTeamsEnum()).
                    map(v -> v.getTeamSheep()).findFirst().get();

            if (sheepItem == SheepItems.SHEEP_HEAL_ITEM) {
                if (event.getRightClicked().getType() != EntityType.SHEEP) continue;
            } else {
                if (teamSheep.isAlive()) continue;
            }

            if (location.distance(teamSheep.getTeam().getSheepSpawn()) > 1) {
                continue;
            }

            sheepItem.getFunction().accept(teamSheep);
            item.setAmount(item.getAmount() - 1);
            event.setCancelled(true);
        }
    }
}
