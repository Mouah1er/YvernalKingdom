package fr.yvernal.yvernalkingdom.commands.guild.args.two.admin;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import org.bukkit.entity.Player;

public class AdminDisbandArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        System.out.println("test");
        final Guild guild = dataManager.getGuildDataManager().getGuildByName(args[1]);

        if (guild == null || guild.isDeleted()) {
            player.sendMessage(messagesManager.getString("guild-not-found")
                    .replace("%guilde%", args[1]));
        } else {
            guild.disband(dataManager, messagesManager);
            player.sendMessage(messagesManager.getString("successfully-deleted-guild"));
        }
    }
}
