package fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims;

import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.Chunk;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClaimDataManager {
    private final DataManager dataManager;
    private final List<Claim> claims;

    public ClaimDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.claims = new ArrayList<>();
    }

    public List<Claim> getClaimsFromDatabase() {
        final List<Claim> claims = new ArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM claims", resultSet -> {
            try {
                while (resultSet.next()) {
                    final Guild guild = dataManager.getGuildDataManager().getGuildByUniqueId(UUID.fromString(resultSet.getString("guildUniqueId")));
                    final int x = resultSet.getInt("x");
                    final int z = resultSet.getInt("z");

                    claims.add(new Claim(new ClaimData(guild, x, z),
                            false, false));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return claims;
    }

    public void addClaim(Guild guild, Chunk chunk) {
        dataManager.getDatabaseManager().update("INSERT INTO claims (x, z, guildUniqueId) VALUES(" +
                        "?, " +
                        "?, " +
                        "?" +
                        ")",
                chunk.getX(),
                chunk.getZ(),
                guild.getGuildData().getGuildUniqueId());
    }

    public void unClaim(Guild guild, Claim claim) {
        dataManager.getDatabaseManager().update("DELETE FROM claims " +
                "WHERE x=" + claim.getClaimData().getX() + " " +
                "AND z=" + claim.getClaimData().getZ() + " " +
                "AND guildUniqueId='" + guild.getGuildData().getGuildUniqueId() + "'");
    }

    public void updateClaimToDatabase(Guild guild, Claim claim) {
        if (claim.isUnClaim() || claim.isNew()) {
            if (claim.isUnClaim()) {
                unClaim(guild, claim);
            }
            if (claim.isNew()) {
                addClaim(guild, claim.toChunk());
            }
        } else {
            dataManager.getDatabaseManager().update("UPDATE claims SET " +
                            "guildUniqueId=? " +
                            "WHERE x=?" +
                            "AND z=?",
                    claim.getClaimData().getGuild().getGuildData().getGuildUniqueId(),
                    claim.getClaimData().getX(),
                    claim.getClaimData().getZ());

        }
    }

    public List<Claim> getClaims() {
        return claims;
    }

    public Claim getClaimAt(int x, int z) {
        return getClaims().stream()
                .filter(Objects::nonNull)
                .filter(claim -> claim.getClaimData().getX() == x)
                .filter(claim -> claim.getClaimData().getZ() == z)
                .findFirst()
                .orElse(null);
    }
}
