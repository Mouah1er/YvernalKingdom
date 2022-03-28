package fr.yvernal.yvernalkingdom.listeners.inventories;

import fr.yvernal.yvernalkingdom.inventories.template.InventoryCreator;
import fr.yvernal.yvernalkingdom.listeners.YvernalListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener extends YvernalListener<InventoryClickEvent> {

    @Override
    @EventHandler
    public void onEvent(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof InventoryCreator) {
            final InventoryCreator inventoryCreator = (InventoryCreator) event.getInventory().getHolder();

            event.setCancelled(true);

            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
                inventoryCreator.onClick(event);
            }
        }
    }
}
