package fr.yvernal.yvernalkingdom.commands.guild.args.two;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerPowerArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final Player targetPlayer = Bukkit.getPlayer(args[1]);

        if (targetPlayer == null) {
            player.sendMessage(messagesManager.getString("player-not-found")
                    .replace("%player%", args[1]));
        } else {
            final PlayerAccount targetPlayerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(targetPlayer.getUniqueId());

            player.sendMessage(messagesManager.getString("send-player-power-to-player")
                    .replace("%player%", targetPlayer.getName())
                    .replace("%power%", String.valueOf(targetPlayerAccount.getPower())));
        }
    }
}
