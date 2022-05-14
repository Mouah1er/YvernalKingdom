package fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims;

import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;

public class ClaimData {
    private final int x;
    private final int z;
    private Guild guild;

    public ClaimData(Guild guild, int x, int z) {
        this.guild = guild;
        this.x = x;
        this.z = z;
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
