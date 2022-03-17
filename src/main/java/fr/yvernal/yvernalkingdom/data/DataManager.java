package fr.yvernal.yvernalkingdom.data;

import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccountManager;
import fr.yvernal.yvernalkingdom.data.database.DatabaseManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.GuildDataManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.KingdomDataManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims.ClaimDataManager;

/**
 * Permet d'accéder à toutes les informations dans la base de données
 */
public class DataManager {
    private final DatabaseManager databaseManager;
    private final KingdomDataManager kingdomDataManager;
    private final GuildDataManager guildDataManager;
    private final PlayerAccountManager playerAccountManager;
    private final ClaimDataManager claimDataManager;

    public DataManager(String dbPath) {
        this.databaseManager = new DatabaseManager(dbPath);
        this.kingdomDataManager = new KingdomDataManager(this);
        this.guildDataManager = new GuildDataManager(this);
        this.playerAccountManager = new PlayerAccountManager(this);
        this.claimDataManager = new ClaimDataManager(this);
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public KingdomDataManager getKingdomDataManager() {
        return kingdomDataManager;
    }

    public GuildDataManager getGuildDataManager() {
        return guildDataManager;
    }

    public PlayerAccountManager getPlayerAccountManager() {
        return playerAccountManager;
    }

    public ClaimDataManager getClaimManager() {
        return claimDataManager;
    }
}
