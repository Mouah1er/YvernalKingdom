package fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims;

public class ClaimData {
    private final String guildUniqueId;
    private String guildName;
    private final int x;
    private final int z;

    public ClaimData(String guildUniqueId, String guildName, int x, int z) {
        this.guildUniqueId = guildUniqueId;
        this.guildName = guildName;
        this.x = x;
        this.z = z;
    }

    public String getGuildUniqueId() {
        return guildUniqueId;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
