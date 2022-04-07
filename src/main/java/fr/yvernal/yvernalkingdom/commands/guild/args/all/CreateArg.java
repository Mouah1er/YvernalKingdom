package fr.yvernal.yvernalkingdom.commands.guild.args.all;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.GuildData;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CreateArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(messagesManager.getStringList("guild-command-help")
                    .toArray(new String[0]));
        } else {
            final Guild playerGuild = dataManager.getGuildDataManager().getGuildByPlayer(player.getUniqueId());
            final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

            if (!playerIsInGuild(playerGuild, playerAccount)) {
                final String joinedArgs = joinArgs(args);

                if (joinedArgs.equalsIgnoreCase("no-guild")) {
                    player.sendMessage(messagesManager.getString("could-not-create-guild"));
                } else {
                    final Kingdom kingdomByUniqueId = dataManager.getKingdomDataManager().getKingdomByUniqueId(player.getUniqueId());
                    final List<Guild> guildsIn = kingdomByUniqueId.getKingdomData().getGuildsIn();
                    for (Guild guild : guildsIn) {
                        if (guild.getGuildData().getName().equals(joinedArgs) && !guild.isDeleted()) {
                            player.sendMessage(messagesManager.getString("guild-name-already-exists"));

                            return;
                        }
                    }

                    if (joinedArgs.length() > 16) {
                        player.sendMessage(messagesManager.getString("guild-name-too-big"));
                    } else {
                        final String guildUniqueId = UUID.randomUUID().toString();

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
                            final Guild guild = new Guild(new GuildData(guildUniqueId, playerAccount.getKingdomName(), joinedArgs,
                                    "", playerAccount.getPower(), null, player.getUniqueId(),
                                    Collections.singletonList(player.getUniqueId())), false, true);

                            dataManager.getGuildDataManager().getGuilds().add(guild);
                            dataManager.getKingdomDataManager().getKingdomByNumber(playerAccount.getKingdomName())
                                    .getKingdomData().getGuildsIn().add(guild);
                        }

                        playerAccount.setGuildName(joinedArgs);
                        playerAccount.setGuildRank(GuildRank.MASTER);
                        playerAccount.setGuildUniqueId(guildUniqueId);

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
