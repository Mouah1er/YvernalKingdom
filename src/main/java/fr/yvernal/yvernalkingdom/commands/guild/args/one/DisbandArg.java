package fr.yvernal.yvernalkingdom.commands.guild.args.one;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import org.bukkit.entity.Player;

public class DisbandArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());
        final Guild playerGuild = playerAccount.getGuild();

        if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
            if (guildRankIsMasterWithMessage(player, playerAccount)) {
                playerGuild.disband(dataManager, messagesManager);
            }
        }
    }
}
