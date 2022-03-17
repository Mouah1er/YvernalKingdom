package fr.yvernal.yvernalkingdom.data.kingdoms.guilds;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Représente les données d'une guild
 */
public class GuildData {
    private final String guildUniqueId;
    private final String kingdomName;
    private String name;
    private String description;
    private int power;
    private Location home;
    private UUID ownerUniqueId;
    private final List<UUID> membersUniqueId;

    public GuildData(String guildUniqueId, String kingdomName, String name, String description, int power, Location home, UUID ownerUniqueId,
                     List<UUID> membersUniqueId) {
        this.guildUniqueId = guildUniqueId;
        this.kingdomName = kingdomName;
        this.name = name;
        this.description = description;
        this.power = power;
        this.home = home;
        this.ownerUniqueId = ownerUniqueId;
        // au cas où la list est immutable on crée une nouvelle ArrayList
        this.membersUniqueId = new ArrayList<>(membersUniqueId);
    }

    public String getGuildUniqueId() {
        return guildUniqueId;
    }

    public String getKingdomName() {
        return kingdomName;
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
}
