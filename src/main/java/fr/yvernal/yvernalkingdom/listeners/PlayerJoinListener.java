package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccountManager;
import fr.yvernal.yvernalkingdom.inventories.ChooseKingdomInventory;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import fr.yvernal.yvernalkingdom.tasks.PowerAdditionsBukkitRunnable;
import fr.yvernal.yvernalkingdom.utils.nametag.NameTagManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
            playerAccount = new PlayerAccount(player.getUniqueId(), 0, 0, null, GuildRank.NO_GUILD, null,
                    null, 0, 0, true);
        }

        playerAccountManager.getAccounts().add(playerAccount);

        Main.getInstance().getNameTagManager().showPlayerNameTag(player);

        if (playerAccount.getWaitingKingdom() == null && playerAccount.getKingdom() == null) {
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> new ChooseKingdomInventory().open(player), 1);
        }

        final PowerAdditionsBukkitRunnable powerAdditionsBukkitRunnable = new PowerAdditionsBukkitRunnable(player, playerAccount);
        powerAdditionsBukkitRunnable.runTaskTimerAsynchronously(Main.getInstance(), 10, 20);

        playerAccount.setPowerRunnable(powerAdditionsBukkitRunnable);
    }
}