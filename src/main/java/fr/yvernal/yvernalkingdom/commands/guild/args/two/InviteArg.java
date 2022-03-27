package fr.yvernal.yvernalkingdom.commands.guild.args.two;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.invitedplayers.InvitedPlayerData;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.invitedplayers.InvitedPlayer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class InviteArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final Player targetPlayer = Bukkit.getPlayer(args[1]);

        if (targetPlayer == null) {
            player.sendMessage(messagesManager.getString("player-not-found"
                    .replace("%player%", args[1])));
        } else {
            if (targetPlayer.getUniqueId().equals(player.getUniqueId())) {
                player.sendMessage(messagesManager.getString("cant-invite-yourself"));
            } else {
                final Guild playerGuild = dataManager.getGuildDataManager().getGuildByPlayer(player.getUniqueId());
                final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());
                final PlayerAccount targetPlayerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(targetPlayer.getUniqueId());

                if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
                    if (!guildRankIsMemberWithMessage(player, playerAccount)) {
                        if (playerIsInGuild(Main.getInstance().getDataManager().getGuildDataManager().getGuildByPlayer(targetPlayer.getUniqueId()),
                                targetPlayerAccount)) {
                            player.sendMessage(messagesManager.getString("certain-player-not-in-guild")
                                    .replace("%player%", targetPlayer.getName()));
                        } else {
                            if (!targetPlayerAccount.getKingdomName().equals(playerAccount.getKingdomName())) {
                                player.sendMessage(messagesManager.getString("certain-player-not-in-kingdom")
                                        .replace("%player%", targetPlayer.getName()));
                            } else {
                                if (playerGuild.getGuildData().getMembersUniqueId().size() > 10) {
                                    player.sendMessage(messagesManager.getString("guild-full"));
                                } else {
                                    InvitedPlayer invitedPlayer = dataManager.getInvitedPlayerDataManager()
                                            .getInvitedPlayerByUniqueId(targetPlayer.getUniqueId());

                                    if (invitedPlayer == null) {
                                        invitedPlayer = new InvitedPlayer(
                                                new InvitedPlayerData(targetPlayer.getUniqueId(),
                                                        UUID.fromString(playerGuild.getGuildData().getGuildUniqueId())), true,
                                                true);
                                        dataManager.getInvitedPlayerDataManager().getInvitedPlayers().add(invitedPlayer);
                                    } else {
                                        if (invitedPlayer.isStillInvited()) {
                                            player.sendMessage(messagesManager.getString("certain-player-already-invited-in-guild")
                                                    .replace("%player%", targetPlayer.getName()));

                                            return;
                                        }
                                    }

                                    final TextComponent messageTextComponent = new TextComponent(messagesManager
                                            .getString("player-invited-in-guild")
                                            .replace("%guilde%", playerGuild.getGuildData().getName())
                                            .replace("%player%", player.getName()));
                                    messageTextComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/g join " +
                                            playerGuild.getGuildData().getName()));
                                    messageTextComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{
                                            new TextComponent("§aClique pour rejoindre !")
                                    }));

                                    targetPlayer.spigot().sendMessage(messageTextComponent);
                                    player.sendMessage(messagesManager.getString("successfully-invited-player-in-guild")
                                            .replace("%player%", targetPlayer.getName()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
