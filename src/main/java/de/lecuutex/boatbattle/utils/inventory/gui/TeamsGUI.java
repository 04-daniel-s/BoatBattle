package de.lecuutex.boatbattle.utils.inventory.gui;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.ingame.teams.Team;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamHandler;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamsEnum;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import de.lecuutex.boatbattle.utils.inventory.Itembuilder;
import de.lecuutex.boatbattle.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * A class created by yi.dnl - 19.04.2022 / 23:15
 */

public class TeamsGUI extends GUI {

    public TeamsGUI() {
        super("§cTeam Auswahl", 3 * 9);
    }

    @Override
    protected void createInventory(Player player) {
        for (TeamsEnum team : TeamsEnum.values()) {
            setItem(team.getInventorySlot(), new Itembuilder(team.getTeamSelectItem()).setDisplayName(team.getName()).build());
        }
    }

    @Override
    protected void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        Material material = event.getCurrentItem().getType();

        for (TeamsEnum team : TeamsEnum.values()) {
            if (material != team.getTeamSelectItem()) continue;
            if (getGameHandler().getPlayerTeam(player) == team) {
                Utils.sendMessage(player, "Du bist bereits in diesem Team");
                return;
            }

            if (getGameHandler().getPlayersInTeam(team).size() >= (Integer) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.TEAM_SIZE)) {
                Utils.sendMessage(player, "§cDas Team ist voll!");
                return;
            }

            getGameHandler().setPlayerTeam(player, team);
            Utils.sendMessage(player, "Du bist " + team.getName() + " §7beigetreten");
            player.closeInventory();
            player.getInventory().setItem(4, new Itembuilder(team.getTeamSelectItem()).addGlow().setDisplayName("§cTeam Auswahl").build());
        }
    }
}
