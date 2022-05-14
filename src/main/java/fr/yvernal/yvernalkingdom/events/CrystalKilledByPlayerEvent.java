package fr.yvernal.yvernalkingdom.events;

import fr.yvernal.yvernalkingdom.kingdoms.crystal.Crystal;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class CrystalKilledByPlayerEvent extends CrystalEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public CrystalKilledByPlayerEvent(Player who, Crystal crystal) {
        super(who, crystal);
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
