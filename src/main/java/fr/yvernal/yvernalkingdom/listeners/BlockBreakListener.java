package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener extends YvernalListener<BlockBreakEvent> {

    @Override
    @EventHandler
    public void onEvent(BlockBreakEvent event) {
        final Player player = event.getPlayer();

        final Claim claim = dataManager.getClaimManager().getClaimAt(event.getBlock().getChunk().getX(),
                event.getBlock().getChunk().getZ());

        if (claim != null && !claim.isUnClaim()) {
            final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

            if (playerAccount.getGuild() != null) {
                if (!claim.getClaimData().getGuild().getGuildData().getGuildUniqueId().equals(playerAccount.getGuild().getGuildData().getGuildUniqueId())) {
                    event.setCancelled(true);
                    player.sendMessage(messagesManager.getString("player-cant-break-in-claim"));
                }
            } else {
                event.setCancelled(true);
                player.sendMessage(messagesManager.getString("player-cant-break-in-claim"));
            }
        }
    }
}
