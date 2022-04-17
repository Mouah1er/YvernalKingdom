package fr.yvernal.yvernalkingdom.data.kingdoms.guilds;

import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims.ClaimData;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.invitedplayers.InvitedPlayerData;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdoms;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.invitedplayers.InvitedPlayer;
import fr.yvernal.yvernalkingdom.utils.LocationUtils;
import org.bukkit.Location;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Permet d'accéder à toutes les informations relatives à une guild dans la base de données
 */
public class GuildDataManager {
    private final DataManager dataManager;
    private final List<Guild> guilds;

    public GuildDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.guilds = new ArrayList<>();
    }

    public List<Guild> getAllGuildsFromDatabase() {
        final List<Guild> guilds = new ArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM guilds", resultSet -> {
            try {
                while (resultSet.next()) {
                    final Guild guild = getGuildFromDatabase(resultSet.getString("guildUniqueId"));

                    guilds.add(guild);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return guilds;
    }

    private List<Claim> getClaims(Guild guild) {
        final List<Claim> claims = new ArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM claims WHERE guildUniqueId=?", resultSet -> {
            try {
                while (resultSet.next()) {
                    final int x = resultSet.getInt("x");
                    final int z = resultSet.getInt("z");

                    claims.add(new Claim(new ClaimData(guild, x, z), false, false));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, guild.getGuildData().getGuildUniqueId());

        return claims;
    }

    private List<InvitedPlayer> getInvitedPlayers(Guild guild) {
        final List<InvitedPlayer> invitedPlayers = new ArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM invitedPlayers WHERE guildUniqueId=?", resultSet -> {
            try {
                while (resultSet.next()) {
                    final UUID uniqueId = UUID.fromString(resultSet.getString("uniqueId"));

                    invitedPlayers.add(new InvitedPlayer(new InvitedPlayerData(uniqueId, guild), true, false));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, guild.getGuildData().getGuildUniqueId());

        return invitedPlayers;
    }

    public Guild getGuildFromDatabase(String guildUniqueId) {
        final AtomicReference<Guild> atomicGuild = new AtomicReference<>();

        dataManager.getDatabaseManager().query("SELECT * FROM guilds WHERE guildUniqueId=?", resultSet -> {
            try {
                if (resultSet.next()) {
                    final String guildName = resultSet.getString("guildName");
                    final Kingdom kingdom = Kingdoms.getByNumber(resultSet.getString("kingdomName"));
                    final String description = resultSet.getString("description");
                    final int power = resultSet.getInt("power");
                    final Location home = resultSet.getString("home").equals("no-home") ? null :
                            LocationUtils.stringToLocation(resultSet.getString("home"));
                    final UUID ownerUniqueId = UUID.fromString(resultSet.getString("ownerUniqueId"));
                    final List<UUID> membersUniqueId = getMembersUniqueId(guildUniqueId);
                    final List<Claim> claims = new ArrayList<>();
                    final List<InvitedPlayer> invitedPlayers = new ArrayList<>();

                    final Guild guild = new Guild(new GuildData(guildUniqueId, kingdom, guildName, description, power, home, ownerUniqueId, membersUniqueId,
                            claims, invitedPlayers), false, false);

                    claims.addAll(getClaims(guild));
                    guild.getGuildData().getClaims().addAll(claims);

                    invitedPlayers.addAll(getInvitedPlayers(guild));
                    guild.getGuildData().getInvitedPlayers().addAll(invitedPlayers);

                    atomicGuild.set(guild);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, guildUniqueId);

        return atomicGuild.get();
    }

    private void createGuild(Guild guild) {
        final String home = guild.getGuildData().getHome() == null ? "no-home" : LocationUtils.locationToString(guild.getGuildData().getHome());

        dataManager.getDatabaseManager().update("INSERT INTO guilds (guildUniqueId, guildName, description, power, home, ownerUniqueId, kingdomName) " +
                        "VALUES (" +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?" +
                        ")",
                guild.getGuildData().getGuildUniqueId(),
                guild.getGuildData().getName(),
                guild.getGuildData().getDescription(),
                guild.getGuildData().getPower(),
                home,
                guild.getGuildData().getOwnerUniqueId(),
                guild.getGuildData().getKingdom().getKingdomProperties().getNumber());
    }


    private void deleteGuildFromDatabase(Guild guild) {
        dataManager.getDatabaseManager().update("DELETE FROM guilds " +
                "WHERE guildUniqueId=?", guild.getGuildData().getGuildUniqueId());
    }

    public void updateGuildToDatabase(Guild guild) {
        if (guild.isDeleted() || guild.isNew()) {
            if (guild.isDeleted()) {
                deleteGuildFromDatabase(guild);
            }
            if (guild.isNew()) {
                createGuild(guild);
            }
        } else {
            final String home = guild.getGuildData().getHome() == null ? "no-home" : LocationUtils.locationToString(guild.getGuildData().getHome());

            dataManager.getDatabaseManager().update("UPDATE guilds SET " +
                            "guildName=?, " +
                            "description=?, " +
                            "home=?, " +
                            "ownerUniqueId=?, " +
                            "kingdomName=?, " +
                            "power=? " +
                            "WHERE guildUniqueId=?",
                    guild.getGuildData().getName(),
                    guild.getGuildData().getDescription(),
                    home,
                    guild.getGuildData().getOwnerUniqueId(),
                    guild.getGuildData().getKingdom().getKingdomProperties().getNumber(),
                    guild.getGuildData().getPower(),
                    guild.getGuildData().getGuildUniqueId());
        }
    }

    private List<UUID> getMembersUniqueId(String guildUniqueId) {
        final List<UUID> uuids = new ArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts WHERE guildUniqueId=?", resultSet -> {
            try {
                while (resultSet.next()) {
                    uuids.add(UUID.fromString(resultSet.getString("uniqueId")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, guildUniqueId);

        return uuids;
    }

    public List<Guild> getGuilds() {
        return guilds;
    }

    public Guild getGuildByName(String guildName) {
        return getGuilds().stream()
                .filter(Objects::nonNull)
                .filter(guild -> guild.getGuildData().getName().equals(guildName))
                .findFirst()
                .orElse(null);
    }

    public Guild getGuildByUniqueId(UUID guildUniqueId) {
        return getGuilds().stream()
                .filter(Objects::nonNull)
                .filter(guild -> UUID.fromString(guild.getGuildData().getGuildUniqueId()).equals(guildUniqueId))
                .findFirst()
                .orElse(null);
    }
}
