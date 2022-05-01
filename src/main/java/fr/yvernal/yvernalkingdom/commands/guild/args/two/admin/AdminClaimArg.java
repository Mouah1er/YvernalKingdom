package fr.yvernal.yvernalkingdom.commands.guild.args.two.admin;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims.ClaimData;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class AdminClaimArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final Guild guild = dataManager.getGuildDataManager().getGuildByName(args[1]);

        if (guild == null || guild.isDeleted()) {
            player.sendMessage(messagesManager.getString("guild-not-found")
                    .replace("%guilde%", args[1]));
        } else {
            final Kingdom kingdom = guild.getGuildData().getKingdom();
    
            if (!kingdom.getKingdomProperties().getTotalTerritoryCuboid().isIn(player)) {
                player.sendMessage(messagesManager.getString("player-try-claim-outside-kingdom"));
            } else {
                final Chunk playerChunk = player.getLocation().getChunk();
                final Claim claim = dataManager.getClaimManager().getClaimAt(playerChunk.getX(),
                        playerChunk.getZ());

                if (claim == null) {
                    final Claim newClaim = new Claim(new ClaimData(guild, playerChunk.getX(), playerChunk.getZ()),
                            false, true, true);
                    dataManager.getClaimManager().getClaims().add(newClaim);
                    guild.getGuildData().getClaims().add(newClaim);

                    guild.sendMessageToMembers(messagesManager.getString("successfully-claimed-chunk")
                            .replace("%player%", player.getName())
                            .replace("%x%", String.valueOf(playerChunk.getX()))
                            .replace("%z%", String.valueOf(playerChunk.getZ())));
                    player.sendMessage(messagesManager.getString("successfully-claimed-chunk")
                            .replace("%player%", player.getName())
                            .replace("%x%", String.valueOf(playerChunk.getX()))
                            .replace("%z%", String.valueOf(playerChunk.getZ())));
                } else {
                    final boolean isSuccessfullyClaimed = claim.claim(guild, player, messagesManager);

                    if (isSuccessfullyClaimed) {
                        player.sendMessage(messagesManager.getString("successfully-claimed-chunk")
                                .replace("%player%", player.getName())
                                .replace("%x%", String.valueOf(playerChunk.getX()))
                                .replace("%z%", String.valueOf(playerChunk.getZ())));
                    }
                }
            }
        }
    }
}
