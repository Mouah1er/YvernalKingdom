package fr.yvernal.yvernalkingdom.data.kingdoms.guilds.invitedplayers;

import java.util.UUID;

public class InvitedPlayerData {
    private final UUID uniqueId;
    private final UUID guildUniqueId;

    public InvitedPlayerData(UUID uniqueId, UUID guildUniqueId) {
        this.uniqueId  = uniqueId;
        this.guildUniqueId = guildUniqueId;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public UUID getGuildUniqueId() {
        return guildUniqueId;
    }

    @Override
    public String toString() {
        return "InvitedPlayerData{" +
                "uniqueId=" + uniqueId +
                ", guildUniqueId=" + guildUniqueId +
                '}';
    }
}
