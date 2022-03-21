package fr.yvernal.yvernalkingdom.commands.guild.args.two;

import com.google.common.base.Joiner;
import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class ListArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final Kingdom kingdom = dataManager.getKingdomDataManager().getKingdomByName(args[1]);

        if (kingdom == null) {
            player.sendMessage(messagesManager.getString("kingdom-not-found")
                    .replace("%kingdom%", args[1]));
        } else {
            final Joiner joiner = Joiner.on(", ");
            final String guilds = joiner.join(kingdom.getKingdomData().getGuildsIn()
                    .stream()
                    .map(guild -> guild.getGuildData().getName())
                    .collect(Collectors.toList()));

            if (guilds.isEmpty()) {
                player.sendMessage(messagesManager.getString("no-guild-in-kingdom")
                        .replace("%kingdom%", kingdom.getKingdomProperties().getName()));
            } else {
                player.sendMessage(messagesManager.getString("kingdom-guilds-list")
                        .replace("%kingdom%", kingdom.getKingdomProperties().getName()));
                player.sendMessage("§7" + guilds);
            }
        }
    }
}