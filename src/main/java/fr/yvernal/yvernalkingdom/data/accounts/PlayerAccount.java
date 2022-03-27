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
    private long kills;
    private long deaths;

    private int powerTaskId;
    private boolean isWaitingToTeleportToHome;
    private boolean isNew;

    public PlayerAccount(UUID uniqueId, int power, double valis, String guildName, String guildUniqueId, GuildRank guildRank,
                         String waitingKingdomName, String kingdomName, long kills, long deaths, boolean isNew) {
        this.uniqueId = uniqueId;
        this.power = power;
        this.valis = valis;
        this.guildName = guildName;
        this.guildUniqueId = guildUniqueId;
        this.guildRank = guildRank;
        this.waitingKingdomName = waitingKingdomName;
        this.kingdomName = kingdomName;
        this.kills = kills;
        this.deaths = deaths;
        this.isNew = isNew;
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

    public void setValis(double valis) {
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

    public long getKills() {
        return kills;
    }

    public void setKills(long kills) {
        this.kills = kills;
    }

    public long getDeaths() {
        return deaths;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public int getPowerTaskId() {
        return powerTaskId;
    }

    public void setPowerTaskId(int powerTaskId) {
        this.powerTaskId = powerTaskId;
    }

    public boolean isWaitingToTeleportToHome() {
        return isWaitingToTeleportToHome;
    }

    public void setWaitingToTeleportToHome(boolean waitingToTeleportToHome) {
        isWaitingToTeleportToHome = waitingToTeleportToHome;
    }

    @Override
    public String toString() {
        return "PlayerAccount{" +
                "uniqueId=" + uniqueId +
                ", power=" + power +
                ", valis=" + valis +
                ", guildName='" + guildName + '\'' +
                ", guildUniqueId='" + guildUniqueId + '\'' +
                ", guildRank=" + guildRank +
                ", waitingKingdomName='" + waitingKingdomName + '\'' +
                ", kingdomName='" + kingdomName + '\'' +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", powerTaskId=" + powerTaskId +
                '}';
    }
}
