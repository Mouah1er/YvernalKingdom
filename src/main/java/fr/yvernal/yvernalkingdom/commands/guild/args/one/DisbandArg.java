package fr.yvernal.yvernalkingdom.commands.guild.args.one;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import org.bukkit.entity.Player;

public class DisbandArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final Guild playerGuild = dataManager.getGuildDataManager().getGuildByPlayer(player.getUniqueId());
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

        if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
            if (guildRankIsMasterWithMessage(player, playerAccount)) {
                playerGuild.setDeleted(true);
                playerGuild.setNew(false);
                dataManager.getKingdomDataManager().getKingdomByNumber(playerAccount.getKingdomName()).getKingdomData().getGuildsIn()
                        .stream()
                        .filter(guild -> guild.getGuildData().getGuildUniqueId().equals(playerGuild.getGuildData().getGuildUniqueId()))
                        .forEach(guild -> {
                            guild.setDeleted(true);
                            guild.setNew(false);
                        });
                playerAccount.setGuildRank(GuildRank.NO_GUILD);
                playerAccount.setGuildName("no-guild");
                playerAccount.setGuildUniqueId("no-guild");
                dataManager.getClaimManager().getClaims(playerGuild).forEach(claim -> {
                    claim.setUnClaim(true);
                    claim.setNew(false);
                });

                playerGuild.sendMessageToMembers(messagesManager.getString("successfully-deleted-guild"));
            }
        }
    }
}
