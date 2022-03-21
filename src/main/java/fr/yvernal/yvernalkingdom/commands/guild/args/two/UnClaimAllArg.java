package fr.yvernal.yvernalkingdom.commands.guild.args.two;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class UnClaimAllArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final Guild playerGuild = dataManager.getGuildDataManager().getGuildByPlayer(player.getUniqueId());
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

        if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
            if (!guildRankIsMemberWithMessage(player, playerAccount)) {
                final List<Claim> claims = dataManager.getClaimManager().getClaims();
                final List<Claim> claimsNoUnclaim = claims.stream()
                        .filter(claim -> !claim.isUnClaim())
                        .collect(Collectors.toList());

                if (claimsNoUnclaim.isEmpty()) {
                    player.sendMessage(messagesManager.getString("guild-does-not-have-any-claim"));
                } else {

                    for (Claim claim : claimsNoUnclaim) {
                        dataManager.getClaimManager().getClaims().remove(claim);
                        claim.setUnClaim(true);
                        claim.setNew(false);
                        dataManager.getClaimManager().getClaims().add(claim);
                    }

                    playerGuild.sendMessageToMembers(messagesManager.getString("successfully-unclaimed-all-chunk")
                            .replace("%player%", player.getName()));
                }
            }
        }
    }
}
