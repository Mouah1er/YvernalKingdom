package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccountManager;
import fr.yvernal.yvernalkingdom.inventories.ChooseKingdomInventory;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import fr.yvernal.yvernalkingdom.tasks.PowerAdditionsBukkitRunnable;
import fr.yvernal.yvernalkingdom.utils.TagUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends YvernalListener<PlayerJoinEvent> {

    @Override
    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final PlayerAccountManager playerAccountManager = dataManager.getPlayerAccountManager();
        PlayerAccount playerAccount = playerAccountManager.getPlayerAccount(player.getUniqueId());

        if (playerAccount == null) {
            playerAccount = new PlayerAccount(player.getUniqueId(), 0, 0, "no-guild", "no-guild",
                    GuildRank.NO_GUILD, "no-waiting-kingdom", "no-kingdom", 0, 0, true);
        }

        playerAccountManager.getAccounts().add(playerAccount);

        TagUtils.handlePlayerDisplayName(player);

        if (playerAccount.getWaitingKingdomName().equals("no-waiting-kingdom") && playerAccount.getKingdomName().equals("no-kingdom")) {
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> new ChooseKingdomInventory().open(player), 1);
        }

        final PowerAdditionsBukkitRunnable powerAdditionsBukkitRunnable = new PowerAdditionsBukkitRunnable(player, playerAccount);
        powerAdditionsBukkitRunnable.runTaskTimerAsynchronously(Main.getInstance(), 10, 20);

        playerAccount.setPowerRunnable(powerAdditionsBukkitRunnable);
    }
}