package fr.yvernal.yvernalkingdom.data.accounts;

import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;

import java.util.UUID;

/**
 * Représente les données d'un joueur
 */
public class PlayerAccount {
    private final UUID uniqueId;
    private int power;
    private double valis;
    private String guildName;
    private String guildUniqueId;
    private GuildRank guildRank;
    private String waitingKingdomName;
    private String kingdomName;

    public PlayerAccount(UUID uniqueId, int power, double valis, String guildName, String guildUniqueId, GuildRank guildRank,
                         String waitingKingdomName, String kingdomName) {
        this.uniqueId = uniqueId;
        this.power = power;
        this.valis = valis;
        this.guildName = guildName;
        this.guildUniqueId = guildUniqueId;
        this.guildRank = guildRank;
        this.waitingKingdomName = waitingKingdomName;
        this.kingdomName = kingdomName;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public double getValis() {
        return valis;
    }

    public void setValis(int valis) {
        this.valis = valis;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public String getGuildUniqueId() {
        return guildUniqueId;
    }

    public void setGuildUniqueId(String guildUniqueId) {
        this.guildUniqueId = guildUniqueId;
    }

    public GuildRank getGuildRank() {
        return guildRank;
    }

    public void setGuildRank(GuildRank guildRank) {
        this.guildRank = guildRank;
    }

    public String getWaitingKingdomName() {
        return waitingKingdomName;
    }

    public void setWaitingKingdomName(String waitingKingdomName) {
        this.waitingKingdomName = waitingKingdomName;
    }

    public String getKingdomName() {
        return kingdomName;
    }

    public void setKingdomName(String kingdomName) {
        this.kingdomName = kingdomName;
    }
}
