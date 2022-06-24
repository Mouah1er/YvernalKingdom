package fr.yvernal.yvernalkingdom.utils.locations;


import fr.yvernal.yvernalkingdom.utils.list.YvernalArrayList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cuboid {
    private final double xMin;
    private final double xMax;
    private final double yMin;
    private final double yMax;
    private final double zMin;
    private final double zMax;
    private final World world;

    public Cuboid(Location point1, Location point2) {
        this.xMin = Math.min(point1.getBlockX(), point2.getBlockX());
        this.xMax = Math.max(point1.getBlockX(), point2.getBlockX());
        this.yMin = Math.min(point1.getBlockY(), point2.getBlockY());
        this.yMax = Math.max(point1.getBlockY(), point2.getBlockY());
        this.zMin = Math.min(point1.getBlockZ(), point2.getBlockZ());
        this.zMax = Math.max(point1.getBlockZ(), point2.getBlockZ());
        this.world = point1.getWorld();
    }

    public Cuboid(World world, double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
        this(new Location(world, xMin, yMin, zMin), new Location(world, xMax, yMax, zMax));
    }

    public Location getPoint1() {
        return new Location(this.world, this.xMin, this.yMin, this.zMin);
    }

    public Location getPoint2() {
        return new Location(this.world, this.xMax, this.yMax, this.zMax);
    }

    public boolean isIn(final Location loc) {
        return loc.getWorld() == this.world && loc.getBlockX() >= this.xMin && loc.getBlockX() <= this.xMax && loc.getBlockY() >= this.yMin && loc.getBlockY() <= this.yMax && loc
                .getBlockZ() >= this.zMin && loc.getBlockZ() <= this.zMax;
    }

    public boolean isIn(final Entity entity) {
        return this.isIn(entity.getLocation());
    }

    public CuboidIterator iterator() {
        return new CuboidIterator(new Cuboid(world, xMin, yMin, zMin, xMax, yMax, zMax));
    }

    public class CuboidIterator implements Iterator<Block> {
        private final Cuboid cci;
        private final World wci;
        private final double baseX;
        private final double baseY;
        private final double baseZ;
        private final double sizeX;
        private final double sizeY;
        private final double sizeZ;
        private final List<Location> blocks3;
        private double x;
        private double y;
        private double z;

        public CuboidIterator(Cuboid c) {
            this.cci = c;
            this.wci = world;
            this.baseX = xMin;
            this.baseY = yMin;
            this.baseZ = zMin;
            this.sizeX = Math.abs(xMax - xMin) + 1;
            this.sizeY = Math.abs(yMax - yMin) + 1;
            this.sizeZ = Math.abs(zMax - yMin) + 1;
            this.x = this.y = this.z = 0;
            this.blocks3 = new YvernalArrayList<>();
        }

        public Cuboid getCuboid() {
            return cci;
        }

        public boolean hasNext() {
            return x < sizeX && y < sizeY && z < sizeZ;
        }

        public Block next() {
            Block b = world.getBlockAt((int) (baseX + x), (int) (baseY + y), (int) (baseZ + z));
            if (++x >= sizeX) {
                x = xMin;
                if (++y >= sizeY) {
                    y = yMin;
                    ++z;
                }
            }
            return b;
        }

        public List<Location> getLocations() {
            for (double x = cci.xMin; x <= cci.xMax; x++) {
                for (double z = cci.zMin; z <= cci.zMax; z++) {
                    for (double y = cci.yMin; y < cci.yMax; y++) {
                        blocks3.add(new Location(wci, x, y, z));
                    }
                }
            }
            return blocks3;
        }
    }

    @Override
    public String toString() {
        return "Cuboid{" +
                "xMin=" + xMin +
                ", xMax=" + xMax +
                ", yMin=" + yMin +
                ", yMax=" + yMax +
                ", zMin=" + zMin +
                ", zMax=" + zMax +
                ", world=" + world +
                '}';
    }
}
