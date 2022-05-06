package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener extends YvernalListener<BlockPlaceEvent> {

    @Override
    @EventHandler
    public void onEvent(BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlockPlaced();

        final Claim claim = dataManager.getClaimManager().getClaimAt(event.getBlock().getChunk().getX(),
                event.getBlock().getChunk().getZ());

        if (claim != null && !claim.isUnClaim()) {
            final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

            if (!claim.getClaimData().getGuild().getGuildData().getGuildUniqueId().equals(playerAccount.getGuild().getGuildData().getGuildUniqueId())) {
                event.setCancelled(true);
                player.sendMessage(messagesManager.getString("player-cant-place-in-claim"));
            }
        }

        if (block.getType() == Material.TNT) {
            final Kingdom kingdomAt = dataManager.getKingdomDataManager()
                    .getKingdomByLocation(block.getLocation());

            if (kingdomAt != null) {
                if (kingdomAt.getKingdomProperties().getSpawnCuboid().isIn(block.getLocation())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
