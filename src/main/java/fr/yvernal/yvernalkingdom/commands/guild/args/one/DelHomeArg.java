package fr.yvernal.yvernalkingdom.commands.guild.args.one;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import org.bukkit.entity.Player;

public class DelHomeArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());
        final Guild playerGuild = playerAccount.getGuild();

        if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
            if (guildRankIsMasterWithMessage(player, playerAccount)) {
                if (playerGuild.getGuildData().getHome() == null) {
                    player.sendMessage(messagesManager.getString("guild-home-not-set"));
                } else {
                    playerGuild.getGuildData().setHome(null);
                    playerGuild.sendMessageToMembers(messagesManager.getString("guild-home-deleted"));
                }
            }
        }
    }
}
