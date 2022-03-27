package fr.yvernal.yvernalkingdom.commands.guild.args.two;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class LeadPlayerArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final Guild playerGuild = dataManager.getGuildDataManager().getGuildByPlayer(player.getUniqueId());
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

        if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
            if (guildRankIsMasterWithMessage(player, playerAccount)) {
                final OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[1]);

                if (targetPlayer == null || !targetPlayer.hasPlayedBefore()) {
                    player.sendMessage(messagesManager.getString("player-not-found")
                            .replace("%player%", args[1]));
                } else {
                    final PlayerAccount targetPlayerAccount = dataManager.getPlayerAccountManager()
                            .getPlayerAccount(targetPlayer.getUniqueId());
                    final Guild targetPlayerGuild = dataManager.getGuildDataManager().getGuildByPlayer(targetPlayer.getUniqueId());

                    if (playerIsInGuildWithMessage(player, targetPlayerGuild, targetPlayerAccount)) {
                        if (!playerGuild.getGuildData().getGuildUniqueId().equals(targetPlayerGuild.getGuildData().getGuildUniqueId())) {
                            player.sendMessage(messagesManager.getString("player-not-in-your-guild")
                                    .replace("%player%", targetPlayer.getName()));
                        } else {
                            targetPlayerAccount.setGuildRank(GuildRank.MASTER);
                            playerAccount.setGuildRank(GuildRank.OFFICER);
                            playerGuild.getGuildData().setOwnerUniqueId(targetPlayer.getUniqueId());
                            playerGuild.sendMessageToMembers(messagesManager.getString("guild-new-leader")
                                    .replace("%player%", targetPlayer.getName()));
                        }
                    }
                }
            }
        }
    }
}
