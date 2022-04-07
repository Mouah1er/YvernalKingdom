package fr.yvernal.yvernalkingdom.commands.guild;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.commands.YvernalCommand;
import fr.yvernal.yvernalkingdom.commands.guild.args.all.CreateArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.all.DescArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.all.RenameArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.one.*;
import fr.yvernal.yvernalkingdom.commands.guild.args.one.admin.AdminArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.one.admin.AdminUnClaimArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.two.*;
import fr.yvernal.yvernalkingdom.commands.guild.args.two.admin.AdminClaimArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.two.admin.AdminDisbandArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.two.admin.AdminFullPowerPlayerArg;
import fr.yvernal.yvernalkingdom.commands.guild.args.two.admin.AdminKickPlayerArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdoms;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
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

public class GuildCommand extends YvernalCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            final PlayerAccount playerAccount = Main.getInstance().getDataManager().getPlayerAccountManager()
                    .getPlayerAccount(player.getUniqueId());

            if (player.hasPermission("guilde.player")) {
                final boolean isInAdminMode = playerAccount.isInAdminMode();

                if (args.length == 0) {
                    sendHelp(player);
                } else {
                    if (args[0].equalsIgnoreCase("help")) {
                        sendHelp(player);
                    } else {
                        if (args[0].equalsIgnoreCase("create") && !isInAdminMode) {
                            new CreateArg().execute(player, args);
                        } else if (args[0].equalsIgnoreCase("desc") && !isInAdminMode) {
                            new DescArg().execute(player, args);
                        } else if (args[0].equalsIgnoreCase("rename") && !isInAdminMode) {
                            new RenameArg().execute(player, args);
                        } else {
                            if (args.length == 1) {
                                if (args[0].equalsIgnoreCase("claim") && !isInAdminMode) {
                                    new ClaimArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("map") && !isInAdminMode) {
                                    new MapArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("unclaim") && !isInAdminMode) {
                                    new UnClaimArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("disband") && !isInAdminMode) {
                                    new DisbandArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("show") && !isInAdminMode) {
                                    new ShowArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("power") && !isInAdminMode) {
                                    new PowerArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("sethome") && !isInAdminMode) {
                                    new SetHomeArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("delhome") && !isInAdminMode) {
                                    new DelHomeArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("home") && !isInAdminMode) {
                                    new HomeArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("leave") && !isInAdminMode) {
                                    new LeaveArg().execute(player, args);
                                } else {
                                    if (player.hasPermission("guilde.admin")) {
                                        if (args[0].equalsIgnoreCase("admin")) {
                                            new AdminArg().execute(player, args);
                                        } else {
                                            if (isInAdminMode) {
                                                if (args[0].equalsIgnoreCase("unclaim")) {
                                                    new AdminUnClaimArg().execute(player, args);
                                                }
                                            }
                                        }
                                    } else {
                                        sendHelp(player);
                                    }
                                }
                            } else if (args.length == 2) {
                                if (args[0].equalsIgnoreCase("list") && !isInAdminMode) {
                                    new ListArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("unclaim") && args[1].equalsIgnoreCase("all")
                                        && !isInAdminMode) {
                                    new UnClaimAllArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("show") && !isInAdminMode) {
                                    new ShowPlayerArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("manage") && !isInAdminMode) {
                                    new ManagePlayerArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("invite") && !isInAdminMode) {
                                    new InviteArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("kick") && !isInAdminMode) {
                                    new KickPlayerArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("power") && !isInAdminMode) {
                                    new PlayerPowerArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("leader") && !isInAdminMode) {
                                    new LeadPlayerArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("promote") && !isInAdminMode) {
                                    new PromotePlayerArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("demote") && !isInAdminMode) {
                                    new DemotePlayerArg().execute(player, args);
                                } else if (args[0].equalsIgnoreCase("join") && !isInAdminMode) {
                                    new JoinArg().execute(player, args);
                                } else {
                                    if (player.hasPermission("guilde.admin") && isInAdminMode) {
                                        if (args[0].equalsIgnoreCase("disband")) {
                                            new AdminDisbandArg().execute(player, args);
                                        } else if (args[0].equalsIgnoreCase("kick")) {
                                            new AdminKickPlayerArg().execute(player, args);
                                        } else if (args[0].equalsIgnoreCase("claim")) {
                                            new AdminClaimArg().execute(player, args);
                                        } else if (args[0].equalsIgnoreCase("fullpower")) {
                                            new AdminFullPowerPlayerArg().execute(player, args);
                                        }
                                    } else {
                                        sendHelp(player);
                                    }
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

            if (sender.hasPermission("guilde.admin")) {
                commands.add("admin");
            }

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
            } else {
                Guild guild = null;
                if (sender instanceof Player) {
                    guild = Main.getInstance().getDataManager().getGuildDataManager()
                            .getGuildByPlayer(((Player) sender).getUniqueId());
                }

                if (args[0].equalsIgnoreCase("show")) {
                    if (sender instanceof Player) {
                        if (guild != null && !guild.isDeleted()) {
                            guild.getGuildData().getMembersUniqueId()
                                    .stream()
                                    .map(Bukkit::getOfflinePlayer)
                                    .forEach(player -> commands.add(player.getName()));
                        }
                    } else {
                        commands.addAll(Arrays.stream(Bukkit.getOfflinePlayers())
                                .map(OfflinePlayer::getName)
                                .collect(Collectors.toList()));
                    }
                } else if (args[0].equalsIgnoreCase("manage")) {
                    if (sender instanceof Player) {
                        if (guild != null && !guild.isDeleted()) {
                            guild.getGuildData().getMembersUniqueId()
                                    .stream()
                                    .map(Bukkit::getOfflinePlayer)
                                    .forEach(player -> commands.add(player.getName()));
                        }
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
                        if (guild != null && !guild.isDeleted()) {
                            guild.getGuildData().getMembersUniqueId()
                                    .stream()
                                    .map(Bukkit::getOfflinePlayer)
                                    .forEach(player -> commands.add(player.getName()));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("power")) {
                    commands.addAll(Bukkit.getOnlinePlayers()
                            .stream()
                            .map(Player::getName)
                            .collect(Collectors.toList()));
                } else if (args[0].equalsIgnoreCase("leader")) {
                    if (sender instanceof Player) {
                        if (guild != null && !guild.isDeleted()) {
                            guild.getGuildData().getMembersUniqueId()
                                    .stream()
                                    .map(Bukkit::getOfflinePlayer)
                                    .forEach(player -> commands.add(player.getName()));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("rename")) {
                    commands.add("<nom>");
                } else if (args[0].equalsIgnoreCase("promote")) {
                    if (sender instanceof Player) {
                        if (guild != null && !guild.isDeleted()) {
                            guild.getGuildData().getMembersUniqueId()
                                    .stream()
                                    .map(Bukkit::getOfflinePlayer)
                                    .forEach(player -> commands.add(player.getName()));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("demote")) {
                    if (sender instanceof Player) {
                        if (guild != null && !guild.isDeleted()) {
                            guild.getGuildData().getMembersUniqueId()
                                    .stream()
                                    .map(Bukkit::getOfflinePlayer)
                                    .forEach(player -> commands.add(player.getName()));
                        }
                    }
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
