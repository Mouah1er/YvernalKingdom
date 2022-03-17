package fr.yvernal.yvernalkingdom.listeners.inventories;

import fr.yvernal.yvernalkingdom.inventories.template.InventoryCreator;
import fr.yvernal.yvernalkingdom.listeners.YvernalListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements YvernalListener<InventoryCloseEvent> {

    @Override
    @EventHandler
    public void onEvent(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof InventoryCreator) {
            final InventoryCreator inventoryCreator = (InventoryCreator) event.getInventory().getHolder();

            inventoryCreator.onClose(event);
        }
    }
}
