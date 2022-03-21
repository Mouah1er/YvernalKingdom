package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccountManager;
import fr.yvernal.yvernalkingdom.inventories.ChooseKingdomInventory;
import fr.yvernal.yvernalkingdom.tasks.PowerAdditionsBukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements YvernalListener<PlayerJoinEvent> {

    @Override
    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final PlayerAccountManager playerAccountManager = Main.getInstance().getDataManager().getPlayerAccountManager();
        PlayerAccount playerAccount = playerAccountManager.getPlayerAccountFromDatabase(player.getUniqueId());

        if (playerAccount == null) {
            playerAccount = playerAccountManager.createPlayerAccount(player.getUniqueId());
        }

        playerAccountManager.getAccounts().add(playerAccount);

        if (playerAccount.getWaitingKingdomName().equals("no-waiting-kingdom") && playerAccount.getKingdomName().equals("no-kingdom")) {
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> new ChooseKingdomInventory().open(player), 1);
        }

        playerAccount.setPowerTaskId(new PowerAdditionsBukkitRunnable(player, playerAccount)
                .runTaskTimerAsynchronously(Main.getInstance(), 10, 20).getTaskId());
    }
}