package de.lecuutex.boatbattle.utils.enums.config;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.PlayerStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamSheep;
import de.lecuutex.boatbattle.utils.GameHandler;
import de.lecuutex.boatbattle.utils.Utils;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * A class created by yi.dnl - 08.05.2022 / 18:46
 */

@Getter
public enum SheepItems {

    SHEEP_HEAL_ITEM(PlaceholderEnum.HEAL_ITEM_SPAWNPOINT, PlaceholderEnum.HEAL_ITEM_SPAWNTIME_IN_SECONDS, "§cMob Heilung", Material.APPLE, "§7Heilt den Mob deines Teams um ein paar Herzen", sheep -> {
        sheep.addHearts((Integer) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.HEAL_ITEM_HEALING_IN_HEARTS) * 2);

        for (MinigamePlayer minigamePlayer : BoatBattle.getInstance().getGameHandler().getPlayersInTeam(sheep.getTeam().getTeamsEnum())) {
            if (Bukkit.getPlayer(UUID.fromString(minigamePlayer.getUuid())) == null) continue;
            String team = sheep.getTeam().getTeamsEnum().getName();
            Utils.sendMessage(Bukkit.getPlayer(UUID.fromString(minigamePlayer.getUuid())), "Das Schaf wurde um §e" + (Integer) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.HEAL_ITEM_HEALING_IN_HEARTS) * 2 + " Herzen §7geheilt!");
        }
    }),

    SHEEP_REVIVE_ITEM(PlaceholderEnum.REVIVE_ITEM_SPAWNPOINT, PlaceholderEnum.REVIVE_ITEM_SPAWNTIME_IN_SECONDS, "§cMob Wiederbelebung", Material.NETHER_STAR, "§7Nur Anwendbar, wenn der Mob deines Teams gestorben ist.\nBelebt den Mob deines Teams wieder - Tote Teammates können respawnen", sheep -> {
        int revivePercentageNumber = (int) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.REVIVE_ITEM_HEARTS_IN_PERCENTAGE);
        float revivePercentage = (float) (revivePercentageNumber / 100.0);
        sheep.spawnSheep(Math.round(revivePercentage * (int) ConfigManager.get(ConfigEnum.CONFIG, PlaceholderEnum.SHEEP_HEARTS)));

        for (MinigamePlayer minigamePlayer : BoatBattle.getInstance().getGameHandler().getPlayersInTeam(sheep.getTeam().getTeamsEnum())) {
            if (minigamePlayer.getPlayerStateEnum() != PlayerStateEnum.SPECTATOR) continue;
            minigamePlayer.spawn();
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            String team = sheep.getTeam().getTeamsEnum().getName();
            Utils.sendMessage(onlinePlayer, "Das Schaf von " + team + " §7wurde wiederbelebt!");
        }
    });

    private final PlaceholderEnum path;

    private final PlaceholderEnum spawnTime;

    private final String name;

    private final Material material;

    private final String lore;

    private final Consumer<TeamSheep> function;

    SheepItems(PlaceholderEnum path, PlaceholderEnum spawnTime, String name, Material material, String lore, Consumer<TeamSheep> function) {
        this.path = path;
        this.spawnTime = spawnTime;
        this.name = name;
        this.material = material;
        this.lore = lore;
        this.function = function;
    }
}
