package fr.yvernal.yvernalkingdom.inventories;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.items.ManagePlayerItems;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.inventories.template.InventoryCreator;
import fr.yvernal.yvernalkingdom.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ManagePlayerInventory extends InventoryCreator {
    private final OfflinePlayer targetPlayer;

    public ManagePlayerInventory(PlayerAccount playerAccount, OfflinePlayer targetPlayer) {
        super(Main.getInstance().getConfigManager().getItemsManager().getManageGuildItems().getInventoryName()
                .replace("%player%", targetPlayer.getName()), 27);
        this.targetPlayer = targetPlayer;

        final ManagePlayerItems manageGuildItems = Main.getInstance().getConfigManager().getItemsManager().getManageGuildItems();

        final ItemStack isOnline = new ItemBuilder(Material.STAINED_GLASS_PANE, targetPlayer.isOnline() ? (byte) 13 : (byte) 14)
                .name(targetPlayer.isOnline() ? "§aConnecté" : "§cDéconnecté")
                .build();

        setItem(3, isOnline);

        setItem(4, new ItemBuilder(Material.SKULL_ITEM, (byte) 3)
                .name(targetPlayer.getName())
                .lore(playerAccount.getGuildRank().getColorizedName())
                .owner(targetPlayer)
                .build());

        setItem(5, isOnline);

        setItem(20, new ItemBuilder(Material.WOOL, (byte) 5)
                .name(manageGuildItems.getFirstGlassName()
                        .replace("%player%", targetPlayer.getName()))
                .build());

        setItem(22, new ItemBuilder(Material.WOOL, (byte) 1)
                .name(manageGuildItems.getSecondGlassName()
                        .replace("%player%", targetPlayer.getName()))
                .build());

        setItem(24, new ItemBuilder(Material.WOOL, (byte) 14)
                .name(manageGuildItems.getThirdGlassName()
                        .replace("%player%", targetPlayer.getName()))
                .build());

        setGlassToEmptySlots();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final ItemStack currentItem = event.getCurrentItem();

        if (currentItem.getType() == Material.WOOL) {
            if (currentItem.getDurability() == 5) {
                player.performCommand("g promote " + targetPlayer.getName());
            } else if (currentItem.getDurability() == 1) {
                player.performCommand("g demote " + targetPlayer.getName());
            } else if (currentItem.getDurability() == 14) {
                player.performCommand("g kick " + targetPlayer.getName());
            }

            player.closeInventory();
        }
    }
}
