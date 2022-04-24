package fr.yvernal.yvernalkingdom.events;

import fr.yvernal.yvernalkingdom.kingdoms.crystal.Crystal;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class CrystalDamageByPlayerEvent extends CrystalEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Crystal crystal;

    private boolean cancelled;
    private double damage;

    public CrystalDamageByPlayerEvent(Player damager, Crystal crystal, double damage) {
        super(damager, crystal);

        this.crystal = crystal;
        this.damage = damage;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Crystal getCrystal() {
        return crystal;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
