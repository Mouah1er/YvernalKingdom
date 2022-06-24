package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.kingdoms.KingdomProperties;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import fr.yvernal.yvernalkingdom.utils.CrystalUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;

public class EntityExplodeListener extends YvernalListener<EntityExplodeEvent> {

    @Override
    @EventHandler
    public void onEvent(EntityExplodeEvent event) {
        final Entity entity = event.getEntity();
        final EntityType entityType = event.getEntityType();
        final Kingdom kingdomAt = dataManager.getKingdomDataManager().getKingdomByLocation(event.getLocation());
        final KingdomProperties kingdomProperties = kingdomAt.getKingdomProperties();

        if (kingdomProperties.getSpawnCuboid().isIn(entity.getLocation()) ||
                kingdomProperties.getCrystalCuboid().isIn(entity.getLocation())) {
            event.setCancelled(true);
        }

        if (entityType == EntityType.ENDER_CRYSTAL) {
            CrystalUtils.spawnCrystal(event.getLocation());
        }

        if (entityType == EntityType.CREEPER || entityType == EntityType.PRIMED_TNT || entityType == EntityType.MINECART_TNT) {
            if (Main.getInstance().getSpawnedDangerousEntitiesByPlayer().containsKey(entity.getUniqueId())) {
                final PlayerAccount playerAccount = Main.getInstance().getSpawnedDangerousEntitiesByPlayer().get(entity.getUniqueId());

                final Kingdom playerKingdom = dataManager.getKingdomDataManager().getKingdomByNumber(playerAccount.getKingdom().getKingdomProperties()
                        .getNumber());
                final Guild playerGuild = playerAccount.getGuild();

                if (playerGuild != null) {
                    final Claim claimAt = dataManager.getClaimManager().getClaimAt(event.getLocation().getChunk().getX(),
                            event.getLocation().getChunk().getZ());
                    final List<Claim> guildClaims = playerGuild.getGuildData().getClaims();

                    if (playerKingdom.getKingdomProperties().getNumber().equals(kingdomProperties.getNumber()) &&
                            !guildClaims.contains(claimAt)) {
                        event.setCancelled(true);
                    }
                } else {
                    if (playerKingdom.getKingdomProperties().getNumber().equals(kingdomProperties.getNumber())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
