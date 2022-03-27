package fr.yvernal.yvernalkingdom.commands.guild;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.commands.YvernalCommand;
import fr.yvernal.yvernalkingdom.commands.guild.args.all.CreateArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.all.DescArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.all.RenameArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.one.*;
import fr.yvernal.yvernalkingdom.commands.guild.args.two.*;
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
                    } else if (args[0].equalsIgnoreCase("rename")) {
                        new RenameArg().execute(player, args);
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
                                new ShowArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("power")) {
                                new PowerArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("sethome")) {
                                new SetHomeArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("delhome")) {
                                new DelHomeArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("home")) {
                                new HomeArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("leave")) {
                                new LeaveArg().execute(player, args);
                            } else {
                                sendHelp(player);
                            }
                        } else if (args.length == 2) {
                            if (args[0].equalsIgnoreCase("list")) {
                                new ListArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("unclaim") && args[1].equalsIgnoreCase("all")) {
                                new UnClaimAllArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("show")) {
                                new ShowPlayerArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("manage")) {
                                new ManagePlayerArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("invite")) {
                                new InviteArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("kick")) {
                                new KickPlayerArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("power")) {
                                new PlayerPowerArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("leader")) {

                            } else if (args[0].equalsIgnoreCase("promote")) {
                                new PromotePlayerArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("demote")) {
                                new DemotePlayerArg().execute(player, args);
                            } else if (args[0].equalsIgnoreCase("join")) {
                                new JoinArg().execute(player, args);
                            } else {
                                sendHelp(player);
                            }
                        } else {
                            sendHelp(player);
                        }
                    }
                }
            } else {
                player.sendMessage(Main.getInstance().getConfigManager().getMessagesManager().getString("permission-message"));
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
