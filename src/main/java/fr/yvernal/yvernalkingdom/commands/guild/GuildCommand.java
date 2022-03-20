package fr.yvernal.yvernalkingdom.commands.guild;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.commands.YvernalCommand;
import fr.yvernal.yvernalkingdom.commands.guild.args.one.ClaimArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.one.CreateArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.one.DisbandArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.one.UnClaimArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.two.DescArg;
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
                    if (args[0].equalsIgnoreCase("create")) {
                        new CreateArg().execute(player, args);
                    } else if (args[0].equalsIgnoreCase("desc")) {
                        new DescArg().execute(player, args);
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
                        } else if (args.length == 2) {
                            if (args[0].equalsIgnoreCase("list")) {

                            } else if (args[0].equalsIgnoreCase("unclaim") && args[1].equalsIgnoreCase("amm")) {

                            } else if (args[0].equalsIgnoreCase("show")) {

                            } else if (args[0].equalsIgnoreCase("manage")) {

                            } else if (args[0].equalsIgnoreCase("invite")) {

                            } else if (args[0].equalsIgnoreCase("kick")) {

                            } else if (args[0].equalsIgnoreCase("power")) {

                            } else if (args[0].equalsIgnoreCase("rename")) {

                            } else if (args[0].equalsIgnoreCase("leader")) {

                            } else if (args[0].equalsIgnoreCase("promote")) {

                            } else if (args[0].equalsIgnoreCase("demote")) {

                            } else {
                                sendHelp(player);
                            }
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
