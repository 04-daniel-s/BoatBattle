package de.lecuutex.boatbattle.minigame;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.ingame.PlayerStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamsEnum;
import de.lecuutex.boatbattle.minigame.lobby.Kit;
import de.lecuutex.boatbattle.minigame.lobby.KitItem;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.config.SpecialItemEnum;
import de.lecuutex.boatbattle.utils.inventory.Itembuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * A class created by yi.dnl - 20.04.2022 / 00:45
 */

@Getter
@Setter
public class MinigamePlayer {

    private String uuid;

    private PlayerStateEnum playerStateEnum = PlayerStateEnum.SPECTATOR;

    private TeamsEnum teamsEnum = TeamsEnum.RANDOM;

    private Kit kit = ConfigManager.getKits().get(0);

    private Integer order = 0;

    public MinigamePlayer(String uuid) {
        this.uuid = uuid;
    }

    public void teleport() {
        Bukkit.getPlayer(UUID.fromString(uuid)).teleport(BoatBattle.getInstance().getTeamHandler().get(teamsEnum).getTeamSpawn());
    }

    public void spawn() {
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        setPlayerStateEnum(PlayerStateEnum.INGAME);
        teleport();
        giveKitItems();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.showPlayer(BoatBattle.getInstance(), player);
        }

        BoatBattle.getInstance().getGameHandler().getScoreboard().getTeam(getTeamsEnum().toString()).addPlayer(player);
    }

    public void giveKitItems() {
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        player.getInventory().clear();

        for (KitItem kitItem : kit.getItems()) {
            ItemStack item = new Itembuilder(kitItem.getMaterial(), kitItem.getAmount()).setDisplayName(kitItem.getName()).setUnbreakable(kitItem.isUnbreakable()).build();

            if (kitItem.getEnchantment() != null && kitItem.isAllowEnchantment()) {
                item = new Itembuilder(item).addEnchantment(kitItem.getEnchantment(), kitItem.getEnchantmentPower()).build();
            }

            player.getInventory().setItem(kitItem.getSlot(), item);
        }
    }
}
