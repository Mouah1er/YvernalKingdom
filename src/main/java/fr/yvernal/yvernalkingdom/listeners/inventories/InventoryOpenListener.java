package fr.yvernal.yvernalkingdom.listeners.inventories;

import fr.yvernal.yvernalkingdom.inventories.template.InventoryCreator;
import fr.yvernal.yvernalkingdom.listeners.YvernalListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryOpenListener implements YvernalListener<InventoryOpenEvent> {

    @Override
    @EventHandler
    public void onEvent(InventoryOpenEvent event) {
        if (event.getInventory().getHolder() instanceof InventoryCreator) {
            final InventoryCreator inventoryCreator = (InventoryCreator) event.getInventory().getHolder();

            inventoryCreator.onOpen(event);
        }
    }
}
