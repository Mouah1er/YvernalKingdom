package fr.yvernal.yvernalkingdom.events;

import fr.yvernal.yvernalkingdom.kingdoms.crystal.Crystal;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public abstract class CrystalEvent extends PlayerEvent {
    protected final Crystal crystal;

    public CrystalEvent(Player who, Crystal crystal) {
        super(who);

        this.crystal = crystal;
    }

    public Crystal getCrystal() {
        return this.crystal;
    }
}
