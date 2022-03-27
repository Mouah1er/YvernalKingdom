package fr.yvernal.yvernalkingdom.inventories;

import com.google.common.base.Splitter;
import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.items.ShowGuildItems;
import fr.yvernal.yvernalkingdom.config.messages.MessagesManager;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccountManager;
import fr.yvernal.yvernalkingdom.inventories.template.InventoryCreator;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdoms;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.utils.ItemBuilder;
import fr.yvernal.yvernalkingdom.utils.ListUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ShowGuildInventory extends InventoryCreator {
    private final ShowGuildItems showGuildItems;
    private final Guild guild;

    public ShowGuildInventory(Player player, Guild guild) {
        super(Main.getInstance().getConfigManager().getItemsManager().getShowGuildItems().getInventoryName()
                .replace("%guilde%", guild.getGuildData().getName()), 54);

        final PlayerAccountManager playerAccountManager = Main.getInstance().getDataManager().getPlayerAccountManager();
        this.showGuildItems = Main.getInstance().getConfigManager().getItemsManager().getShowGuildItems();
        this.guild = guild;

        setItem(3, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 2)
                .name("§c")
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .build());

        setItem(4, new ItemBuilder(Material.ENCHANTMENT_TABLE)
                .name(showGuildItems.getEnchantmentTableName()
                        .replace("%guilde%", guild.getGuildData().getName()))
                .lore(ListUtils.modifyList(showGuildItems.getEnchantmentTableLore(), list -> list.replaceAll(s -> {
                    if (s.contains("%kingdom%")) {
                        return s.replace("%kingdom%", Kingdoms.valueOf(guild.getGuildData().getKingdomName().toUpperCase(Locale.ROOT))
                                .getKingdom()
                                .getKingdomProperties().getName());
                    }

                    return s;
                })))
                .build());

        setItem(5, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 2)
                .name("§c")
                .flags(ItemFlag.HIDE_ATTRIBUTES)
                .build());

        setItem(9, new ItemBuilder(Material.BOOK)
                .name(showGuildItems.getFirstBookName())
                .lore(ListUtils.modifyList(showGuildItems.getFirstBookLore(), list -> list.replaceAll(s -> {
                    if (s.contains("%power%")) {
                        return s.replace("%power%", String.valueOf(guild.getGuildData().getPower()));
                    }

                    if (s.contains("%max_power%")) {
                        return s.replace("%max_power%", String.valueOf(guild.getGuildData().getMembersUniqueId().size() * 10));
                    }

                    if (s.contains("%claims%")) {
                        return s.replace("%claims%", String.valueOf(Main.getInstance().getDataManager()
                                .getClaimManager().getClaims(guild).size()));
                    }

                    return s;
                })))
                .build());

        setItem(10, new ItemBuilder(Material.BOOK)
                .name(showGuildItems.getSecondBookName())
                .lore(Splitter.fixedLength(40).splitToList(guild.getGuildData().getDescription())
                        .stream()
                        .map(s -> {
                            if (s.isEmpty()) return "§cAucune description !";

                            return "§b" + s;
                        })
                        .collect(Collectors.toList()))
                .build());

        setItem(16, new ItemBuilder(Material.BOOK_AND_QUILL)
                .name(showGuildItems.getThirdBookName())
                .lore(showGuildItems.getThirdBookLore())
                .build());

        setItem(17, new ItemBuilder(Material.BOOK_AND_QUILL)
                .name(showGuildItems.getFourthBookName())
                .lore(showGuildItems.getFourthBookLore())
                .build());

        final OfflinePlayer ownerOfflinePlayer = Bukkit.getOfflinePlayer(guild.getGuildData().getOwnerUniqueId());

        final PlayerAccount ownerAccount = playerAccountManager.getPlayerAccount(ownerOfflinePlayer.getUniqueId());

        final ItemStack isOnline = new ItemBuilder(Material.STAINED_GLASS_PANE, ownerOfflinePlayer.isOnline() ? (byte) 13 : (byte) 14)
                .name(ownerOfflinePlayer.isOnline() ? "§aConnecté" : "§cDéconnecté")
                .build();

        setItem(21, isOnline);
        setItem(23, isOnline);


        setItem(22, new ItemBuilder(Material.SKULL_ITEM, (byte) 3)
                .name(showGuildItems.getOwnerItemName()
                        .replace("%player%", ownerOfflinePlayer.getName()))
                .owner(ownerOfflinePlayer)
                .lore(ListUtils.modifyList(showGuildItems.getOwnerItemLore(), list -> list.replaceAll(s -> {
                    if (s.contains("%ratio%")) {
                        final DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        return s.replace("%ratio%",
                                ownerAccount.getDeaths() != 0 ? decimalFormat.format((double) ownerAccount.getKills() /
                                        (double) ownerAccount.getDeaths()) : String.valueOf(ownerAccount.getKills()));
                    }

                    if (s.contains("%guilde_rank%")) {
                        return s.replace("%guilde_rank%", ownerAccount.getGuildRank().getColorizedName());
                    }

                    return s;
                })))
                .build());

        for (int i = 27; i < 36; i++) {
            setItem(i, new ItemBuilder(Material.SKULL_ITEM, (byte) 3)
                    .name("§cPersonne")
                    .build());
        }

        for (int i = 36; i < 45; i++) {
            setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 1)
                    .name("§aPlace libre !")
                    .build());
        }

        for (int i = 45; i < 54; i++) {
            setItem(i, new ItemBuilder(Material.BARRIER)
                    .name("§aPlace libre !")
                    .build());
        }

        final List<UUID> membersWithoutOwner = guild.getGuildData().getMembersUniqueId()
                .stream()
                .filter(uuid -> !uuid.equals(guild.getGuildData().getOwnerUniqueId()))
                .collect(Collectors.toList());

        int slot = 26;
        for (UUID uuid : membersWithoutOwner) {
            slot++;

            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            final boolean isConnected = offlinePlayer.isOnline();
            final PlayerAccount playerAccount = playerAccountManager.getPlayerAccount(offlinePlayer.getUniqueId());

            setItem(slot, new ItemBuilder(Material.SKULL_ITEM, (byte) 3)
                    .name(showGuildItems.getPlayerItemName()
                            .replace("%player%", offlinePlayer.getName()))
                    .owner(offlinePlayer)
                    .lore(guild.getGuildData().getOwnerUniqueId().equals(player.getUniqueId()) ? "§cGérer le joueur" : "")
                    .build());

            setItem(slot + 9, new ItemBuilder(Material.STAINED_GLASS_PANE, isConnected ? (byte) 13 : (byte) 14)
                    .name(isConnected ? "§aConnecté" : "§cDéconnecté")
                    .build());

            setItem(slot + 18, new ItemBuilder(Material.PAPER)
                    .name(showGuildItems.getPaperItemName())
                    .lore(ListUtils.modifyList(showGuildItems.getPaperItemLore(), list -> list.replaceAll(s -> {
                        if (s.contains("%ratio%")) {
                            final DecimalFormat decimalFormat = new DecimalFormat("#.##");
                            return s.replace("%ratio%",
                                    playerAccount.getDeaths() != 0 ? decimalFormat.format((double) playerAccount.getKills() /
                                            (double) ownerAccount.getDeaths()) : String.valueOf(playerAccount.getKills()));
                        }

                        if (s.contains("%guilde_rank%")) {
                            return s.replace("%guilde_rank%", playerAccount.getGuildRank().getColorizedName());
                        }

                        return s;
                    })))
                    .build());
        }

        setGlassToEmptySlots();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final ItemStack currentItem = event.getCurrentItem();
        final MessagesManager messagesManager = Main.getInstance().getConfigManager().getMessagesManager();

        final Guild playerGuild = Main.getInstance().getDataManager().getGuildDataManager().getGuildByPlayer(player.getUniqueId());

        if (currentItem.getType() == Material.BOOK_AND_QUILL) {
            if (playerGuild != null) {
                if (playerGuild.getGuildData().getGuildUniqueId().equals(guild.getGuildData().getGuildUniqueId())) {
                    if (playerGuild.getGuildData().getOwnerUniqueId().equals(player.getUniqueId())) {
                        if (currentItem.getItemMeta().getDisplayName().equals(showGuildItems.getFourthBookName())) {
                            player.closeInventory();
                            player.sendMessage(messagesManager.getString("ask-player-change-desc"));

                            final CompletableFuture<String> completableFuture = new CompletableFuture<>();

                            Main.getInstance().getMessageWaiter().put(player.getUniqueId(), completableFuture);

                            completableFuture.thenAcceptAsync(s -> {
                                player.performCommand("g desc " + s);
                                Main.getInstance().getMessageWaiter().remove(player.getUniqueId(), completableFuture);
                            });
                        } else {
                            player.closeInventory();
                            player.sendMessage(messagesManager.getString("ask-player-change-name"));

                            final CompletableFuture<String> completableFuture = new CompletableFuture<>();

                            Main.getInstance().getMessageWaiter().put(player.getUniqueId(), completableFuture);

                            completableFuture.thenAcceptAsync(s -> {
                                player.performCommand("g rename " + s);
                                Main.getInstance().getMessageWaiter().remove(player.getUniqueId(), completableFuture);
                            });
                        }
                    } else {
                        player.closeInventory();
                        player.sendMessage(messagesManager.getString("player-guild-permission-error"));
                    }
                } else {
                    player.closeInventory();
                    player.sendMessage(messagesManager.getString("player-not-in-certain-guild")
                            .replace("%guilde%", guild.getGuildData().getName()));
                }
            } else {
                player.closeInventory();
                player.sendMessage(messagesManager.getString("player-not-in-guild"));
            }
        } else if (currentItem.getType() == Material.SKULL_ITEM) {
            if (event.getSlot() != 22) {
                if (playerGuild != null) {
                    if (playerGuild.getGuildData().getGuildUniqueId().equals(guild.getGuildData().getGuildUniqueId())) {
                        if (playerGuild.getGuildData().getOwnerUniqueId().equals(player.getUniqueId())) {
                            player.performCommand("g manage " + currentItem.getItemMeta().getDisplayName().substring(2));
                        }
                    }
                }
            }
        }
    }
}
