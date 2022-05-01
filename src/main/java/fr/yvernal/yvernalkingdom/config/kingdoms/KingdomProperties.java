package fr.yvernal.yvernalkingdom.config.kingdoms;

import fr.yvernal.yvernalkingdom.utils.locations.Cuboid;
import org.bukkit.ChatColor;
import org.bukkit.Location;

/**
 * Représente toutes les propriétés d'un royaume, comme que son spawn
 */
public class KingdomProperties {
    private final String number;
    private final String name;
    private final ChatColor color;
    private final Location crystalLocation;
    private final Cuboid crystalCuboid;
    private final Cuboid safeZoneCuboid;
    private final Cuboid totalTerritoryCuboid;
    private final Location spawnLocation;
    private final Cuboid spawnCuboid;

    public KingdomProperties(String number, String name, ChatColor color, Location crystalLocation, Cuboid crystalCuboid, Cuboid safeZoneCuboid,
                             Cuboid totalTerritoryCuboid, Location spawnLocation, Cuboid spawnCuboid) {
        this.number = number;
        this.name = name;
        this.color = color;
        this.crystalLocation = crystalLocation;
        this.crystalCuboid = crystalCuboid;
        this.safeZoneCuboid = safeZoneCuboid;
        this.totalTerritoryCuboid = totalTerritoryCuboid;
        this.spawnLocation = spawnLocation;
        this.spawnCuboid = spawnCuboid;
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

    public Location getCrystalLocation() {
        return crystalLocation;
    }

    public Cuboid getCrystalCuboid() {
        return crystalCuboid;
    }

    public Cuboid getSafeZoneCuboid() {
        return safeZoneCuboid;
    }

    public Cuboid getTotalTerritoryCuboid() {
        return totalTerritoryCuboid;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public Cuboid getSpawnCuboid() {
        return spawnCuboid;
    }

    @Override
    public String toString() {
        return "KingdomProperties{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", crystalLocation=" + crystalLocation +
                ", crystalCuboid=" + crystalCuboid +
                ", safeZoneCuboid=" + safeZoneCuboid +
                ", totalTerritoryCuboid=" + totalTerritoryCuboid +
                ", spawnLocation=" + spawnLocation +
                ", spawnCuboid=" + spawnCuboid +
                '}';
    }
}
