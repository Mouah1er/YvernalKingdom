package fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims;

import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.data.DataManagerTemplate;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import fr.yvernal.yvernalkingdom.utils.list.YvernalArrayList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class ClaimDataManager implements DataManagerTemplate<Claim> {
    private final DataManager dataManager;
    private final List<Claim> claims;

    public ClaimDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.claims = new YvernalArrayList<>();
    }

    @Override
    public List<Claim> getAllFromDatabase() {
        final List<Claim> claims = new YvernalArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM claims", resultSet -> {
            try {
                while (resultSet.next()) {
                    claims.add(getFromDatabase(resultSet.getString("guildUniqueId")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return claims;
    }

    @Override
    public Claim getFromDatabase(String uniqueId) {
        final AtomicReference<Claim> claim = new AtomicReference<>();

        dataManager.getDatabaseManager().query("SELECT * FROM claims WHERE guildUniqueId=?", resultSet -> {
            try {
                if (resultSet.next()) {
                    final Guild guild = dataManager.getGuildDataManager().getGuildByUniqueId(UUID.fromString(uniqueId));
                    final int x = resultSet.getInt("x");
                    final int z = resultSet.getInt("z");

                    claim.set(new Claim(new ClaimData(guild, x, z), false, false, true));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, uniqueId);

        return claim.get();
    }

    @Override
    public void addToDatabase(Claim claim) {
        dataManager.getDatabaseManager().update("INSERT INTO claims (x, z, guildUniqueId) VALUES(" +
                        "?, " +
                        "?, " +
                        "?" +
                        ")",
                claim.toChunk().getX(),
                claim.toChunk().getZ(),
                claim.getClaimData().getGuild().getGuildData().getGuildUniqueId());
    }

    @Override
    public void deleteFromDatabase(Claim claim) {
        dataManager.getDatabaseManager().update("DELETE FROM claims " +
                "WHERE x=" + claim.getClaimData().getX() + " " +
                "AND z=" + claim.getClaimData().getZ() + " " +
                "AND guildUniqueId='" + claim.getClaimData().getGuild().getGuildData().getGuildUniqueId() + "'");
    }

    @Override
    public void updateToDatabase(Claim claim) {
        if (claim.isUnClaim() || claim.isNew()) {
            if (claim.isUnClaim()) {
                deleteFromDatabase(claim);
            }
            if (claim.isNew()) {
                addToDatabase(claim);
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
