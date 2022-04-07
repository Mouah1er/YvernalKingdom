package fr.yvernal.yvernalkingdom.commands.guild.args.two.admin;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class AdminFullPowerPlayerArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[1]);

        if (targetPlayer == null) {
            player.sendMessage(messagesManager.getString("player-not-found")
                    .replace("%player%", args[1]));
        } else {
            final PlayerAccount targetPlayerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(targetPlayer.getUniqueId());

            targetPlayerAccount.setPower(10);

            if (targetPlayer.isOnline()) {
                ((Player) targetPlayer).sendMessage(messagesManager.getString("admin-full-power-player"));
            }

            player.sendMessage(messagesManager.getString("admin-full-powered-player")
                    .replace("%player%", targetPlayer.getName()));
        }
    }
}
