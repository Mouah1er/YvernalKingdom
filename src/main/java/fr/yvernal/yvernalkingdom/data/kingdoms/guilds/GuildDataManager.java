package fr.yvernal.yvernalkingdom.data.kingdoms.guilds;

import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
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
        this.guilds = getAllGuildsFromDatabase();
    }

    public List<Guild> getAllGuildsFromDatabase() {
        final List<Guild> guilds = new ArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM guilds", resultSet -> {
            try {
                while (resultSet.next()) {
                    Location home;

                    if (resultSet.getString("home").equals("no-home")) {
                        home = null;
                    } else {
                        home = LocationUtils.stringToLocation(resultSet.getString("home"));
                    }

                    final String guildUniqueId = resultSet.getString("guildUniqueId");
                    final String kingdomName = resultSet.getString("kingdomName");
                    final String guildName = resultSet.getString("guildName");
                    final String description = resultSet.getString("description");
                    final int power = resultSet.getInt("power");
                    final UUID ownerUniqueId = UUID.fromString(resultSet.getString("ownerUniqueId"));
                    final List<UUID> membersUniqueId = getMembersUniqueId(guildUniqueId);

                    guilds.add(new Guild(new GuildData(guildUniqueId, kingdomName, guildName, description, power, home, ownerUniqueId, membersUniqueId),
                            false, false));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return guilds;
    }

    public Guild getGuildFromDatabase(String guildUniqueId) {
        final AtomicReference<Guild> guild = new AtomicReference<>();

        dataManager.getDatabaseManager().query("SELECT * FROM guilds WHERE guildUniqueId='" + guildUniqueId + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    final String guildName = resultSet.getString("guildName");
                    final String kingdomName = resultSet.getString("kingdomName");
                    final String description = resultSet.getString("description");
                    final int power = resultSet.getInt("power");
                    final UUID ownerUniqueId = UUID.fromString(resultSet.getString("ownerUniqueId"));
                    final List<UUID> membersUniqueId = getMembersUniqueId(guildUniqueId);
                    if (!resultSet.getString("home").equals("no-home")) {
                        final Location home = LocationUtils.stringToLocation(resultSet.getString("home"));
                        guild.set(new Guild(new GuildData(guildUniqueId, kingdomName, guildName, description, power, home, ownerUniqueId, membersUniqueId),
                                false, false));
                    } else {
                        guild.set(new Guild(new GuildData(guildUniqueId, kingdomName, guildName, description, power, null, ownerUniqueId,
                                membersUniqueId), false, false));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return guild.get();
    }

    public void createGuild(Guild guild) {
        final String home = guild.getGuildData().getHome() == null ? "no-home" : LocationUtils.locationToString(guild.getGuildData().getHome());

        dataManager.getDatabaseManager().update("INSERT INTO guilds (guildUniqueId, guildName, description, power, home, ownerUniqueId, kingdomName) " +
                "VALUES (" +
                "'" + guild.getGuildData().getGuildUniqueId() + "', " +
                "'" + guild.getGuildData().getName() + "', " +
                "'" + guild.getGuildData().getDescription() + "', " +
                "'" + guild.getGuildData().getPower() + "', " +
                "'" + home + "', " +
                "'" + guild.getGuildData().getOwnerUniqueId() + "', " +
                "'" + guild.getGuildData().getKingdomName() + "'" +
                ")");
    }


    public void deleteGuild(Guild guild) {
        final String home = guild.getGuildData().getHome() == null ? "no-home" : LocationUtils.locationToString(guild.getGuildData().getHome());

        dataManager.getDatabaseManager().update("DELETE FROM guilds " +
                "WHERE guildName='" + guild.getGuildData().getName() + "' " +
                "AND description='" + guild.getGuildData().getDescription() + "' " +
                "AND power=" + guild.getGuildData().getPower() + " " +
                "AND home='" + home + "' " +
                "AND ownerUniqueId='" + guild.getGuildData().getOwnerUniqueId() + "' " +
                "AND kingdomName='" + guild.getGuildData().getKingdomName() + "' " +
                "AND guildUniqueId='" + guild.getGuildData().getGuildUniqueId() + "'");
    }

    public void updateGuildToDatabase(Guild guild) {
        if (guild.isDeleted() || guild.isNew()) {
            if (guild.isDeleted()) {
                deleteGuild(guild);
            }
            if (guild.isNew()) {
                createGuild(guild);
            }
        } else {
            final String home = guild.getGuildData().getHome() == null ? "no-home" : LocationUtils.locationToString(guild.getGuildData().getHome());
            dataManager.getDatabaseManager().update("UPDATE guilds SET " +
                    "guildName='" + guild.getGuildData().getName() + "', " +
                    "description='" + guild.getGuildData().getDescription() + "', " +
                    "home='" + home + "', " +
                    "ownerUniqueId='" + guild.getGuildData().getOwnerUniqueId() + "', " +
                    "kingdomName='" + guild.getGuildData().getKingdomName() + "', " +
                    "power=" + guild.getGuildData().getPower() + " " +
                    "WHERE guildUniqueId='" + guild.getGuildData().getGuildUniqueId() + "'");
        }
    }

    private List<UUID> getMembersUniqueId(String guildUniqueId) {
        final List<UUID> uuids = new ArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts WHERE guildUniqueId='" + guildUniqueId + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    uuids.add(UUID.fromString(resultSet.getString("uniqueId")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return uuids;
    }

    public List<Guild> getGuilds() {
        return guilds;
    }

    public Guild getGuild(String guildName) {
        return getGuilds().stream()
                .filter(Objects::nonNull)
                .filter(guild -> guild.getGuildData().getName().equals(guildName))
                .findFirst()
                .orElse(null);
    }

    public Guild getGuild(UUID uuid) {
        return getGuilds().stream()
                .filter(Objects::nonNull)
                .filter(guild -> guild.getGuildData().getMembersUniqueId().contains(uuid))
                .findFirst()
                .orElse(null);
    }
}
