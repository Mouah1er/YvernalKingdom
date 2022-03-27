package fr.yvernal.yvernalkingdom.commands.guild.args.two;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class KickPlayerArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final Guild playerGuild = dataManager.getGuildDataManager().getGuildByPlayer(player.getUniqueId());
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

        if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
            if (playerAccount.getGuildRank().getPower() < 2) {
                player.sendMessage(messagesManager.getString("player-guild-permission-error"));
            } else {
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
                            if (playerAccount.getGuildRank().getPower() <= targetPlayerAccount.getGuildRank().getPower()) {
                                player.sendMessage(messagesManager.getString("cannot-kick")
                                        .replace("%player%", targetPlayer.getName()));
                            } else {
                                playerGuild.getGuildData().getMembersUniqueId().remove(targetPlayer.getUniqueId());
                                targetPlayerAccount.setGuildName("no-guild");
                                targetPlayerAccount.setGuildUniqueId("no-guild");
                                targetPlayerAccount.setGuildRank(GuildRank.NO_GUILD);
                                playerGuild.getGuildData().setPower(targetPlayerGuild.getGuildData().getPower() - targetPlayerAccount
                                        .getPower());
                                playerGuild.sendMessageToMembers(messagesManager.getString("player-kicked-from-guild")
                                        .replace("%player%", player.getName()));
                                if (targetPlayer.isOnline()) {
                                    ((Player) targetPlayer).sendMessage(messagesManager.getString("player-kicked"));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
