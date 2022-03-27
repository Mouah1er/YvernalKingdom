package fr.yvernal.yvernalkingdom.kingdoms.guilds.claims;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims.ClaimData;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

public class Claim {
    private final ClaimData claimData;
    private boolean isUnClaim;
    private boolean isNew;

    public Claim(ClaimData claimData, boolean isUnClaim, boolean isNew) {
        this.claimData = claimData;
        this.isUnClaim = isUnClaim;
        this.isNew = isNew;
    }

    public Chunk toChunk() {
        final String worldName = Main.getInstance().getConfigManager().getGameConfigManager().getString("world-name");

        return Bukkit.getWorld(worldName).getChunkAt(claimData.getX(), claimData.getZ());
    }

    public ClaimData getClaimData() {
        return claimData;
    }

    public boolean isUnClaim() {
        return isUnClaim;
    }

    public void setUnClaim(boolean unClaim) {
        System.out.println(unClaim);
        isUnClaim = unClaim;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "claimData=" + claimData +
                ", isUnClaim=" + isUnClaim +
                ", isNew=" + isNew +
                '}';
    }
}
