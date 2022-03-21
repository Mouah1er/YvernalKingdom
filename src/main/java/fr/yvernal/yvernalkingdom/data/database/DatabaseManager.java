package fr.yvernal.yvernalkingdom.data.database;

import java.io.File;
import java.io.IOException;
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
     * @param dbPath le chemin vers le fichier de la base de données
     */
    public DatabaseManager(String dbPath) {
        final File file = new File(dbPath);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                "guildName VARCHAR(255), " +
                "guildUniqueId VARCHAR(37), " +
                "guildRank VARCHAR(255), " +
                "waitingKingdomName VARCHAR(255), " +
                "kingdomName VARCHAR(255)" +
                ")");

        update("CREATE TABLE IF NOT EXISTS guilds(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildUniqueId VARCHAR(37), " +
                "guildName VARCHAR(17), " +
                "description VARCHAR(255), " +
                "power INT, " +
                "home VARCHAR(50), " +
                "ownerUniqueId VARCHAR(37), " +
                "kingdomName VARCHAR(255)" +
                ")");

        update("CREATE TABLE IF NOT EXISTS claims(" +
                "x BIGINT, " +
                "z BIGINT, " +
                "guildName VARCHAR(16), " +
                "guildUniqueId VARCHAR(37))");
    }

    public void update(String query) {
        connect();

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void query(String query, Consumer<ResultSet> consumer) {
        connect();

        try (final Connection connection = getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query);
             final ResultSet resultSet = preparedStatement.executeQuery()) {
            consumer.accept(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
