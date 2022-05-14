package fr.yvernal.yvernalkingdom.commands.guild.args.all;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import org.bukkit.entity.Player;

public class RenameArg extends YvernalArg {

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

                if (joinedArgs.equalsIgnoreCase("no-guild")) {
                    player.sendMessage(messagesManager.getString("could-not-create-guild"));
                } else {
                    for (Guild guild : dataManager.getGuildDataManager().getGuilds()) {
                        if (guild.getGuildData().getName().equals(joinedArgs) && !guild.isDeleted()) {
                            player.sendMessage(messagesManager.getString("guild-name-already-exists"));

                            return;
                        }
                    }

                    if (joinedArgs.length() > 16) {
                        player.sendMessage(messagesManager.getString("guild-name-too-big"));
                    } else {
                        playerGuild.getGuildData().setName(joinedArgs);

                        playerGuild.sendMessageToMembers(messagesManager.getString("successfully-changed-guild-name")
                                .replace("%guilde%", playerGuild.getGuildData().getName()));
                    }
                }
            }
        }
    }
}
