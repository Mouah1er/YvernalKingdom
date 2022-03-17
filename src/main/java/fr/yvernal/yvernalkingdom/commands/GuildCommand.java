package fr.yvernal.yvernalkingdom.commands;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.commands.args.ClaimArg;
import fr.yvernal.yvernalkingdom.commands.args.CreateArg;
import fr.yvernal.yvernalkingdom.commands.args.DisbandArg;
import fr.yvernal.yvernalkingdom.commands.args.UnClaimArg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GuildCommand implements YvernalCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("guilde.player")) {
                if (args.length == 0) {
                    sendHelp(player);
                } else {
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("claim")) {
                            new ClaimArg().execute(player, args);
                        } else if (args[0].equalsIgnoreCase("map")) {

                        } else if (args[0].equalsIgnoreCase("unclaim")) {
                            new UnClaimArg().execute(player, args);
                        } else if (args[0].equalsIgnoreCase("disband")) {
                            new DisbandArg().execute(player, args);
                        } else if (args[0].equalsIgnoreCase("show")) {

                        } else if (args[0].equalsIgnoreCase("power")) {

                        } else if (args[0].equalsIgnoreCase("sethome")) {

                        } else if (args[0].equalsIgnoreCase("delhome")) {

                        } else if (args[0].equalsIgnoreCase("home")) {

                        } else {
                            sendHelp(player);
                        }
                    } else {
                        if (args[0].equalsIgnoreCase("create")) {
                            new CreateArg().execute(player, args);
                        } else {
                            sendHelp(player);
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }

    private void sendHelp(Player player) {
        player.sendMessage(Main.getInstance().getConfigManager().getMessagesManager().getStringList("guild-command-help")
                .toArray(new String[0]));
    }
}
