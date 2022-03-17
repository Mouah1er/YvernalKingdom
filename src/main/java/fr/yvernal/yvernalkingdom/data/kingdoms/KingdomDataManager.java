package fr.yvernal.yvernalkingdom.data.kingdoms;

import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;

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
        this.kingdoms = new ArrayList<>();
    }

    public KingdomData getKingdomDataFromDatabase(String kingdom) {
        return new KingdomData(getGuildsIn(kingdom), getWaitingPlayers(kingdom), getPlayersIn(kingdom));
    }

    public void makePlayerJoin(UUID uuid, String kingdomName) {
        dataManager.getPlayerAccountManager().getPlayerAccount(uuid).setKingdomName(kingdomName);
        getKingdom(kingdomName).getKingdomData().getPlayersIn().add(uuid);
    }

    public void makePlayerJoinWaitingList(UUID uuid, String kingdomName) {
        dataManager.getPlayerAccountManager().getPlayerAccount(uuid).setWaitingKingdomName(kingdomName);
        getKingdom(kingdomName).getKingdomData().getWaitingPlayers().add(uuid);
    }

    private List<Guild> getGuildsIn(String kingdomName) {
        final List<Guild> guilds = new ArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM guilds WHERE kingdomName='" + kingdomName + "'", resultSet -> {
            try {
                while (resultSet.next()) {
                    final String guildUniqueId = resultSet.getString("guildUniqueId");

                    guilds.add(dataManager.getGuildDataManager().getGuildFromDatabase(guildUniqueId));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return guilds;
    }

    private List<UUID> getWaitingPlayers(String kingdomName) {
        final List<UUID> uuids = new ArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts " +
                "WHERE kingdomName='no-kingdom' AND waitingKingdomName='" + kingdomName + "'", resultSet -> {
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

    private List<UUID> getPlayersIn(String kingdomName) {
        final List<UUID> uuids = new ArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts WHERE kingdomName='" + kingdomName + "' AND " +
                "waitingKingdomName='no-waiting-kingdom'", resultSet -> {
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

    public List<Kingdom> getKingdoms() {
        return kingdoms;
    }

    public Kingdom getKingdom(String kingdomName) {
        return getKingdoms().stream()
                .filter(Objects::nonNull)
                .filter(kingdom -> kingdom.getKingdomProperties().getName().equals(kingdomName))
                .findFirst()
                .orElse(null);
    }
}
