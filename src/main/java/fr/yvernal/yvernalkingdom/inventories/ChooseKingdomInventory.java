package fr.yvernal.yvernalkingdom.inventories;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.items.ChooseKingdomItems;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.inventories.template.InventoryCreator;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdoms;
import fr.yvernal.yvernalkingdom.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChooseKingdomInventory extends InventoryCreator {

    public ChooseKingdomInventory() {
        super("§bChoix du Royaume", 54);

        final ChooseKingdomItems chooseKingdomItems = Main.getInstance().getConfigManager().getItemsManager().getChooseKingdomItems();

        setItem(12, new ItemBuilder(Material.BOOK)
                .name(chooseKingdomItems.getBookName())
                .lore(chooseKingdomItems.getBookLore())
                .build());

        setItem(13, new ItemBuilder(Material.COMPASS)
                .name(chooseKingdomItems.getCompassName())
                .lore(chooseKingdomItems.getCompassLore())
                .build());

        setItem(14, new ItemBuilder(Material.PAPER)
                .name(chooseKingdomItems.getPaperName())
                .lore(chooseKingdomItems.getPaperLore())
                .build());

        setItem(28, new ItemBuilder(Material.EMPTY_MAP)
                .name(chooseKingdomItems.getEmptyMap1Name() + " §o§7#1")
                .lore(chooseKingdomItems.getEmptyMap1Lore())
                .build());

        setItem(30, new ItemBuilder(Material.EMPTY_MAP)
                .name(chooseKingdomItems.getEmptyMap2Name() + " §o§7#2")
                .lore(chooseKingdomItems.getEmptyMap2Lore())
                .build());

        setItem(32, new ItemBuilder(Material.EMPTY_MAP)
                .name(chooseKingdomItems.getEmptyMap3Name() + " §o§7#3")
                .lore(chooseKingdomItems.getEmptyMap3Lore())
                .build());

        setItem(34, new ItemBuilder(Material.EMPTY_MAP)
                .name(chooseKingdomItems.getEmptyMap4Name() + " §o§7#4")
                .lore(chooseKingdomItems.getEmptyMap4Lore())
                .build());

        final ItemStack waitingLineTrueItemStack = new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 14)
                .name(chooseKingdomItems.getWaitingLineTrueName())
                .lore(chooseKingdomItems.getWaitingLineTrueLore())
                .build();

        final ItemStack waitingLineFalseItemStack = new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 5)
                .name(chooseKingdomItems.getWaitingLineFalseName())
                .lore(chooseKingdomItems.getWaitingLineFalseLore())
                .build();

        final int kingdomMaxPlayers = Main.getInstance().getConfigManager().getGameConfigManager().get("kingdom-max-players", int.class);

        if (Kingdoms.KINGDOM_1.getKingdom().getKingdomData().getPlayersIn().size() >= kingdomMaxPlayers) {
            setItem(37, waitingLineTrueItemStack);
        } else {
            setItem(37, waitingLineFalseItemStack);
        }

        if (Kingdoms.KINGDOM_2.getKingdom().getKingdomData().getPlayersIn().size() >= kingdomMaxPlayers) {
            setItem(39, waitingLineTrueItemStack);
        } else {
            setItem(39, waitingLineFalseItemStack);
        }

        if (Kingdoms.KINGDOM_3.getKingdom().getKingdomData().getPlayersIn().size() >= kingdomMaxPlayers) {
            setItem(41, waitingLineTrueItemStack);
        } else {
            setItem(41, waitingLineFalseItemStack);
        }

        if (Kingdoms.KINGDOM_4.getKingdom().getKingdomData().getPlayersIn().size() >= kingdomMaxPlayers) {
            setItem(43, waitingLineTrueItemStack);
        } else {
            setItem(43, waitingLineFalseItemStack);
        }

        setGlassToEmptySlots();
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        final Player player = (Player) event.getPlayer();
        final PlayerAccount playerAccount = Main.getInstance().getDataManager().getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

        if (playerAccount == null || (playerAccount.getKingdom() == null &&
                playerAccount.getWaitingKingdom() == null)) {
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> this.open(player), 1);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final Inventory inventory = event.getInventory();
        final ItemStack currentItem = event.getCurrentItem();

        if (currentItem.getType() == Material.EMPTY_MAP) {
            final String displayName = currentItem.getItemMeta().getDisplayName();
            final String kingdomName = "kingdom_" + displayName.substring(displayName.length() - 1);

            if (inventory.getItem(event.getSlot() + 9).getDurability() == 14) {
                Main.getInstance().getDataManager().getKingdomDataManager().makePlayerJoinWaitingList(player.getUniqueId(),
                        Kingdoms.getByNumber(kingdomName));
                player.sendMessage(Main.getInstance().getConfigManager().getMessagesManager().getString("join-kingdom-waiting-list-message")
                        .replace("%kingdom%", displayName));
            } else if (inventory.getItem(event.getSlot() + 9).getDurability() == 5) {
                Main.getInstance().getDataManager().getKingdomDataManager().makePlayerJoin(player.getUniqueId(),
                        Kingdoms.getByNumber(kingdomName));
                player.sendMessage(Main.getInstance().getConfigManager().getMessagesManager().getString("join-kingdom-message")
                        .replace("%kingdom%", displayName));
                Main.getInstance().getTeamManager().showPlayerNameTag(player);
            }

            player.closeInventory();
        }
    }
}
