package fr.yvernal.yvernalkingdom.commands.guild.args.two;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.invitedplayers.InvitedPlayer;
import org.bukkit.entity.Player;

public class JoinArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());
        final Guild playerGuild = playerAccount.getGuild();

        if (playerIsInGuild(playerGuild, playerAccount)) {
            player.sendMessage(messagesManager.getString("already-in-guild"));
        } else {
            final InvitedPlayer invitedPlayer = dataManager.getInvitedPlayerDataManager().getInvitedPlayerByUniqueId(player.getUniqueId());

            if (invitedPlayer == null || !invitedPlayer.isStillInvited()) {
                player.sendMessage(messagesManager.getString("no-invitation"));
            } else {
                final Guild targetGuild = dataManager.getGuildDataManager().getGuildByName(args[1]);

                if (targetGuild == null) {
                    player.sendMessage(messagesManager.getString("guild-not-found")
                            .replace("%guilde%", args[1]));
                } else {
                    if (!targetGuild.getGuildData().getGuildUniqueId()
                            .equals(invitedPlayer.getInvitedPlayerData().getGuild().getGuildData().getGuildUniqueId())) {
                        player.sendMessage(messagesManager.getString("not-invited-in-guild")
                                .replace("%guilde%", targetGuild.getGuildData().getName()));
                    } else {
                        if (targetGuild.getGuildData().getMembersUniqueId().size() > 10) {
                            player.sendMessage(messagesManager.getString("guild-full")
                                    .replace("%guilde%", args[1]));
                        } else {
                            invitedPlayer.setStillInvited(false);
                            invitedPlayer.setNew(false);
                            playerAccount.setGuild(targetGuild);
                            playerAccount.setGuildRank(GuildRank.MEMBER);
                            targetGuild.getGuildData().setPower(targetGuild.getGuildData().getPower() + playerAccount.getPower());
                            targetGuild.getGuildData().getMembersUniqueId().add(player.getUniqueId());
                            targetGuild.sendMessageToMembers(messagesManager.getString("guild-join")
                                    .replace("%player%", player.getName()));
                        }
                    }
                }
            }
        }
    }
}
