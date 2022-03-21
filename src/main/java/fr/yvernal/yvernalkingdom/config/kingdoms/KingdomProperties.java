package fr.yvernal.yvernalkingdom.config.kingdoms;

import fr.yvernal.yvernalkingdom.utils.Cuboid;
import org.bukkit.ChatColor;
import org.bukkit.Location;

/**
 * Représente toutes les propriétés d'un royaume, comme que son spawn
 */
public class KingdomProperties {
    private final String number;
    private final String name;
    private final ChatColor color;
    private final Location cristalLocation;
    private final Cuboid cristalCuboid;
    private final Cuboid safeZoneCuboid;
    private final Cuboid totalTerritoryCuboid;

    public KingdomProperties(String number, String name, ChatColor color, Location cristalLocation, Cuboid cristalCuboid, Cuboid safeZoneCuboid,
                             Cuboid totalTerritoryCuboid) {
        this.number = number;
        this.name = name;
        this.color = color;
        this.cristalLocation = cristalLocation;
        this.cristalCuboid = cristalCuboid;
        this.safeZoneCuboid = safeZoneCuboid;
        this.totalTerritoryCuboid = totalTerritoryCuboid;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public Location getCristalLocation() {
        return cristalLocation;
    }

    public Cuboid getCristalCuboid() {
        return cristalCuboid;
    }

    public Cuboid getSafeZoneCuboid() {
        return safeZoneCuboid;
    }

    public Cuboid getTotalTerritoryCuboid() {
        return totalTerritoryCuboid;
    }
}
