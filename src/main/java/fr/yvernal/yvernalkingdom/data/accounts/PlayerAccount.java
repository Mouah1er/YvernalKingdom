package fr.yvernal.yvernalkingdom.data.accounts;

import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.Relation;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import fr.yvernal.yvernalkingdom.tasks.PowerAdditionsBukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Représente les données d'un joueur
 */
public class PlayerAccount {
    private final UUID uniqueId;
    private int power;
    private double valis;
    private Guild guild;
    private GuildRank guildRank;
    private Kingdom waitingKingdom;
    private Kingdom kingdom;
    private long kills;
    private long deaths;

    private PowerAdditionsBukkitRunnable powerRunnable;
    private boolean isWaitingToTeleportToHome;
    private boolean isNew;
    private boolean isInAdminMode;
    private final List<UUID> playerWithWhomInBattle = new ArrayList<>();

    public PlayerAccount(UUID uniqueId, int power, double valis, Guild guild, GuildRank guildRank,
                         Kingdom waitingKingdom, Kingdom kingdom, long kills, long deaths, boolean isNew) {
        this.uniqueId = uniqueId;
        this.power = power;
        this.valis = valis;
        this.guild = guild;
        this.guildRank = guildRank;
        this.waitingKingdom = waitingKingdom;
        this.kingdom = kingdom;
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

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public GuildRank getGuildRank() {
        return guildRank;
    }

    public void setGuildRank(GuildRank guildRank) {
        this.guildRank = guildRank;
    }

    public Kingdom getWaitingKingdom() {
        return waitingKingdom;
    }

    public void setWaitingKingdom(Kingdom waitingKingdom) {
        this.waitingKingdom = waitingKingdom;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
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

    public PowerAdditionsBukkitRunnable getPowerRunnable() {
        return powerRunnable;
    }

    public void setPowerRunnable(PowerAdditionsBukkitRunnable powerRunnable) {
        this.powerRunnable = powerRunnable;
    }

    public boolean isWaitingToTeleportToHome() {
        return isWaitingToTeleportToHome;
    }

    public void setWaitingToTeleportToHome(boolean waitingToTeleportToHome) {
        isWaitingToTeleportToHome = waitingToTeleportToHome;
    }

    public boolean isInAdminMode() {
        return isInAdminMode;
    }

    public void setInAdminMode(boolean isInAdminMode) {
        this.isInAdminMode = isInAdminMode;
    }

    public List<UUID> getPlayerWithWhomInBattle() {
        return playerWithWhomInBattle;
    }

    public Relation getRelationWith(PlayerAccount playerAccount) {
        if (playerAccount.getKingdom() == null || kingdom == null) {
            return Relation.NEUTRAL;
        } else if (playerAccount.getUniqueId().equals(this.uniqueId) ||
                playerAccount.getKingdom().getKingdomProperties().getNumber().equals(this.kingdom.getKingdomProperties().getNumber())) {
            return Relation.ALLY;
        } else {
            return Relation.ENEMY;
        }
    }

    @Override
    public String toString() {
        return "PlayerAccount{" +
                "uniqueId=" + uniqueId +
                ", power=" + power +
                ", valis=" + valis +
                ", guild=" + guild +
                ", guildRank=" + guildRank +
                ", waitingKingdom=" + waitingKingdom +
                ", kingdom=" + kingdom +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", powerRunnable=" + powerRunnable +
                ", isWaitingToTeleportToHome=" + isWaitingToTeleportToHome +
                ", isNew=" + isNew +
                ", isInAdminMode=" + isInAdminMode +
                ", playerWithWhomInBattle=" + playerWithWhomInBattle +
                '}';
    }
}