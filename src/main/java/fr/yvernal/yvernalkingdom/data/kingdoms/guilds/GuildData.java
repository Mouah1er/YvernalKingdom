package fr.yvernal.yvernalkingdom.data.kingdoms.guilds;

import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.invitedplayers.InvitedPlayer;
import fr.yvernal.yvernalkingdom.utils.list.YvernalArrayList;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Représente les données d'une guild
 */
public class GuildData {
    private final String guildUniqueId;
    private final Kingdom kingdom;
    private final List<UUID> membersUniqueId;
    private final List<Claim> claims;
    private final List<InvitedPlayer> invitedPlayers;
    private String name;
    private String description;
    private int power;
    private Location home;
    private UUID ownerUniqueId;

    public GuildData(String guildUniqueId, Kingdom kingdom, String name, String description, int power, Location home, UUID ownerUniqueId,
                     List<UUID> membersUniqueId, List<Claim> claims, List<InvitedPlayer> invitedPlayers) {
        this.guildUniqueId = guildUniqueId;
        this.kingdom = kingdom;
        this.name = name;
        this.description = description;
        this.power = power;
        this.home = home;
        this.ownerUniqueId = ownerUniqueId;
        // au cas où la list est immutable on crée une nouvelle ArrayList
        this.membersUniqueId = new YvernalArrayList<>(membersUniqueId);
        this.claims = new YvernalArrayList<>(claims);
        this.invitedPlayers = invitedPlayers;
    }

    public String getGuildUniqueId() {
        return guildUniqueId;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Location getHome() {
        return home;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    public UUID getOwnerUniqueId() {
        return ownerUniqueId;
    }

    public void setOwnerUniqueId(UUID ownerUniqueId) {
        this.ownerUniqueId = ownerUniqueId;
    }

    public List<UUID> getMembersUniqueId() {
        return membersUniqueId;
    }

    public List<Claim> getClaims() {
        return claims;
    }

    public List<InvitedPlayer> getInvitedPlayers() {
        return invitedPlayers;
    }

    @Override
    public String toString() {
        return "GuildData{" +
                "guildUniqueId='" + guildUniqueId + '\'' +
                ", kingdom='" + kingdom + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", power=" + power +
                ", home=" + home +
                ", ownerUniqueId=" + ownerUniqueId +
                ", membersUniqueId=" + membersUniqueId +
                '}';
    }
}
