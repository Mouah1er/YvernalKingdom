package fr.yvernal.yvernalkingdom.data.database;

import fr.yvernal.yvernalkingdom.utils.ConfigUtils;

import java.io.File;
import java.sql.*;
import java.util.function.Consumer;

/**
 * Class contenant les méthodes permettant de se connecter et de faire des query à la base de données
 */
public class DatabaseManager {
    private final String dbPath;

    private Connection connection;

    /**
     * Constructeur appelé à chaque fois que le serveur se lance
     *
     * @param dbPath le chemin vers le fichier de la base de données
     */
    public DatabaseManager(String dbPath) {
        final File file = new File(dbPath);

        ConfigUtils.createFileIfNotExists(file);

        this.dbPath = dbPath;

        createTables();
    }

    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        update("CREATE TABLE IF NOT EXISTS accounts(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "uniqueId VARCHAR(37), " +
                "power INT, " +
                "valis DOUBLE, " +
                "guildUniqueId VARCHAR(37), " +
                "guildRank VARCHAR(255), " +
                "waitingKingdomName VARCHAR(255), " +
                "kingdomName VARCHAR(255), " +
                "kills BIGINT, " +
                "deaths BIGINT" +
                ")");

        update("CREATE TABLE IF NOT EXISTS guilds(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildUniqueId VARCHAR(37), " +
                "guildName VARCHAR(17), " +
                "description VARCHAR(255), " +
                "power INT, " +
                "home VARCHAR(255), " +
                "ownerUniqueId VARCHAR(37), " +
                "kingdomName VARCHAR(255)" +
                ")");

        update("CREATE TABLE IF NOT EXISTS claims(" +
                "x BIGINT, " +
                "z BIGINT, " +
                "guildUniqueId VARCHAR(37))");

        update("CREATE TABLE IF NOT EXISTS invitedPlayers(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildUniqueId VARCHAR(37), " +
                "uniqueId VARCHAR(37))");
    }

    @SafeVarargs
    public final <T> void update(String query, T... values) {
        connect();

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    preparedStatement.setObject(i + 1, values[i]);
                }
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SafeVarargs
    public final <T> void query(String query, Consumer<ResultSet> consumer, T... values) {
        connect();

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (values.length > 0) {
                for (int i = 0; i < values.length; i++) {
                    preparedStatement.setObject(i + 1, values[i]);
                }
            }

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                consumer.accept(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
