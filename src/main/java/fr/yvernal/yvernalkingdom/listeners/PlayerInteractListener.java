package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener extends YvernalListener<PlayerInteractEvent> {

    @Override
    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_BLOCK) {
            final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());
            final Claim claim = dataManager.getClaimManager().getClaimAt(event.getClickedBlock().getChunk().getX(),
                    event.getClickedBlock().getChunk().getZ());

            if (claim != null) {
                if (claim.isActivated()) {
                    if (!claim.isUnClaim()) {
                        final Guild playerGuild = playerAccount.getGuild();
                        //if (event.getClickedBlock().getState() != null) {
                        if (playerGuild != null) {
                            if (!claim.getClaimData().getGuild().getGuildData().getGuildUniqueId().equals(playerGuild.getGuildData().getGuildUniqueId())) {
                                event.setCancelled(true);
                                //player.sendMessage(messagesManager.getString("player-cant-interact-in-claim"));
                            }
                        } else {
                            event.setCancelled(true);
                            //player.sendMessage(messagesManager.getString("player-cant-interact-in-claim"));
                        }
                        //}
                    }
                }
            }
        }
    }
}
