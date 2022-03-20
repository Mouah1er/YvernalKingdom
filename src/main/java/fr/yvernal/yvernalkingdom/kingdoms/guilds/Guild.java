package fr.yvernal.yvernalkingdom.kingdoms.guilds;

import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.GuildData;
import org.bukkit.Bukkit;

import java.util.Objects;

public class Guild {
    private final GuildData guildData;
    private boolean isDeleted;
    private boolean isNew;

    /**
     * Représente une guild
     * @param guildData les data de la guild stockées en base de données
     * @param isDeleted si la guild est supprimée et doit l'être dans la base de données
     * @param isNew si la guild est nouvelle et doit l'être dans la base de données
     */
    public Guild(GuildData guildData, boolean isDeleted, boolean isNew) {
        this.guildData = guildData;
        this.isDeleted = isDeleted;
        this.isNew = isNew;
    }

    public void sendMessageToMembers(String message) {
        getGuildData().getMembersUniqueId()
                .stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player1 -> player1.sendMessage(message));
    }

    public GuildData getGuildData() {
        return guildData;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}