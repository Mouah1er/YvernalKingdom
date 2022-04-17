package fr.yvernal.yvernalkingdom.commands.guild.args.all;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.GuildData;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(messagesManager.getStringList("guild-command-help")
                    .toArray(new String[0]));
        } else {
            final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());
            Guild playerGuild = playerAccount.getGuild();

            if (!playerIsInGuild(playerGuild, playerAccount)) {
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
                        String guildUniqueId = UUID.randomUUID().toString();

                        while (dataManager.getGuildDataManager().getGuilds()
                                .stream()
                                .map(guild -> guild.getGuildData().getGuildUniqueId())
                                .collect(Collectors.toList())
                                .contains(guildUniqueId)) {
                            guildUniqueId = UUID.randomUUID().toString();
                        }

                        if (playerGuild != null && playerGuild.isDeleted()) {
                            playerGuild.getGuildData().setName(joinedArgs);
                            playerGuild.getGuildData().setDescription("");
                            playerGuild.getGuildData().setPower(10);
                            playerGuild.getGuildData().setHome(null);
                            playerGuild.getGuildData().setOwnerUniqueId(player.getUniqueId());
                            playerGuild.getGuildData().getMembersUniqueId().clear();
                            playerGuild.getGuildData().getMembersUniqueId().add(player.getUniqueId());
                            playerGuild.setDeleted(false);
                            playerGuild.setNew(true);
                        } else {
                            playerGuild = new Guild(new GuildData(guildUniqueId, playerAccount.getKingdom(), joinedArgs,
                                    "", playerAccount.getPower(), null, player.getUniqueId(),
                                    Collections.singletonList(player.getUniqueId()), Collections.emptyList(), Collections.emptyList()), false, true);

                            dataManager.getGuildDataManager().getGuilds().add(playerGuild);
                            playerAccount.getKingdom().getKingdomData().getGuildsIn().add(playerGuild);
                        }

                        playerAccount.setGuild(playerGuild);
                        playerAccount.setGuildRank(GuildRank.MASTER);

                        player.sendMessage(messagesManager.getString("successfully-created-guild")
                                .replace("%guilde%", joinedArgs));
                    }
                }
            } else {
                player.sendMessage(messagesManager.getString("already-in-guild"));
            }
        }
    }
}
