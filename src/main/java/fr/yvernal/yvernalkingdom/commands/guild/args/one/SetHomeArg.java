package fr.yvernal.yvernalkingdom.commands.guild.args.one;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class SetHomeArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final Guild playerGuild = dataManager.getGuildDataManager().getGuildByPlayer(player.getUniqueId());
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

        if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
            if (guildRankIsMasterWithMessage(player, playerAccount)) {
                final Location playerLocation = player.getLocation();
                final List<Chunk> claims = dataManager.getClaimManager().getClaims(playerGuild)
                        .stream()
                        .map(Claim::toChunk)
                        .collect(Collectors.toList());

                if (!claims.contains(playerLocation.getChunk())) {
                    player.sendMessage(messagesManager.getString("guild-home-set-not-in-guild-claims"));
                } else {
                    playerGuild.getGuildData().setHome(new Location(playerLocation.getWorld(), playerLocation.getBlockX(), playerLocation.getBlockY(),
                            playerLocation.getBlockZ(), playerLocation.getYaw(), playerLocation.getPitch()));
                    playerGuild.sendMessageToMembers(messagesManager.getString("guild-home-set"));
                }
            }
        }
    }
}
