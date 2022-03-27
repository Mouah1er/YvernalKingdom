package fr.yvernal.yvernalkingdom.data.accounts;

import com.google.common.util.concurrent.AtomicDouble;
import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Permet d'accéder aux informations relatives à un joueur
 */
public class PlayerAccountManager {
    private final DataManager dataManager;
    private final List<PlayerAccount> accounts;

    public PlayerAccountManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.accounts = new ArrayList<>();
    }

    public PlayerAccount getPlayerAccountFromDatabase(UUID uuid) {
        final int power = getPower(uuid);
        final double valis = getValis(uuid);
        final String guild = getGuildName(uuid);
        final String guildUniqueId = getGuildUniqueId(uuid);
        final GuildRank guildRank = getGuildRank(uuid);
        final String waitingKingdomName = getWaitingKingdomName(uuid);
        final String kingdomName = getKingdomName(uuid);
        final long kills = getKills(uuid);
        final long deaths = getDeaths(uuid);

        if (guild == null || guildRank == null || waitingKingdomName == null || kingdomName == null) {
            return null;
        }

        return new PlayerAccount(uuid, power, valis, guild, guildUniqueId, guildRank, waitingKingdomName, kingdomName, kills, deaths);
    }

    public void updatePlayerAccountToDatabase(PlayerAccount playerAccount) {
        dataManager.getDatabaseManager().update("UPDATE accounts SET " +
                "power='" + playerAccount.getPower() + "', " +
                "valis='" + playerAccount.getValis() + "', " +
                "guildName='" + playerAccount.getGuildName() + "', " +
                "guildUniqueId='" + playerAccount.getGuildUniqueId() + "', " +
                "guildRank='" + playerAccount.getGuildRank().getName() + "', " +
                "waitingKingdomName='" + playerAccount.getWaitingKingdomName() + "', " +
                "kingdomName='" + playerAccount.getKingdomName() + "', " +
                "kills='" + playerAccount.getKills() + "', " +
                "deaths='" + playerAccount.getDeaths() + "' " +
                "WHERE uniqueId='" + playerAccount.getUniqueId() + "'");
    }

    public PlayerAccount createPlayerAccount(UUID uuid) {
        final PlayerAccount playerAccountFromDatabase = getPlayerAccountFromDatabase(uuid);
        if (playerAccountFromDatabase != null) {
            if (playerAccountFromDatabase.getUniqueId().equals(uuid)) {
                if (!accounts.contains(playerAccountFromDatabase)) {
                    accounts.add(playerAccountFromDatabase);
                }
                return playerAccountFromDatabase;
            }
        }

        final PlayerAccount playerAccount = new PlayerAccount(uuid, 0, 0, "no-guild", "no-guild",
                GuildRank.NO_GUILD, "no-waiting-kingdom", "no-kingdom", 0, 0);
        dataManager.getDatabaseManager().update(
                "INSERT INTO accounts (uniqueId, power, valis, guildName, guildUniqueId, guildRank, waitingKingdomName, kingdomName," +
                        "kills, deaths)" +
                        " VALUES (" +
                        "'" + uuid + "', " +
                        "'" + playerAccount.getPower() + "', " +
                        "'" + playerAccount.getValis() + "', " +
                        "'" + playerAccount.getGuildName() + "', " +
                        "'" + playerAccount.getGuildUniqueId() + "', " +
                        "'" + playerAccount.getGuildRank().getName() + "', " +
                        "'" + playerAccount.getWaitingKingdomName() + "', " +
                        "'" + playerAccount.getKingdomName() + "', " +
                        "'" + playerAccount.getKills() + "', " +
                        "'" + playerAccount.getDeaths() + "')");

        accounts.add(playerAccount);

        return playerAccount;
    }

    private int getPower(UUID uuid) {
        final AtomicInteger power = new AtomicInteger();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts WHERE uniqueId='" + uuid + "'", resultSet -> {
            try {
                if (resultSet.next()) power.set(resultSet.getInt("power"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return power.get();    }

    private double getValis(UUID uuid) {
        final AtomicDouble valis = new AtomicDouble();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts WHERE uniqueId='" + uuid + "'", resultSet -> {
            try {
                if (resultSet.next()) valis.set(resultSet.getDouble("valis"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return valis.get();
    }

    private String getGuildName(UUID uuid) {
        final AtomicReference<String> guild = new AtomicReference<>();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts WHERE uniqueId='" + uuid + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    guild.set(resultSet.getString("guildName"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return guild.get();
    }

    private String getGuildUniqueId(UUID uuid) {
        final AtomicReference<String> guildUniqueId = new AtomicReference<>();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts WHERE uniqueId='" + uuid + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    guildUniqueId.set(resultSet.getString("guildUniqueId"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return guildUniqueId.get();
    }

    private GuildRank getGuildRank(UUID uuid) {
        final AtomicReference<GuildRank> guildRank = new AtomicReference<>();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts WHERE uniqueId='" + uuid + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    guildRank.set(GuildRank.getByName(resultSet.getString("guildRank")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return guildRank.get();
    }

    private String getWaitingKingdomName(UUID uuid) {
        final AtomicReference<String> waitingKingdomName = new AtomicReference<>();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts WHERE uniqueId='" + uuid + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    waitingKingdomName.set(resultSet.getString("waitingKingdomName"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return waitingKingdomName.get();
    }

    private String getKingdomName(UUID uuid) {
        final AtomicReference<String> kingdomName = new AtomicReference<>();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts WHERE uniqueId='" + uuid + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    kingdomName.set(resultSet.getString("kingdomName"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return kingdomName.get();
    }

    private long getKills(UUID uuid) {
        final AtomicLong kills = new AtomicLong();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts WHERE uniqueId='" + uuid + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    kills.set(resultSet.getLong("kills"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return kills.get();
    }

    private long getDeaths(UUID uuid) {
        final AtomicLong deaths = new AtomicLong();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts WHERE uniqueId='" + uuid + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    deaths.set(resultSet.getLong("deaths"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return deaths.get();
    }

    public List<PlayerAccount> getAccounts() {
        return accounts;
    }

    public PlayerAccount getPlayerAccount(UUID uuid) {
        return getAccounts().stream()
                .filter(Objects::nonNull)
                .filter(playerAccount -> playerAccount.getUniqueId().equals(uuid))
                .findFirst()
                .orElse(null);
    }
}
