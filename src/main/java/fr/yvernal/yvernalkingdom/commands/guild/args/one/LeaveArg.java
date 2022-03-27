package fr.yvernal.yvernalkingdom.commands.guild.args.one;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import org.bukkit.entity.Player;

public class LeaveArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());
        final Guild playerGuild = dataManager.getGuildDataManager().getGuildByPlayer(player.getUniqueId());

        if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
            if (guildRankIsMaster(playerAccount)) {
                player.sendMessage(messagesManager.getString("master-cannot-leave-guild"));
            } else {
                playerGuild.getGuildData().getMembersUniqueId().remove(player.getUniqueId());
                playerAccount.setGuildName("no-guild");
                playerAccount.setGuildUniqueId("no-guild");
                playerAccount.setGuildRank(GuildRank.NO_GUILD);
                playerGuild.getGuildData().setPower(playerGuild.getGuildData().getPower() - playerAccount.getPower());
                playerGuild.sendMessageToMembers(messagesManager.getString("guild-member-left")
                        .replace("%player%", player.getName()));
                player.sendMessage(messagesManager.getString("guild-left"));
            }
        }
    }
}
