package fr.yvernal.yvernalkingdom.commands.guild.args.two;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.inventories.ShowGuildInventory;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ShowPlayerArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[1]);

        if (targetPlayer == null || !targetPlayer.hasPlayedBefore()) {
            player.sendMessage(messagesManager.getString("player-not-found")
                    .replace("%player%", args[1]));
        } else {
            final PlayerAccount targetPlayerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(targetPlayer.getUniqueId());
            final Guild targetPlayerGuild = dataManager.getGuildDataManager().getGuildByPlayer(targetPlayer.getUniqueId());

            if (playerIsInGuildWithMessage(player, targetPlayerGuild, targetPlayerAccount)) {
                new ShowGuildInventory(player, targetPlayerGuild).open(player);
            }
        }
    }
}
