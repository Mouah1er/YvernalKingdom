package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.config.kingdoms.KingdomProperties;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityAddToWorldEvent;

public class EntityAddToWorldListener extends YvernalListener<EntityAddToWorldEvent> {

    @Override
    @EventHandler
    public void onEvent(EntityAddToWorldEvent event) {
        final Entity entity = event.getEntity();

        if (entity instanceof TNTPrimed) {
            final Location entityLocation = entity.getLocation();
            final Kingdom kingdomAtExplosion = dataManager.getKingdomDataManager().getKingdomByLocation(entityLocation);
            final KingdomProperties kingdomProperties = kingdomAtExplosion.getKingdomProperties();

            if (kingdomProperties.getSpawnCuboid().isIn(entityLocation) ||
                    kingdomProperties.getCrystalCuboid().isIn(entityLocation)) {
                entity.remove();
            }
        }
    }
}
