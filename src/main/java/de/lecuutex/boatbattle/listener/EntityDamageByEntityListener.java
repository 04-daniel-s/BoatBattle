package de.lecuutex.boatbattle.listener;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.MinigamePlayer;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.PlayerStateEnum;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamBlue;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamSheep;
import de.lecuutex.boatbattle.minigame.ingame.teams.TeamsEnum;
import de.lecuutex.boatbattle.utils.GameHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A class created by yi.dnl - 20.04.2022 / 22:53
 */

public class EntityDamageByEntityListener implements Listener {

    private final GameHandler gameHandler = BoatBattle.getInstance().getGameHandler();

    @EventHandler
    public void onEvent(EntityDamageByEntityEvent event) {
        if (BoatBattle.getInstance().getGameHandler().getGameState() != GameStateEnum.INGAME) event.setCancelled(true);

        Entity entity = event.getEntity();

        if (event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            Player shooter = (Player) arrow.getShooter();
            if (!canAttackSheep(shooter, entity, event.getDamage())) event.setCancelled(true);
            if (canAttackPlayer(shooter, entity)) {
                event.setCancelled(true);
            } else if (event.getEntity() instanceof Player) {
                gameHandler.getPlayerInCombat().put(entity.getUniqueId().toString(), shooter.getUniqueId().toString());
            }

        } else if (event.getDamager().getType() == EntityType.PRIMED_TNT) {
            TNTPrimed tntPrimed = (TNTPrimed) event.getDamager();

            for (Map.Entry<Integer, String> entry : gameHandler.getPlacedTnt().entrySet()) {
                int id = entry.getKey();
                Player p = Bukkit.getPlayer(UUID.fromString(entry.getValue()));

                if (tntPrimed.getEntityId() != id) continue;
                if (!canAttackSheep(p, entity, event.getDamage())) event.setCancelled(true);
                if (canAttackPlayer(p, entity)) event.setCancelled(true);
            }

        } else if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            List<Material> types = Arrays.asList(Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE);
            types.stream().filter(t -> t == player.getItemInHand().getType()).forEach(t -> event.setDamage(event.getDamage() * 0.5));

            if (BoatBattle.getInstance().getGameHandler().getPlayer(player).getPlayerStateEnum() == PlayerStateEnum.SPECTATOR)
                event.setCancelled(true);

            if (canAttackPlayer(player, entity)) event.setCancelled(true);
            if (!canAttackSheep(player, entity, event.getDamage())) event.setCancelled(true);

            if (event.getEntity() instanceof Player) {
                gameHandler.getPlayerInCombat().put(event.getEntity().getUniqueId().toString(), player.getUniqueId().toString());
            }
        } else if (event.getDamager() instanceof Fireball) {
            Fireball fireball = (Fireball) event.getDamager();
            if (!(fireball.getShooter() instanceof Player)) return;

            Player shooter = (Player) fireball.getShooter();

            if (canAttackPlayer(shooter, entity)) event.setCancelled(true);
            if (!canAttackSheep(shooter, entity, event.getDamage())) event.setCancelled(true);

            if (event.getEntity() instanceof Player) {
                gameHandler.getPlayerInCombat().put(event.getEntity().getUniqueId().toString(), shooter.getUniqueId().toString());
            }
        }
    }

    private boolean canAttackPlayer(Player player, Entity target) {
        if (!(target instanceof Player)) return true;
        return (gameHandler.getPlayerTeam(player) == gameHandler.getPlayerTeam((Player) target));
    }

    public boolean canAttackSheep(Player player, Entity entity, double damage) {
        if (!(entity instanceof Sheep)) return true;
        TeamSheep teamSheep = BoatBattle.getInstance().getTeamHandler().getTeam(entity.getName().equals(TeamsEnum.BLUE.getName()) ? TeamsEnum.BLUE : TeamsEnum.RED).getTeamSheep();

        if (gameHandler.getPlayerTeam(player) == teamSheep.getTeam().getTeamsEnum()) {
            return false;
        }

        teamSheep.removeHearts((int) Math.round(damage));
        return true;
    }
}
