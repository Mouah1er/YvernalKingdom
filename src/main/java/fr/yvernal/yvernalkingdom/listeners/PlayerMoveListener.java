package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener extends YvernalListener<PlayerMoveEvent> {

    @Override
    @EventHandler
    public void onEvent(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final Location from = event.getFrom();
        final Location to = event.getTo();

        if (from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ() ||
                from.getBlockY() != to.getBlockY()) {
            final PlayerAccount playerAccount = dataManager.getPlayerAccountManager()
                    .getPlayerAccount(player.getUniqueId());

            if (playerAccount.isWaitingToTeleportToHome()) {
                playerAccount.setWaitingToTeleportToHome(false);
                player.sendMessage(messagesManager.getString("player-has-moved"));
            }

            final Kingdom kingdomFrom = dataManager.getKingdomDataManager().getKingdomByLocation(from);
            final Kingdom kingdomTo = dataManager.getKingdomDataManager().getKingdomByLocation(to);

            if (kingdomFrom != null && kingdomTo != null && !kingdomFrom.getKingdomProperties().getNumber().equals(
                    kingdomTo.getKingdomProperties().getNumber())) {
                player.sendMessage(messagesManager.getString("player-has-moved-to-new-kingdom")
                        .replace("%kingdom%", kingdomTo.getKingdomProperties().getName()));
            }
        }
    }
}
