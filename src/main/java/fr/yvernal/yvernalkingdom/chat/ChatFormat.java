package fr.yvernal.yvernalkingdom.chat;

import fr.twah2em.nametag.Relation;
import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.utils.GroupManagerHook;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatFormat {

    public static String format(Player player, Player receiver, String message, ChatMode chatMode) {
        String toFormat = message;
        final PlayerAccount playerAccount = Main.getInstance().getDataManager().getPlayerAccountManager()
                .getPlayerAccount(player.getUniqueId());
        final PlayerAccount receiverAccount = Main.getInstance().getDataManager().getPlayerAccountManager()
                .getPlayerAccount(receiver.getUniqueId());
        final GroupManagerHook groupManagerHook = Main.getInstance().getGroupManagerHook();
        final Kingdom playerKingdom = Main.getInstance().getDataManager().getKingdomDataManager()
                .getKingdomByUniqueId(player.getUniqueId());

        if (chatMode == ChatMode.KINGDOM) {
            toFormat = ChatColor.translateAlternateColorCodes('&',
                    Main.getInstance().getConfigManager().getGameConfigManager().getString("kingdom-chat-format")
                            .replace("%{guild_rank_prefix}%", playerAccount.getGuildRank().getPrefix())
                            .replace("%{guild}%", playerAccount.getGuild() == null ? "§2Wilderness" : playerAccount.getGuild().getGuildData().getName())
                            .replace("%{player_grade}%", groupManagerHook.getPrefix(player) + groupManagerHook.getGroup(player))
                            .replace("%{kingdom_color}%", playerKingdom.getKingdomProperties().getColor().toString())
                            .replace("%{name}%", player.getName())
                            .replace("%{message}%", message));
        } else if (chatMode == ChatMode.GLOBAL) {
            final Relation playersRelation = playerAccount.getRelationWith(receiverAccount);
            message = message.substring(1);
            message = "§8" + Main.getInstance().getConfigManager().getGameConfigManager().getString("global-chat-prefix") +
                    playerKingdom.getKingdomProperties().getColor().toString() + message;

            toFormat = ChatColor.translateAlternateColorCodes('&',
                    Main.getInstance().getConfigManager().getGameConfigManager().getString("global-chat-format")
                            .replace("%{relation_color}%", playersRelation.getPrefix())
                            .replace("%{guild_rank_prefix}%", playerAccount.getGuildRank().getPrefix())
                            .replace("%{guild}%", playerAccount.getGuild() == null ? "§2Wilderness" : playerAccount.getGuild().getGuildData().getName())
                            .replace("%{player_grade}%", groupManagerHook.getPrefix(player) + groupManagerHook.getGroup(player))
                            .replace("%{name}%", player.getName())
                            .replace("%{kingdom_color}%", playerKingdom.getKingdomProperties().getColor().toString())
                            .replace("%{message}%", message));
        }

        return toFormat;
    }
}
