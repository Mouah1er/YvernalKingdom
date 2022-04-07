package fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims;

import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.Chunk;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClaimDataManager {
    private final DataManager dataManager;
    private final List<Claim> claims;

    public ClaimDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.claims = getClaimsFromDatabase();
    }

    public List<Claim> getClaimsFromDatabase() {
        final List<Claim> claims = new ArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM claims", resultSet -> {
            try {
                while (resultSet.next()) {
                    claims.add(new Claim(new ClaimData(resultSet.getString("guildUniqueId"), resultSet.getString("guildName"),
                            resultSet.getInt("x"), resultSet.getInt("z")),
                            false, false));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return claims;
    }

    public void addClaim(Guild guild, Chunk chunk) {
        dataManager.getDatabaseManager().update("INSERT INTO claims (x, z, guildName, guildUniqueId) VALUES(" +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?" +
                        ")",
                chunk.getX(),
                chunk.getZ(),
                guild.getGuildData().getName(),
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
                            "guildName=?, " +
                            "guildUniqueId=? " +
                            "WHERE x=?" +
                            "AND z=?",
                    claim.getClaimData().getGuildName(),
                    claim.getClaimData().getGuildUniqueId(),
                    claim.getClaimData().getX(),
                    claim.getClaimData().getZ());

        }
    }

    public List<Claim> getClaims() {
        return claims;
    }

    public List<Claim> getGuildClaims(Guild guild) {
        return getClaims().stream()
                .filter(Objects::nonNull)
                .filter(claim -> claim.getClaimData().getGuildName().equals(guild.getGuildData().getName()))
                .collect(Collectors.toList());
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
