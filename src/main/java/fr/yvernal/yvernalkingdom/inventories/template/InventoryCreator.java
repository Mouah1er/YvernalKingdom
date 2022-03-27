package fr.yvernal.yvernalkingdom.inventories.template;

import fr.yvernal.yvernalkingdom.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public abstract class InventoryCreator implements InventoryHolder {
    protected final Inventory inventory;

    public InventoryCreator(String name, int size) {
        this.inventory = Bukkit.createInventory(this, size, name);
    }

    protected ItemStack getItem(int slot) {
        return inventory.getItem(slot);
    }

    protected void addItem(ItemStack itemStack) {
        this.addItems(itemStack);
    }

    protected void addItems(ItemStack... itemStack) {
        inventory.addItem(itemStack);
    }

    protected void setItem(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
    }

    protected void setGlassToEmptySlots() {
        for (int i = 0; i < 54; i++) {
            if (getItem(i) == null) {
                setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE)
                        .name("§c")
                        .flags(ItemFlag.HIDE_ATTRIBUTES)
                        .build());
            }
        }
    }

    public void onOpen(InventoryOpenEvent event) {}

    public void onClick(InventoryClickEvent event) {}

    public void onClose(InventoryCloseEvent event) {}

    public void open(Player player) {
        player.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
