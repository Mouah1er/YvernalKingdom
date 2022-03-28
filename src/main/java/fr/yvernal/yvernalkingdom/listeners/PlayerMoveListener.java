package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener extends YvernalListener<PlayerMoveEvent> {

    @Override
    @EventHandler
    public void onEvent(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockZ() != event.getTo().getBlockZ() ||
                event.getFrom().getBlockY() != event.getTo().getBlockY()) {
            final PlayerAccount playerAccount = dataManager.getPlayerAccountManager()
                    .getPlayerAccount(player.getUniqueId());

            if (playerAccount.isWaitingToTeleportToHome()) {
                playerAccount.setWaitingToTeleportToHome(false);
                player.sendMessage(messagesManager.getString("player-has-moved"));
            }
        }
    }
}
