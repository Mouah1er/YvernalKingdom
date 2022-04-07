package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class EntityExplodeListener extends YvernalListener<EntityExplodeEvent> {

    @Override
    @EventHandler
    public void onEvent(EntityExplodeEvent event) {
        final EntityType entityType = event.getEntityType();

        if (entityType == EntityType.CREEPER || entityType == EntityType.PRIMED_TNT || entityType == EntityType.MINECART_TNT) {
            final Entity entity = event.getEntity();

            if (Main.getInstance().getSpawnedCreepersByPlayer().containsKey(entity.getUniqueId())) {
                final PlayerAccount playerAccount = Main.getInstance().getSpawnedCreepersByPlayer().get(entity.getUniqueId());

                final Kingdom playerKingdom = dataManager.getKingdomDataManager().getKingdomByNumber(playerAccount.getKingdomName());
                final Kingdom kingdomAt = dataManager.getKingdomDataManager().getKingdomByLocation(event.getLocation());

                final Guild playerGuild = dataManager.getGuildDataManager().getGuildByPlayer(playerAccount.getUniqueId());
                final Claim claimAt = dataManager.getClaimManager().getClaimAt(event.getLocation().getChunk().getX(),
                        event.getLocation().getChunk().getZ());
                final List<Claim> guildClaims = dataManager.getClaimManager().getGuildClaims(playerGuild);

                if (playerKingdom.getKingdomProperties().getNumber().equals(kingdomAt.getKingdomProperties().getNumber()) &&
                        !guildClaims.contains(claimAt)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
