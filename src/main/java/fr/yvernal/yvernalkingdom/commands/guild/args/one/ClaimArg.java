package fr.yvernal.yvernalkingdom.commands.guild.args.one;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims.ClaimData;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ClaimArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final Guild playerGuild = dataManager.getGuildDataManager().getGuildByPlayer(player.getUniqueId());
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

        if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
            if (!guildRankIsMemberWithMessage(player, playerAccount)) {
                final List<Claim> claims = dataManager.getClaimManager().getClaims();
                final Chunk playerChunk = player.getLocation().getChunk();

                final Kingdom kingdom = dataManager.getKingdomDataManager().getKingdomByUniqueId(player.getUniqueId());

                if (!kingdom.getKingdomProperties().getTotalTerritoryCuboid().isIn(player))  {
                    player.sendMessage(messagesManager.getString("player-try-claim-outside-kingdom"));
                } else {
                    final Optional<Claim> optionalClaim = claims.stream().filter(claim -> claim.getClaimData().getX() == playerChunk.getX() &&
                                    claim.getClaimData().getZ() == playerChunk.getZ())
                            .findFirst();

                    if (optionalClaim.isPresent()) {
                        final Claim claim = optionalClaim.get();

                        if (!claim.isUnClaim()) {
                            player.sendMessage(messagesManager.getString("already-claim-by-guild-error")
                                    .replace("%guilde%", claim.getClaimData().getGuildName()));
                        } else {
                            claim.setUnClaim(false);

                            playerGuild.getGuildData().getMembersUniqueId()
                                    .stream()
                                    .map(Bukkit::getPlayer)
                                    .filter(Objects::nonNull)
                                    .forEach(player1 -> player1.sendMessage(messagesManager.getString("successfully-claimed-chunk")
                                            .replace("%player%", player.getName())
                                            .replace("%x%", String.valueOf(playerChunk.getX()))
                                            .replace("%z%", String.valueOf(playerChunk.getZ()))));
                        }
                    } else {
                        dataManager.getClaimManager().getClaims().add(new Claim(new ClaimData(playerGuild.getGuildData().getGuildUniqueId(),
                                playerGuild.getGuildData().getName(), playerChunk.getX(), playerChunk.getZ()), false, true));

                        playerGuild.sendMessageToMembers(messagesManager.getString("successfully-claimed-chunk")
                                .replace("%player%", player.getName())
                                .replace("%x%", String.valueOf(playerChunk.getX()))
                                .replace("%z%", String.valueOf(playerChunk.getZ())));
                    }
                }
            }
        }
    }
}
