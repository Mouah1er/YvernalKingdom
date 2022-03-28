package fr.yvernal.yvernalkingdom.utils;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

public class GuildLocation {
    private String worldName;
    private int x;
    private int z;

    public GuildLocation(final String worldName, int x, int z) {
        this.worldName = worldName;
        this.x = x;
        this.z = z;
    }

    public GuildLocation(Location location) {
        this(location.getWorld().getName(), blockToChunk(location.getBlockX()), blockToChunk(location.getBlockZ()));
    }

    public static int blockToChunk(int blockVal) {    // 1 chunk c'est 16x16 blocks
        return blockVal >> 4;   // ">> 4" == "/ 16"
    }

    public static int chunkToBlock(int chunkVal) {
        return chunkVal << 4;   // "<< 4" == "* 16"
    }

    public GuildLocation getRelative(int deltaX, int deltaZ) {
        return new GuildLocation(worldName, x + deltaX, z + deltaZ);
    }

    public Chunk getChunk() {
        return Bukkit.getWorld(worldName).getChunkAt(x, z);
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }


    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
