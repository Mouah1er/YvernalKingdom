package fr.yvernal.yvernalkingdom.commands.args;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.messages.MessagesManager;
import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class UnClaimArg implements YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final DataManager dataManager = Main.getInstance().getDataManager();
        final MessagesManager messagesManager = Main.getInstance().getConfigManager().getMessagesManager();
        final Guild playerGuild = dataManager.getGuildDataManager().getGuild(player.getUniqueId());
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

        if (playerAccount.getGuildRank() == GuildRank.NO_GUILD && playerGuild == null ||
                playerGuild.isDeleted() && playerAccount.getGuildRank() == GuildRank.NO_GUILD) {
            player.sendMessage(messagesManager.getString("player-not-in-guild"));
        } else {
            if (playerAccount.getGuildRank() == GuildRank.MEMBER) {
                player.sendMessage(messagesManager.getString("player-guild-permission-error"));
            } else {
                final List<Claim> claims = dataManager.getClaimManager().getClaims();
                final Chunk playerChunk = player.getLocation().getChunk();

                for (int i = 0; i < claims.size(); i++) {
                    final Claim claim = claims.get(i);

                    if (claim.getClaimData().getX() == playerChunk.getX() &&
                            claim.getClaimData().getZ() == playerChunk.getZ()) {
                        if (claim.isUnClaim()) {
                            player.sendMessage(messagesManager.getString("not-claimed"));
                        } else {
                            if (!claim.getClaimData().getGuildUniqueId().equals(playerGuild.getGuildData().getGuildUniqueId())) {
                                player.sendMessage(messagesManager.getString("not-claimed-by-player-guild-error")
                                        .replace("%guild%", claim.getClaimData().getGuildName()));
                            } else {
                                claim.setNew(false);
                                claim.setUnClaim(true);
                                dataManager.getClaimManager().getClaims().set(i, claim);
                                System.out.println(claims.get(i) + " unclaim");
                                playerGuild.getGuildData().getMembersUniqueId()
                                        .stream()
                                        .map(Bukkit::getPlayer)
                                        .filter(Objects::nonNull)
                                        .forEach(player1 -> player1.sendMessage(messagesManager.getString("successfully-unclaimed")
                                                .replace("%player%", player.getName())
                                                .replace("%x%", String.valueOf(playerChunk.getX()))
                                                .replace("%z%", String.valueOf(playerChunk.getZ()))));
                            }
                        }

                        return;
                    }
                }
            }
        }
    }
}
