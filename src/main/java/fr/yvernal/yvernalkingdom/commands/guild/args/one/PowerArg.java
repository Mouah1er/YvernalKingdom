package fr.yvernal.yvernalkingdom.commands.guild.args.one;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import org.bukkit.entity.Player;

public class PowerArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());
        System.out.println(playerAccount.getPower() + " power");

        player.sendMessage(messagesManager.getString("send-power-to-player")
                .replace("%power%", String.valueOf(playerAccount.getPower())));
    }
}
