package fr.yvernal.yvernalkingdom.data.kingdoms;

import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.utils.list.YvernalArrayList;
import org.bukkit.Location;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Permet d'accéder à toutes les informations relatives à un royaume dans la base de données
 */
public class KingdomDataManager {
    private final DataManager dataManager;
    private final List<Kingdom> kingdoms;

    public KingdomDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.kingdoms = new YvernalArrayList<>();
    }

    public KingdomData getKingdomDataFromDatabase(String kingdom) {
        return new KingdomData(getGuildsIn(kingdom), getWaitingPlayers(kingdom), getPlayersIn(kingdom));
    }

    public void makePlayerJoin(UUID uuid, Kingdom kingdom) {
        dataManager.getPlayerAccountManager().getPlayerAccount(uuid).setKingdom(kingdom);
        kingdom.getKingdomData().getPlayersIn().add(uuid);
    }

    public void makePlayerJoinWaitingList(UUID uuid, Kingdom kingdom) {
        dataManager.getPlayerAccountManager().getPlayerAccount(uuid).setWaitingKingdom(kingdom);
        kingdom.getKingdomData().getWaitingPlayers().add(uuid);
    }

    private List<Guild> getGuildsIn(String kingdomName) {
        final List<Guild> guilds = new YvernalArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM guilds WHERE kingdomName=?", resultSet -> {
            try {
                while (resultSet.next()) {
                    final String guildUniqueId = resultSet.getString("guildUniqueId");

                    guilds.add(dataManager.getGuildDataManager().getFromDatabase(guildUniqueId));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, kingdomName);

        return guilds;
    }

    private List<UUID> getWaitingPlayers(String kingdomName) {
        final List<UUID> uuids = new YvernalArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts " +
                "WHERE kingdomName=? AND waitingKingdomName=?", resultSet -> {
            try {
                while (resultSet.next()) {
                    uuids.add(UUID.fromString(resultSet.getString("uniqueId")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, "no-kingdom", kingdomName);

        return uuids;
    }

    private List<UUID> getPlayersIn(String kingdomName) {
        final List<UUID> uuids = new YvernalArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts WHERE kingdomName=? AND " +
                "waitingKingdomName=?", resultSet -> {
            try {
                while (resultSet.next()) {
                    uuids.add(UUID.fromString(resultSet.getString("uniqueId")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, kingdomName, "no-waiting-kingdom");

        return uuids;
    }

    public List<Kingdom> getKingdoms() {
        return kingdoms;
    }

    public Kingdom getKingdomByName(String kingdomName) {
        return getKingdoms().stream()
                .filter(Objects::nonNull)
                .filter(kingdom -> kingdom.getKingdomProperties().getName().equals(kingdomName))
                .findFirst()
                .orElse(null);
    }

    public Kingdom getKingdomByNumber(String kingdomNumber) {
        return getKingdoms().stream()
                .filter(Objects::nonNull)
                .filter(kingdom -> kingdom.getKingdomProperties().getNumber().equals(kingdomNumber))
                .findFirst()
                .orElse(null);
    }

    public Kingdom getKingdomByUniqueId(UUID playerUniqueId) {
        return getKingdoms().stream()
                .filter(Objects::nonNull)
                .filter(kingdom -> kingdom.getKingdomData().getPlayersIn().contains(playerUniqueId))
                .findFirst()
                .orElse(null);
    }

    public Kingdom getKingdomByLocation(Location location) {
        return getKingdoms().stream()
                .filter(Objects::nonNull)
                .filter(kingdom -> kingdom.getKingdomProperties().getTotalTerritoryCuboid().isIn(location))
                .findFirst()
                .orElse(null);
    }
}
