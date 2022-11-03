package de.lecuutex.boatbattle.minigame.ingame.scheduler;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.teams.Team;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamRed;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamsEnum;
import de.lecuutex.boatbattle.utils.Utils;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.MessageEnum;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import de.lecuutex.boatbattle.utils.enums.config.SheepItems;
import de.lecuutex.boatbattle.utils.inventory.Itembuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class created by yi.dnl - 21.04.2022 / 01:28
 */

public class LobbyScheduler extends GameScheduler {

    private final Scoreboard scoreboard;

    public LobbyScheduler(int time) {
        super(time);
        scoreboard = getGameHandler().getScoreboard();
    }

    @Override
    public void setGameState() {
        getGameHandler().setGameState(GameStateEnum.LOBBY);
    }

    @Override
    public void nextScheduler() {
        new IngameScheduler((Integer) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.INGAME_PLAYTIME));
    }

    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().size() < (int) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.LOBBY_MIN_PLAYER)) {
            resetTimer();
        }

        TextComponent actionbar = (Bukkit.getOnlinePlayers().size() >= (int) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.LOBBY_MIN_PLAYER)
                ? new TextComponent("§aSpielstart in: §e" + getTime() + "§a Sekunden")
                : new TextComponent(MessageEnum.NOT_ENOUGH_PLAYER.getMessage()));


        Bukkit.getOnlinePlayers().forEach(p -> p.sendActionBar(actionbar));

        sendMessageAtSeconds("§cDas Spiel startet in §e" + getTime() + "§c Sekunden!", 15, 10, 5, 3, 2, 1);
    }

    @Override
    public void end() {
        if (getGameHandler().isTest()) {
            Bukkit.getScheduler().cancelTask(getId());
            nextScheduler();
        }

        HashMap<String, MinigamePlayer> players = getGameHandler().getPlayers();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            Utils.sendMessage(onlinePlayer, MessageEnum.START);

            if (getGameHandler().getPlayerTeam(onlinePlayer) == TeamsEnum.RANDOM) {
                if (getTeamHandler().getPlayersInTeam(TeamsEnum.BLUE).size() > getTeamHandler().getPlayersInTeam(TeamsEnum.RED).size()) {
                    getGameHandler().setPlayerTeam(onlinePlayer, TeamsEnum.RED);
                } else {
                    getGameHandler().setPlayerTeam(onlinePlayer, TeamsEnum.BLUE);
                }
            }

            scoreboard.getTeam(getGameHandler().getPlayerTeam(onlinePlayer).toString()).addPlayer(onlinePlayer);
        }

        for (int i = 0; i < getTeamHandler().getTeams().values().size() - 1; i++) {
            List<Team> teams = getTeamHandler().getTeams().values().stream().toList();

            TeamsEnum team = teams.get(i).getTeamsEnum();
            TeamsEnum nextTeam = teams.get(i + 1).getTeamsEnum() == null ? teams.get(0).getTeamsEnum() : teams.get(i + 1).getTeamsEnum();

            List<MinigamePlayer> teamPlayers = getTeamHandler().getPlayersInTeam(team);
            List<MinigamePlayer> nextTeamPlayers = getTeamHandler().getPlayersInTeam(nextTeam);
            List<MinigamePlayer> largerTeam = (teamPlayers.size() > nextTeamPlayers.size() ? teamPlayers : nextTeamPlayers);

            TeamsEnum smallerTeamEnum = teamPlayers.size() > nextTeamPlayers.size() ? nextTeam : team;
            int sizeDifference = (Math.max(teamPlayers.size(), nextTeamPlayers.size())) - Math.min(teamPlayers.size(), nextTeamPlayers.size());

            Collections.sort(largerTeam, Comparator.comparing(MinigamePlayer::getOrder));
            Collections.reverse(largerTeam);

            largerTeam.subList(0, sizeDifference / 2).forEach(p -> {
                p.setTeamsEnum(smallerTeamEnum);
                Utils.sendMessage(Bukkit.getPlayer(UUID.fromString(p.getUuid())), "Aufgrund Teamungleichheiten wurdest du in " + smallerTeamEnum.getName() + " §7verschoben");
            });
        }

        for (MinigamePlayer minigamePlayer : players.values()) {
            Utils.sendMessage(Bukkit.getPlayer(UUID.fromString(minigamePlayer.getUuid())), "Du bist in " + minigamePlayer.getTeamsEnum().getName());
            minigamePlayer.spawn();
        }

        for (SheepItems sheepItem : SheepItems.values()) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(BoatBattle.getInstance(), () -> {
                Location location = (Location) ConfigManager.get(ConfigEnum.LOCATIONS, sheepItem.getPath());
                ItemStack item = new Itembuilder(sheepItem.getMaterial()).setDisplayName(sheepItem.getName()).setLore(sheepItem.getLore()).build();

                location.getWorld().dropItem(location, item);

                if (sheepItem == SheepItems.SHEEP_REVIVE_ITEM) {
                    Bukkit.getOnlinePlayers().forEach(p -> Utils.sendMessage(p, "Das Wiederbelebungs-Item ist gespawnt!"));
                }
            }, ((int) ConfigManager.get(ConfigEnum.CONFIG, sheepItem.getSpawnTime())) * 20L, ((int) ConfigManager.get(ConfigEnum.CONFIG, sheepItem.getSpawnTime())) * 20L);
        }
        getTeamHandler().getTeams().values().forEach(t -> t.getTeamSheep().spawnSheep());
    }
}
