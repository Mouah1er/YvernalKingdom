package fr.yvernal.yvernalkingdom.kingdoms.guilds.claims;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.messages.MessagesManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims.ClaimData;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.Objects;

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

    public boolean claim(Guild guild, Player player, MessagesManager messagesManager) {
        if (!this.isUnClaim()) {
            player.sendMessage(messagesManager.getString("already-claim-by-guild-error")
                    .replace("%guilde%", this.getClaimData().getGuildName()));

            return false;
        } else {
            this.setUnClaim(false);

            guild.getGuildData().getMembersUniqueId()
                    .stream()
                    .map(Bukkit::getPlayer)
                    .filter(Objects::nonNull)
                    .forEach(player1 -> player1.sendMessage(messagesManager.getString("successfully-claimed-chunk")
                            .replace("%player%", player.getName())
                            .replace("%x%", String.valueOf(player.getLocation().getChunk().getX()))
                            .replace("%z%", String.valueOf(player.getLocation().getChunk().getZ()))));

            return true;
        }
    }

    public ClaimData getClaimData() {
        return claimData;
    }

    public boolean isUnClaim() {
        return isUnClaim;
    }

    public void setUnClaim(boolean unClaim) {
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
