package de.lecuutex.boatbattle.minigame.lobby.adminkit;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamsEnum;
import de.lecuutex.boatbattle.utils.Utils;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.config.ConfigEnum;
import de.lecuutex.boatbattle.utils.inventory.Itembuilder;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.Getter;
import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class created by yi.dnl - 21.04.2022 / 23:24
 */

@Getter
public class AdminKit implements Listener {

    private final HashMap<String, AdminItemsEnum> currentSpawnPoint = new HashMap<>();

    private final List<ItemStack> items = new ArrayList<>();

    private final ItemStack itemInGUI;

    public AdminKit() {
        for (AdminItemsEnum itemEnum : AdminItemsEnum.values()) {
            items.add(new Itembuilder(itemEnum.getMaterial()).setDisplayName(itemEnum.getItemName()).build());
        }

        itemInGUI = new Itembuilder(Material.BARRIER).setDisplayName("§cAdminKit").build();
        Bukkit.getPluginManager().registerEvents(this, BoatBattle.getInstance());
    }

    @EventHandler
    public void onChatEvent(AsyncChatEvent event) {
        Player player = event.getPlayer();
        TextComponent message = (TextComponent) event.message();
        if (!currentSpawnPoint.containsKey(player.getUniqueId().toString())) return;

        event.setCancelled(true);

        for (TeamsEnum teamsEnum : TeamsEnum.values()) {
            if (teamsEnum == TeamsEnum.RANDOM) continue;
            if (ChatColor.stripColor(teamsEnum.getName()).equalsIgnoreCase(message.content())) {
                Utils.sendMessage(player, "Der Spawnpunkt §a" + currentSpawnPoint.get(player.getUniqueId().toString()).getItemName().substring(8) + " §7für " + teamsEnum.getName() + " §7wurde gesetzt!");

                if (currentSpawnPoint.get(player.getUniqueId().toString()).getMaterial() == Material.NAME_TAG) {
                    ConfigManager.set(ConfigEnum.LOCATIONS, BoatBattle.getInstance().getTeamHandler().get(teamsEnum).getPath(), player.getLocation());
                } else if (currentSpawnPoint.get(player.getUniqueId().toString()).getMaterial() == Material.DIAMOND_HORSE_ARMOR) {
                    ConfigManager.set(ConfigEnum.LOCATIONS, BoatBattle.getInstance().getTeamHandler().get(teamsEnum).getPath().getName().replace("spawnpoint", "schaf-spawnpoint"), player.getLocation());
                }
            }
        }
        currentSpawnPoint.remove(player.getUniqueId().toString());
    }
}
