package fr.yvernal.yvernalkingdom.data.kingdoms.guilds.invitedplayers;

import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;

import java.util.UUID;

public class InvitedPlayerData {
    private final UUID uniqueId;
    private final Guild guild;

    public InvitedPlayerData(UUID uniqueId, Guild guild) {
        this.uniqueId  = uniqueId;
        this.guild = guild;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Guild getGuild() {
        return guild;
    }
}
