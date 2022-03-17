package fr.yvernal.yvernalkingdom.commands.args;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.messages.MessagesManager;
import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import org.bukkit.entity.Player;

public class DisbandArg implements YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final DataManager dataManager = Main.getInstance().getDataManager();
        final MessagesManager messagesManager = Main.getInstance().getConfigManager().getMessagesManager();
        final Guild playerGuild = dataManager.getGuildDataManager().getGuild(player.getUniqueId());
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

        if (playerAccount.getGuildRank() == GuildRank.NO_GUILD && (playerGuild == null || playerGuild.isDeleted())) {
            player.sendMessage(messagesManager.getString("player-not-in-guild"));
        } else {
            if (playerAccount.getGuildRank() != GuildRank.MASTER) {
                player.sendMessage("player-guild-permission-error");
            } else {
                playerGuild.setDeleted(true);
                playerGuild.setNew(false);
                dataManager.getKingdomDataManager().getKingdom(playerAccount.getKingdomName()).getKingdomData().getGuildsIn()
                        .stream()
                        .filter(guild -> guild.getGuildData().getGuildUniqueId().equals(playerGuild.getGuildData().getGuildUniqueId()))
                        .forEach(guild -> {
                            guild.setDeleted(true);
                            guild.setNew(true);
                        });
                playerAccount.setGuildRank(GuildRank.NO_GUILD);
                playerAccount.setGuildName("no-guild");
                playerAccount.setGuildUniqueId("no-guild");
                dataManager.getClaimManager().getClaims(playerGuild).forEach(claim -> {
                    claim.setUnClaim(true);
                    claim.setNew(false);
                });

                player.sendMessage(messagesManager.getString("successfully-deleted-guild"));
            }
        }
    }
}
