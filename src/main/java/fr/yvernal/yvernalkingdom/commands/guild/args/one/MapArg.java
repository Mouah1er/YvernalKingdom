package fr.yvernal.yvernalkingdom.commands.guild.args.one;

import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.utils.map.AsciiMap;
import me.rayzr522.jsonmessage.JSONMessage;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class MapArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final List<JSONMessage> lines = new AsciiMap(player.getLocation().getYaw(), player).getMap();

        player.sendMessage(StringUtils.repeat(ChatColor.STRIKETHROUGH + " " + ChatColor.GOLD + "-", 28));
        lines.forEach(line -> line.send(player));
        player.sendMessage(StringUtils.repeat(ChatColor.STRIKETHROUGH + " " + ChatColor.GOLD + "-", 28));
    }
}
