package fr.yvernal.yvernalkingdom.chat;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.utils.GroupManagerHook;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatFormat {

    public static String format(Player player, String message, ChatMode chatMode) {
        String toFormat = message;
        final PlayerAccount playerAccount = Main.getInstance().getDataManager().getPlayerAccountManager()
                .getPlayerAccount(player.getUniqueId());
        final GroupManagerHook groupManagerHook = Main.getInstance().getGroupManagerHook();

        if (chatMode == ChatMode.KINGDOM) {
            toFormat = ChatColor.translateAlternateColorCodes('&',
                    Main.getInstance().getConfigManager().getGameConfigManager().getString("kingdom-chat-format")
                            .replace("%{guild_rank_prefix}%", playerAccount.getGuildRank().getPrefix())
                            .replace("%{guild}%", playerAccount.getGuild() == null ? "§2Wilderness" : playerAccount.getGuild().getGuildData().getName())
                            .replace("%{player_grade}%", groupManagerHook.getGroup(player))
                            .replace("%{name}%", player.getName())
                            .replace("%{message}%", message));
        } else if (chatMode == ChatMode.GLOBAL) {
            final Kingdom playerKingdom = Main.getInstance().getDataManager().getKingdomDataManager()
                    .getKingdomByUniqueId(player.getUniqueId());

            toFormat = ChatColor.translateAlternateColorCodes('&',
                    Main.getInstance().getConfigManager().getGameConfigManager().getString("global-chat-format")
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
