package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccountManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements YvernalListener<PlayerQuitEvent> {

    @Override
    @EventHandler
    public void onEvent(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final PlayerAccountManager playerAccountManager = Main.getInstance().getDataManager().getPlayerAccountManager();
        final PlayerAccount playerAccount = playerAccountManager.getPlayerAccount(player.getUniqueId());

        playerAccountManager.updatePlayerAccountToDatabase(playerAccount);
        playerAccountManager.getAccounts().remove(playerAccount);

        Bukkit.getScheduler().cancelTask(playerAccount.getPowerTaskId());
    }
}
