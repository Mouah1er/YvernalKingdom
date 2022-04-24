package fr.yvernal.yvernalkingdom.data;

import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccountManager;
import fr.yvernal.yvernalkingdom.data.database.DatabaseManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.crystal.CrystalDataManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.GuildDataManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.KingdomDataManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims.ClaimDataManager;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.invitedplayers.InvitedPlayerDataManager;

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
    private final CrystalDataManager crystalDataManager;

    public DataManager(String dbPath) {
        this.databaseManager = new DatabaseManager(dbPath);
        this.kingdomDataManager = new KingdomDataManager(this);
        this.guildDataManager = new GuildDataManager(this);
        this.playerAccountManager = new PlayerAccountManager(this);
        this.claimDataManager = new ClaimDataManager(this);
        this.invitedPlayerDataManager = new InvitedPlayerDataManager(this);
        this.crystalDataManager = new CrystalDataManager(this);
    }

    public void init() {
        this.guildDataManager.getGuilds().addAll(guildDataManager.getAllFromDatabase());
        this.playerAccountManager.getAccounts().addAll(playerAccountManager.getAllFromDatabase());
        this.claimDataManager.getClaims().addAll(claimDataManager.getAllFromDatabase());
        this.invitedPlayerDataManager.getInvitedPlayers().addAll(invitedPlayerDataManager.getAllFromDatabase());
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

    public CrystalDataManager getCrystalDataManager() {
        return crystalDataManager;
    }
}
