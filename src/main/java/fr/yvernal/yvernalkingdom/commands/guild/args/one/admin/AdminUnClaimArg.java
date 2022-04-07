package fr.yvernal.yvernalkingdom.commands.guild.args.one.admin;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class AdminUnClaimArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final Chunk chunk = player.getLocation().getChunk();

        final Claim claimAt = dataManager.getClaimManager().getClaimAt(chunk.getX(), chunk.getZ());

        if (claimAt == null || claimAt.isUnClaim()) {
            player.sendMessage(messagesManager.getString("not-claimed"));
        } else {
            final Guild guild = dataManager.getGuildDataManager().getGuildByName(claimAt.getClaimData().getGuildName());

            guild.sendMessageToMembers(messagesManager.getString("successfully-unclaimed")
                    .replace("%player%", player.getName())
                    .replace("%x%", String.valueOf(chunk.getX()))
                    .replace("%z%", String.valueOf(chunk.getZ())));
            claimAt.setNew(false);
            claimAt.setUnClaim(true);

            player.sendMessage(messagesManager.getString("successfully-unclaimed")
                    .replace("%player%", player.getName())
                    .replace("%x%", String.valueOf(chunk.getX()))
                    .replace("%z%", String.valueOf(chunk.getZ())));
        }
    }
}