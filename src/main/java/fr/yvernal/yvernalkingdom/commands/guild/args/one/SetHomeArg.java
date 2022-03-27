package fr.yvernal.yvernalkingdom.commands.guild.args.one;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SetHomeArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final Guild playerGuild = dataManager.getGuildDataManager().getGuildByPlayer(player.getUniqueId());
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

        if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
            if (guildRankIsMasterWithMessage(player, playerAccount)) {
                final Location playerLocation = player.getLocation();
                playerGuild.getGuildData().setHome(new Location(playerLocation.getWorld(), playerLocation.getBlockX(), playerLocation.getBlockY(),
                        playerLocation.getBlockZ(), playerLocation.getYaw(), playerLocation.getPitch()));
                playerGuild.sendMessageToMembers(messagesManager.getString("guild-home-set"));
            }
        }
    }
}
