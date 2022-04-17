package fr.yvernal.yvernalkingdom.data;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccountManager;
import fr.yvernal.yvernalkingdom.data.database.DatabaseManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.GuildDataManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.KingdomDataManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims.ClaimDataManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.invitedplayers.InvitedPlayerDataManager;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdoms;

/**
 * Permet d'accéder à toutes les informations dans la base de données
 */
public class DataManager {
    private final DatabaseManager databaseManager;
    private final KingdomDataManager kingdomDataManager;
    private final GuildDataManager guildDataManager;
    private final PlayerAccountManager playerAccountManager;
    private final ClaimDataManager claimDataManager;
    private final InvitedPlayerDataManager invitedPlayerDataManager;

    public DataManager(String dbPath) {
        this.databaseManager = new DatabaseManager(dbPath);
        this.kingdomDataManager = new KingdomDataManager(this);
        this.guildDataManager = new GuildDataManager(this);
        this.playerAccountManager = new PlayerAccountManager(this);
        this.claimDataManager = new ClaimDataManager(this);
        this.invitedPlayerDataManager = new InvitedPlayerDataManager(this);
    }

    public void init() {
        this.guildDataManager.getGuilds().addAll(guildDataManager.getAllGuildsFromDatabase());
        this.playerAccountManager.getAccounts().addAll(playerAccountManager.getAllPlayerAccountsFromDatabase());
        this.claimDataManager.getClaims().addAll(claimDataManager.getClaimsFromDatabase());
        this.invitedPlayerDataManager.getInvitedPlayers().addAll(invitedPlayerDataManager.getInvitedPlayersFromDatabase());
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

    public InvitedPlayerDataManager getInvitedPlayerDataManager() {
        return invitedPlayerDataManager;
    }
}
