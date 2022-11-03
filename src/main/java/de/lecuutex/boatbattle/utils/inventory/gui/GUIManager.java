package de.lecuutex.boatbattle.utils.inventory.gui;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

/**
 * A class created by yi.dnl - 19.04.2022 / 22:15
 */

@Getter
public class GUIManager implements Listener {

    private final ArrayList<GUI> inventories = new ArrayList<>();

    public GUIManager() {
        inventories.add(kitGUI);
        inventories.add(teamsGUI);
    }

    private final KitGUI kitGUI = new KitGUI();

    private final TeamsGUI teamsGUI = new TeamsGUI();

    @EventHandler
    protected void onEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();

        for (GUI inventory : inventories) {
            if (!event.getView().getTitle().equals(inventory.getTitle())) continue;
            inventory.clickEvent(event);
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            event.setCancelled(true);
        }
    }
}
