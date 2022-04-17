package fr.yvernal.yvernalkingdom.data.accounts;

import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdoms;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    public List<PlayerAccount> getAllPlayerAccountsFromDatabase() {
        final List<PlayerAccount> playerAccounts = new ArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM accounts", resultSet -> {
            try {
                while (resultSet.next()) {
                    final UUID uniqueId = UUID.fromString(resultSet.getString("uniqueId"));
                    final int power = resultSet.getInt("power");
                    final double valis = resultSet.getDouble("valis");
                    Guild guild = null;

                    try {
                        guild = dataManager.getGuildDataManager().getGuildByUniqueId(UUID.fromString(resultSet.getString("guildUniqueId")));
                    } catch (IllegalArgumentException ignored) {
                    }

                    final GuildRank guildRank = GuildRank.getByName(resultSet.getString("guildRank"));
                    final Kingdom waitingKingdom = Kingdoms.getByNumber(resultSet.getString("waitingKingdomName"));
                    final Kingdom kingdom = Kingdoms.getByNumber(resultSet.getString("kingdomName"));
                    final long kills = resultSet.getLong("kills");
                    final long deaths = resultSet.getLong("deaths");

                    playerAccounts.add(new PlayerAccount(uniqueId, power, valis, guild, guildRank, waitingKingdom,
                            kingdom, kills, deaths, false));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return playerAccounts;
    }

    public void createPlayerAccount(PlayerAccount playerAccount) {
        dataManager.getDatabaseManager().update(
                "INSERT INTO accounts (uniqueId, power, valis, guildUniqueId, guildRank, waitingKingdomName, kingdomName," +
                        "kills, deaths)" +
                        " VALUES (" +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?)",
                playerAccount.getUniqueId(),
                playerAccount.getPower(),
                playerAccount.getValis(),
                playerAccount.getGuild() != null ? playerAccount.getGuild().getGuildData().getGuildUniqueId() : "no-guild",
                playerAccount.getGuildRank().getName(),
                playerAccount.getWaitingKingdom() != null ? playerAccount.getWaitingKingdom().getKingdomProperties().getNumber() : "no-waiting-kingdom",
                playerAccount.getKingdom() != null ? playerAccount.getKingdom().getKingdomProperties().getNumber() : "no-kingdom",
                playerAccount.getKills(),
                playerAccount.getDeaths());

    }

    public void updatePlayerAccountToDatabase(PlayerAccount playerAccount) {
        if (playerAccount.isNew()) {
            createPlayerAccount(playerAccount);
        } else {
            dataManager.getDatabaseManager().update("UPDATE accounts SET " +
                            "power=?, " +
                            "valis=?, " +
                            "guildUniqueId=?, " +
                            "guildRank=?, " +
                            "waitingKingdomName=?, " +
                            "kingdomName=?, " +
                            "kills=?, " +
                            "deaths=? " +
                            "WHERE uniqueId=?",
                    playerAccount.getPower(),
                    playerAccount.getValis(),
                    playerAccount.getGuild() != null ? playerAccount.getGuild().getGuildData().getGuildUniqueId() : "no-guild",
                    playerAccount.getGuildRank().getName(),
                    playerAccount.getWaitingKingdom() != null ? playerAccount.getWaitingKingdom().getKingdomProperties().getNumber() : "no-waiting-kingdom",
                    playerAccount.getKingdom() != null ? playerAccount.getKingdom().getKingdomProperties().getNumber() : "no-kingdom",
                    playerAccount.getKills(),
                    playerAccount.getDeaths(),
                    playerAccount.getUniqueId());
        }
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
