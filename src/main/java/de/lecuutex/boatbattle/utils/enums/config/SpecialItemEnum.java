package de.lecuutex.boatbattle.utils.enums.config;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.utils.GameHandler;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A class created by yi.dnl - 19.04.2022 / 21:22
 */

@Getter
public enum SpecialItemEnum {
    NONE("NONE", null, null),
    BOOST("§cBoost", Material.FIREWORK_ROCKET, event -> {
        Player player = event.getPlayer();
        player.setVelocity(player.getLocation().getDirection().setY(0.6D).multiply(2D));
        player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1, 1);
    }),
    FIREBLAST("§cFireblast", Material.BLAZE_ROD, event -> {
        Player player = event.getPlayer();
        Fireball f = player.launchProjectile(Fireball.class);
        f.setIsIncendiary(true);
        f.setYield(3);
        player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1, 1);
    }),
    FIREBOMB("§cFirebomb", Material.TNT, event -> {
        Player player = event.getPlayer();
        TNTPrimed tntPrimed = ((TNTPrimed) player.getWorld().spawnEntity(event.getClickedBlock().getLocation().add(0, 1, 0), EntityType.PRIMED_TNT));
        tntPrimed.setFuseTicks(40);

        BoatBattle.getInstance().getGameHandler().getPlacedTnt().put(tntPrimed.getEntityId(), player.getUniqueId().toString());

        List<Player> nearbyPlayers = player.getNearbyEntities(10, 10, 10).stream().filter(v -> v instanceof Player).map(v -> (Player) v).collect(Collectors.toList());
        nearbyPlayers.add(player);

        for (Player players : nearbyPlayers) {
            players.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);

            Bukkit.getScheduler().scheduleSyncDelayedTask(BoatBattle.getInstance(), () -> {
                players.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
            }, 20);

            Bukkit.getScheduler().scheduleSyncDelayedTask(BoatBattle.getInstance(), () -> {
                players.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
                for (int i = 0; i < 10; i++) {
                    player.getWorld().getHighestBlockAt(event.getClickedBlock().getLocation().clone().add(new Vector(new Random().nextInt(5), 1, new Random().nextInt(5)))).setType(Material.FIRE);
                }
            }, 20 * 2);
        }
    }),
    WORKBENCH("§cWerkbank", Material.PAPER, event -> {
        event.getPlayer().openInventory(Bukkit.createInventory(null, InventoryType.WORKBENCH, " "));
    });

    private final String name;

    private final Material material;

    private final Consumer<PlayerInteractEvent> function;

    SpecialItemEnum(String name, Material material, Consumer<PlayerInteractEvent> function) {
        this.name = name;
        this.material = material;
        this.function = function;
    }
}
