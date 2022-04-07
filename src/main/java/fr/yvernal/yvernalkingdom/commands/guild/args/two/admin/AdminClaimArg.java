package fr.yvernal.yvernalkingdom.commands.guild.args.two.admin;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.claims.ClaimData;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.entity.Player;

public class AdminClaimArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final Guild guild = dataManager.getGuildDataManager().getGuildByName(args[1]);

        if (guild == null || guild.isDeleted()) {
            player.sendMessage(messagesManager.getString("guild-not-found")
                    .replace("%guilde%", args[1]));
        } else {
            final Kingdom kingdom = dataManager.getKingdomDataManager().getKingdomByNumber(guild.getGuildData().getKingdomName());
    
            if (!kingdom.getKingdomProperties().getTotalTerritoryCuboid().isIn(player)) {
                player.sendMessage(messagesManager.getString("player-try-claim-outside-kingdom"));
            } else {
                final Claim claim = dataManager.getClaimManager().getClaimAt(player.getLocation().getChunk().getX(),
                        player.getLocation().getChunk().getZ());

                if (claim == null) {
                    dataManager.getClaimManager().getClaims().add(new Claim(new ClaimData(guild.getGuildData().getGuildUniqueId(),
                            guild.getGuildData().getName(), player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ()),
                            false, true));

                    guild.sendMessageToMembers(messagesManager.getString("successfully-claimed-chunk")
                            .replace("%player%", player.getName())
                            .replace("%x%", String.valueOf(player.getLocation().getChunk().getX()))
                            .replace("%z%", String.valueOf(player.getLocation().getChunk().getZ())));
                    player.sendMessage(messagesManager.getString("successfully-claimed-chunk")
                            .replace("%player%", player.getName())
                            .replace("%x%", String.valueOf(player.getLocation().getChunk().getX()))
                            .replace("%z%", String.valueOf(player.getLocation().getChunk().getZ())));
                } else {
                    final boolean isSuccessfullyClaimed = claim.claim(guild, player, messagesManager);

                    if (isSuccessfullyClaimed) {
                        player.sendMessage(messagesManager.getString("successfully-claimed-chunk")
                                .replace("%player%", player.getName())
                                .replace("%x%", String.valueOf(player.getLocation().getChunk().getX()))
                                .replace("%z%", String.valueOf(player.getLocation().getChunk().getZ())));
                    }
                }
            }
        }
    }
}
