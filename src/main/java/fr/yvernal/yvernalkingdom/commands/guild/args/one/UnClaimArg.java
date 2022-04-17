package fr.yvernal.yvernalkingdom.commands.guild.args.one;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class UnClaimArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());
        final Guild playerGuild = playerAccount.getGuild();

        if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
            if (!guildRankIsMemberWithMessage(player, playerAccount)) {
                final Chunk playerChunk = player.getLocation().getChunk();

                final Claim claim = dataManager.getClaimManager().getClaimAt(playerChunk.getX(), playerChunk.getZ());

                if (claim == null) {
                    player.sendMessage(messagesManager.getString("not-claimed"));
                } else {
                    if (claim.isUnClaim()) {
                        player.sendMessage(messagesManager.getString("not-claimed"));
                    } else {
                        if (!claim.getClaimData().getGuild().getGuildData().getGuildUniqueId().equals(playerGuild.getGuildData().getGuildUniqueId())) {
                            player.sendMessage(messagesManager.getString("not-claimed-by-player-guild-error")
                                    .replace("%guilde%", claim.getClaimData().getGuild().getGuildData().getName()));
                        } else {
                            playerGuild.sendMessageToMembers(messagesManager.getString("successfully-unclaimed")
                                    .replace("%player%", player.getName())
                                    .replace("%x%", String.valueOf(playerChunk.getX()))
                                    .replace("%z%", String.valueOf(playerChunk.getZ())));
                            claim.setNew(false);
                            claim.setUnClaim(true);
                        }
                    }
                }
            }
        }
    }
}
