package fr.yvernal.yvernalkingdom.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityAddToWorldEvent;

public class EntityAddToWorldListener extends YvernalListener<EntityAddToWorldEvent> {

    @Override
    @EventHandler
    public void onEvent(EntityAddToWorldEvent event) {
        System.out.println("test");
    }
}
