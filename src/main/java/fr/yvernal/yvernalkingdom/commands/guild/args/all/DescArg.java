package fr.yvernal.yvernalkingdom.commands.guild.args.all;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import org.bukkit.entity.Player;

public class DescArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(messagesManager.getStringList("guild-command-help")
                    .toArray(new String[0]));
        } else {
            final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());
            final Guild playerGuild = playerAccount.getGuild();

            if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
                final String joinedArgs = joinArgs(args);

                if (joinedArgs.length() > 100) {
                    player.sendMessage(messagesManager.getString("guild-desc-too-big"));
                } else {
                    playerGuild.getGuildData().setDescription(joinedArgs);

                    playerGuild.sendMessageToMembers(messagesManager.getString("successfully-changed-guild-desc")
                            .replace("%description%", playerGuild.getGuildData().getDescription()));
                }
            }
        }
    }
}
