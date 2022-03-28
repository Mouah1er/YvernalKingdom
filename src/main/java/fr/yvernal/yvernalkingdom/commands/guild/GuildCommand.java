package fr.yvernal.yvernalkingdom.commands.guild;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.commands.YvernalCommand;
import fr.yvernal.yvernalkingdom.commands.guild.args.all.CreateArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.all.DescArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.all.RenameArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.one.*;
import fr.yvernal.yvernalkingdom.commands.guild.args.two.*;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdoms;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GuildCommand implements YvernalCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("guilde.player")) {
                if (args.length == 0) {
                    sendHelp(player);
                } else {
                    if (args[0].equalsIgnoreCase("help")) {
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
                                    new MapArg().execute(player, args);
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
                                    new LeadPlayerArg().execute(player, args);
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
                }
            } else {
                player.sendMessage(Main.getInstance().getConfigManager().getMessagesManager().getString("permission-message"));
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        final List<String> completions = new ArrayList<>();
        final List<String> commands = new ArrayList<>();

        if (args.length == 1) {
            commands.add("create");
            commands.add("desc");
            commands.add("claim");
            commands.add("map");
            commands.add("list");
            commands.add("unclaim");
            commands.add("disband");
            commands.add("show");
            commands.add("manage");
            commands.add("invite");
            commands.add("kick");
            commands.add("power");
            commands.add("rename");
            commands.add("leader");
            commands.add("promote");
            commands.add("demote");
            commands.add("join");
            commands.add("home");
            commands.add("sethome");
            commands.add("delhome");
            commands.add("leave");
            commands.add("help");

            StringUtil.copyPartialMatches(args[0], commands, completions);
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                commands.add("<nom>");
            } else if (args[0].equalsIgnoreCase("desc")) {
                commands.add("<description>");
            } else if (args[0].equalsIgnoreCase("list")) {
                for (Kingdoms value : Kingdoms.values()) {
                    commands.add(value.getKingdom().getKingdomProperties().getName());
                }
            } else if (args[0].equalsIgnoreCase("unclaim")) {
                commands.add("all");
            } else if (args[0].equalsIgnoreCase("show")) {
                if (sender instanceof Player) {
                    Main.getInstance().getDataManager().getGuildDataManager().getGuildByPlayer(((Player) sender).getUniqueId())
                            .getGuildData().getMembersUniqueId()
                            .stream()
                            .map(Bukkit::getOfflinePlayer)
                            .forEach(player -> commands.add(player.getName()));
                } else {
                    commands.addAll(Arrays.stream(Bukkit.getOfflinePlayers())
                            .map(OfflinePlayer::getName)
                            .collect(Collectors.toList()));
                }
            } else if (args[0].equalsIgnoreCase("manage")) {
                if (sender instanceof Player) {
                    Main.getInstance().getDataManager().getGuildDataManager().getGuildByPlayer(((Player) sender).getUniqueId())
                            .getGuildData().getMembersUniqueId()
                            .stream()
                            .map(Bukkit::getOfflinePlayer)
                            .forEach(player -> commands.add(player.getName()));
                } else {
                    commands.addAll(Arrays.stream(Bukkit.getOfflinePlayers())
                            .map(OfflinePlayer::getName)
                            .collect(Collectors.toList()));
                }
            } else if (args[0].equalsIgnoreCase("invite")) {
                commands.addAll(Bukkit.getOnlinePlayers()
                        .stream()
                        .map(Player::getName)
                        .collect(Collectors.toList()));
            } else if (args[0].equalsIgnoreCase("kick")) {
                if (sender instanceof Player) {
                    Main.getInstance().getDataManager().getGuildDataManager().getGuildByPlayer(((Player) sender).getUniqueId())
                            .getGuildData().getMembersUniqueId()
                            .stream()
                            .map(Bukkit::getOfflinePlayer)
                            .forEach(player -> commands.add(player.getName()));
                }
            } else if (args[0].equalsIgnoreCase("power")) {
                commands.addAll(Bukkit.getOnlinePlayers()
                        .stream()
                        .map(Player::getName)
                        .collect(Collectors.toList()));
            } else if (args[0].equalsIgnoreCase("leader")) {
                if (sender instanceof Player) {
                    Main.getInstance().getDataManager().getGuildDataManager().getGuildByPlayer(((Player) sender).getUniqueId())
                            .getGuildData().getMembersUniqueId()
                            .stream()
                            .map(Bukkit::getOfflinePlayer)
                            .forEach(player -> commands.add(player.getName()));
                }
            } else if (args[0].equalsIgnoreCase("rename")) {
                commands.add("<nom>");
            } else if (args[0].equalsIgnoreCase("promote")) {
                if (sender instanceof Player) {
                    Main.getInstance().getDataManager().getGuildDataManager().getGuildByPlayer(((Player) sender).getUniqueId())
                            .getGuildData().getMembersUniqueId()
                            .stream()
                            .map(Bukkit::getOfflinePlayer)
                            .forEach(player -> commands.add(player.getName()));
                }
            } else if (args[0].equalsIgnoreCase("demote")) {
                if (sender instanceof Player) {
                    Main.getInstance().getDataManager().getGuildDataManager().getGuildByPlayer(((Player) sender).getUniqueId())
                            .getGuildData().getMembersUniqueId()
                            .stream()
                            .map(Bukkit::getOfflinePlayer)
                            .forEach(player -> commands.add(player.getName()));
                }
            }

            StringUtil.copyPartialMatches(args[1], commands, completions);
        }

        return completions;
    }

    private void sendHelp(Player player) {
        player.sendMessage(Main.getInstance().getConfigManager().getMessagesManager().getStringList("guild-command-help")
                .toArray(new String[0]));
    }
}
