package de.lecuutex.boatbattle.utils;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.PlayerStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.teams.Team;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamsEnum;
import de.lecuutex.boatbattle.minigame.lobby.Kit;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.enums.config.PlaceholderEnum;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A class created by yi.dnl - 20.04.2022 / 00:43
 */

@Getter
public class GameHandler {

    private HashMap<String, String> playerInCombat = new HashMap<>();

    private HashMap<Integer, String> placedTnt = new HashMap<>();

    private final Scoreboard scoreboard;

    @Setter
    private boolean test = false;

    @Setter
    private GameStateEnum gameState = GameStateEnum.LOBBY;

    public GameHandler() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(BoatBattle.getInstance(), () -> {
            if (gameState != GameStateEnum.INGAME) return;
            for (Team team : BoatBattle.getInstance().getTeamHandler().getTeams().values()) {
                team.getTeamSheep().removeHearts((Integer) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.SHEEP_MINUS_HEARTS_PER_MINUTE));
            }
        }, (int) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.SHEEP_START_DIEING_AFTER_X_SECONDS) * 20, 20 * 60);
    }

    private final HashMap<String, MinigamePlayer> players = new HashMap<>();

    public void addPlayer(Player player) {
        MinigamePlayer minigamePlayer = new MinigamePlayer(player.getUniqueId().toString());
        players.put(player.getUniqueId().toString(), minigamePlayer);
        scoreboard.getTeam(TeamsEnum.RANDOM.toString()).addPlayer(player);

        if (gameState == GameStateEnum.INGAME) {
            setSpectator(player);
        }
    }

    public void setSpectator(Player player) {
        MinigamePlayer minigamePlayer = getPlayer(player);

        if (minigamePlayer.getTeamsEnum() != TeamsEnum.RANDOM) {
            scoreboard.getTeam(minigamePlayer.getTeamsEnum().toString()).removePlayer(player);
        }

        minigamePlayer.setPlayerStateEnum(PlayerStateEnum.SPECTATOR);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.hidePlayer(player);
        }

        scoreboard.getTeam(TeamsEnum.RANDOM.toString()).addPlayer(player);

        Location center = (Location) ConfigManager.get(ConfigEnum.LOCATIONS, PlaceholderEnum.MAP_CENTER_SPAWNPOINT);

        Bukkit.getScheduler().scheduleSyncDelayedTask(BoatBattle.getInstance(), () -> {
            player.teleport(center);
            player.setAllowFlight(true);
            player.setFlying(true);
        }, 1);
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId().toString());
    }

    public MinigamePlayer getPlayer(Player player) {
        return players.get(player.getUniqueId().toString());
    }

    public MinigamePlayer getPlayer(String uuid) {
        return players.get(uuid);
    }

    public TeamsEnum getPlayerTeam(Player player) {
        if (players.containsKey(player.getUniqueId().toString())) {
            return getPlayer(player).getTeamsEnum();
        }

        return TeamsEnum.RANDOM;
    }

    public void setPlayerTeam(Player player, TeamsEnum team) {
        getPlayer(player).setOrder(getPlayersInTeam(team).size());
        getPlayer(player).setTeamsEnum(team);
    }

    public void setKit(Player player, Kit kit) {
        getPlayer(player).setKit(kit);
    }

    public Kit getKit(Player player) {
        for (Kit kit : ConfigManager.getKits()) {
            if (getPlayer(player).getKit() == null) continue;
            if (!kit.getName().equals(getPlayer(player).getKit().getName())) continue;
            return kit;
        }

        return ConfigManager.getKits().get(0);
    }

    public List<MinigamePlayer> getPlayersInTeam(TeamsEnum teamsEnum) {
        return getPlayers().values().stream().filter(t -> t.getTeamsEnum() == teamsEnum && Bukkit.getPlayer(UUID.fromString(t.getUuid())) != null).collect(Collectors.toList());
    }

    public void createTeams() {
        for (TeamsEnum team : TeamsEnum.values()) {
            if (scoreboard.getTeam(team.toString()) != null) {
                continue;
            }

            org.bukkit.scoreboard.Team t = scoreboard.registerNewTeam(team.toString());
            t.color(team.getChatColor());
        }
    }
}
