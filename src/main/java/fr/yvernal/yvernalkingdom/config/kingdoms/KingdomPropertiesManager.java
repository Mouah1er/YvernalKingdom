package fr.yvernal.yvernalkingdom.config.kingdoms;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.utils.Cuboid;
import fr.yvernal.yvernalkingdom.utils.LocationUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Locale;

public class KingdomPropertiesManager {
    private final FileConfiguration config;

    public KingdomPropertiesManager() {
        this.config = Main.getInstance().getConfig();
    }

    public KingdomProperties getProperties(String kingdomName) {
        final String kingdomPropertiesPath = "kingdoms." + kingdomName.toLowerCase(Locale.ROOT) + ".";

        return new KingdomProperties(kingdomName, config.getString(kingdomPropertiesPath + "name"),
                getColor(kingdomPropertiesPath + "color"),
                getLocation(kingdomPropertiesPath + "cristal-location"),
                getCuboid(kingdomPropertiesPath + "cristal-cuboid"),
                getCuboid(kingdomPropertiesPath + "safe-zone-cuboid"),
                getCuboid(kingdomPropertiesPath + "total-territory-cuboid"));

    }

    public ChatColor getColor(String path) {
        if (path == null) {
            return null;
        } else {
            try {
                return ChatColor.valueOf(config.getString(path));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Location getLocation(String path) {
        if (path == null) {
            return null;
        } else {
            final String location = config.getString(path);

            return LocationUtils.stringToLocation(location);
        }
    }

    public Cuboid getCuboid(String path) {
        if (path == null) {
            return null;
        } else {
            final String locations = config.getString(path);

            return LocationUtils.stringToCuboid(locations);
        }
    }
}
