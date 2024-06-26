package fr.yvernal.yvernalkingdom.commands.guild.args.one;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims.ClaimData;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class ClaimArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());
        final Guild playerGuild = playerAccount.getGuild();

        if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
            if (!guildRankIsMemberWithMessage(player, playerAccount)) {
                final Chunk playerChunk = player.getLocation().getChunk();

                final Kingdom kingdom = dataManager.getKingdomDataManager().getKingdomByUniqueId(player.getUniqueId());

                if (!kingdom.getKingdomProperties().getTotalTerritoryCuboid().isIn(player)) {
                    player.sendMessage(messagesManager.getString("player-try-claim-outside-kingdom"));
                } else {
                    if (playerAccount.getPower() == 0) {
                        final int hour = playerAccount.getPowerRunnable().getTimer() / 3600;
                        final int minutes = (playerAccount.getPowerRunnable().getTimer() % 3600) / 60;
                        player.sendMessage(messagesManager.getString("no-power-to-claim")
                                .replace("%time%", hour + "h" + minutes + "min"));
                    } else {
                        final Claim claim = dataManager.getClaimManager().getClaimAt(playerChunk.getX(), playerChunk.getZ());

                        if (claim == null) {
                            final Claim newClaim = new Claim(new ClaimData(playerGuild, playerChunk.getX(), playerChunk.getZ()),
                                    false, true, true);
                            dataManager.getClaimManager().getClaims().add(newClaim);
                            playerGuild.getGuildData().getClaims().add(newClaim);

                            playerGuild.sendMessageToMembers(messagesManager.getString("successfully-claimed-chunk")
                                    .replace("%player%", player.getName())
                                    .replace("%x%", String.valueOf(playerChunk.getX()))
                                    .replace("%z%", String.valueOf(playerChunk.getZ())));
                        } else {
                            claim.claim(playerGuild, player, messagesManager);
                        }
                    }
                }
            }
        }
    }
}
