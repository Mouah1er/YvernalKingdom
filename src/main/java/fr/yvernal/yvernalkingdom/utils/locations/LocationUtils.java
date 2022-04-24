package fr.yvernal.yvernalkingdom.utils.locations;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class LocationUtils {

    /**
     * Permet de récupérer une Location à partir d'un String stocké en base de données ou dans un fichier de config
     *
     * @param string le String en question
     * @return la Location en question
     */
    public static Location stringToLocation(String string) {
        final String[] location = string.split(", ");

        return location.length == 6 ? new Location(Bukkit.getWorld(location[0]), Double.parseDouble(location[1]), Double.parseDouble(location[2]),
                Double.parseDouble(location[3]), Float.parseFloat(location[4]), Float.parseFloat(location[5])) :
                new Location(Bukkit.getWorld(location[0]),
                        Double.parseDouble(location[1]),
                        Double.parseDouble(location[2]),
                        Double.parseDouble(location[3]));
    }

    /**
     * Permet de récupérer un String à partir d'une Location.
     *
     * @param location la Location en question
     * @return le String en question
     */
    public static String locationToString(Location location) {
        return location.getWorld().getName() + ", " + location.getX() + ", " + location.getY() + ", " + location.getZ() + ", " + location.getYaw() + ", " +
                location.getPitch();
    }

    /**
     * Permet de récupérer un Cuboid à partir d'un String stocké en base de données ou dans un fichier de config
     *
     * @param string le String en question
     * @return le Cuboid en question
     */
    public static Cuboid stringToCuboid(String string) {
        final String[] locations = string.split(", ");

        final World world = Bukkit.getWorld(locations[0]);
        final double xMin = Double.parseDouble(locations[1]);
        final double yMin = Double.parseDouble(locations[2]);
        final double zMin = Double.parseDouble(locations[3]);
        final double xMax = Double.parseDouble(locations[4]);
        final double yMax = Double.parseDouble(locations[5]);
        final double zMax = Double.parseDouble(locations[6]);

        return new Cuboid(world, xMin, yMin, zMin, xMax, yMax, zMax);
    }

    public static List<Location> cube(Cuboid cuboid) {
        return new ArrayList<>(cuboid.iterator().getLocations()) {{
            addAll(cuboid.iterator().getLocations());
            addAll(cuboid.iterator().getLocations());
        }};
    }
}
