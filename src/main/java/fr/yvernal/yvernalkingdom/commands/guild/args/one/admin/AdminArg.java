package fr.yvernal.yvernalkingdom.commands.guild.args.one.admin;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import org.bukkit.entity.Player;

public class AdminArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());
        playerAccount.setInAdminMode(!playerAccount.isInAdminMode());
        player.sendMessage(playerAccount.isInAdminMode() ? messagesManager.getString("admin-mode-enabled") :
                messagesManager.getString("admin-mode-disabled"));
    }
}
