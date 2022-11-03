package de.lecuutex.boatbattle.utils.inventory.gui;

import de.lecuutex.boatbattle.BoatBattle;
import de.lecuutex.boatbattle.minigame.ingame.GameStateEnum;
import de.lecuutex.boatbattle.minigame.lobby.Kit;
import de.lecuutex.boatbattle.minigame.lobby.adminkit.AdminKit;
import de.lecuutex.boatbattle.utils.Utils;
import de.lecuutex.boatbattle.utils.configs.ConfigManager;
import de.lecuutex.boatbattle.utils.enums.PermissionsEnum;
import de.lecuutex.boatbattle.utils.enums.config.SpecialItemEnum;
import de.lecuutex.boatbattle.utils.inventory.Itembuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * A class created by yi.dnl - 19.04.2022 / 22:14
 */

public class KitGUI extends GUI {

    public KitGUI() {
        super("§cKit Auswahl", 3 * 9);
    }

    @Getter
    private final AdminKit adminKit = new AdminKit();

    @Override
    protected void createInventory(Player player) {

        for (Kit kit : ConfigManager.getKits()) {
            ItemStack item = new Itembuilder(kit.getMaterial(), 1)
                    .setDisplayName(kit.getName())
                    .build();
            setItem(kit.getSlot(), item);
        }

        if (player.hasPermission(PermissionsEnum.ADMIN.getPermission()) && BoatBattle.getInstance().getGameHandler().isTest()) {
            setItem(getInventory().getSize() - 1, adminKit.getItemInGUI());
        }
    }

    @Override
    protected void clickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        for (Kit kit : ConfigManager.getKits()) {
            if (slot != kit.getSlot()) continue;

            if (getGameHandler().getKit(player) != null && getGameHandler().getKit(player).getName().equals(kit.getName())) {
                Utils.sendMessage(player, "Du hast dieses Kit bereits ausgewählt");
                return;
            }

            getGameHandler().setKit(player, kit);
            Utils.sendMessage(player, "Du hast das Kit " + kit.getName() + " §7ausgewählt");
            player.closeInventory();

            if (getGameHandler().isTest() && getGameHandler().getGameState() == GameStateEnum.INGAME) {

            }
        }

        if (getGameHandler().isTest() && getGameHandler().getGameState() == GameStateEnum.INGAME && event.getCurrentItem().getType() == adminKit.getItemInGUI().getType()) {
            player.getInventory().clear();
            for (ItemStack item : adminKit.getItems()) {
                player.getInventory().addItem(item);
            }
        }
    }
}
