package fr.yvernal.yvernalkingdom.kingdoms.guilds;

import fr.yvernal.yvernalkingdom.config.messages.MessagesManager;
import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.GuildData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

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

    public void disband(DataManager dataManager, MessagesManager messagesManager) {
        this.sendMessageToMembers(messagesManager.getString("successfully-deleted-guild"));

        this.getGuildData().getMembersUniqueId().stream()
                .map(uuid -> dataManager.getPlayerAccountManager().getPlayerAccount(uuid))
                .forEach(playerAccount -> {
                    playerAccount.setGuildName("no-guild");
                    playerAccount.setGuildRank(GuildRank.NO_GUILD);
                    playerAccount.setGuildUniqueId("no-guild");
                });

        this.setDeleted(true);
        this.setNew(false);
        this.getGuildData().getMembersUniqueId().clear();

        dataManager.getClaimManager().getGuildClaims(this).forEach(claim -> {
            claim.setUnClaim(true);
            claim.setNew(false);
        });

        dataManager.getInvitedPlayerDataManager().getInvitedPlayersByGuild(UUID.fromString(this.getGuildData().getGuildUniqueId()))
                .forEach(invitedPlayerData -> {
                    invitedPlayerData.setStillInvited(false);
                    invitedPlayerData.setNew(false);
                });
    }

    public void kickPlayer(PlayerAccount playerAccount, OfflinePlayer player, MessagesManager messagesManager) {
        this.getGuildData().getMembersUniqueId().remove(playerAccount.getUniqueId());
        playerAccount.setGuildName("no-guild");
        playerAccount.setGuildUniqueId("no-guild");
        playerAccount.setGuildRank(GuildRank.NO_GUILD);
        this.getGuildData().setPower(this.getGuildData().getPower() - playerAccount.getPower());
        this.sendMessageToMembers(messagesManager.getString("player-kicked-from-guild")
                .replace("%player%", player.getName()));
        if (player.isOnline()) {
            ((Player) player).sendMessage(messagesManager.getString("player-kicked"));
        }
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

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public String toString() {
        return "Guild{" +
                "guildData=" + guildData +
                ", isDeleted=" + isDeleted +
                ", isNew=" + isNew +
                '}';
    }
}