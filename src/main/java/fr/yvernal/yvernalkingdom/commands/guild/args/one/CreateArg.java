package fr.yvernal.yvernalkingdom.commands.guild.args.one;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.GuildData;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.UUID;

public class CreateArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(messagesManager.getStringList("guild-command-help")
                    .toArray(new String[0]));
        } else {
            final Guild playerGuild = dataManager.getGuildDataManager().getGuild(player.getUniqueId());
            final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

            if (!playerIsInGuild(playerGuild, playerAccount)) {
                final String joinedArgs = joinArgs(args);

                if (joinedArgs.length() > 16) {
                    player.sendMessage(messagesManager.getString("guild-name-too-big"));
                } else {
                    final String guildUniqueId = UUID.randomUUID().toString();
                    final Guild guild = new Guild(new GuildData(guildUniqueId, playerAccount.getKingdomName(), joinedArgs,
                            "", 10, null, player.getUniqueId(), Collections.singletonList(player.getUniqueId())),
                            false, true);

                    dataManager.getGuildDataManager().getGuilds().add(guild);
                    dataManager.getKingdomDataManager().getKingdom(playerAccount.getKingdomName()).getKingdomData().getGuildsIn().add(guild);
                    playerAccount.setGuildName(joinedArgs);
                    playerAccount.setGuildRank(GuildRank.MASTER);
                    playerAccount.setGuildUniqueId(guildUniqueId);

                    player.sendMessage(messagesManager.getString("successfully-created-guild")
                            .replace("%guild%", joinedArgs));
                }
            } else {
                player.sendMessage(messagesManager.getString("already-in-guild"));
            }
        }
    }
}
