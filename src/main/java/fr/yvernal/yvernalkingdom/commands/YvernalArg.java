package fr.yvernal.yvernalkingdom.commands;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.messages.MessagesManager;
import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import org.bukkit.entity.Player;

public abstract class YvernalArg {
    protected final DataManager dataManager = Main.getInstance().getDataManager();
    protected final MessagesManager messagesManager = Main.getInstance().getConfigManager().getMessagesManager();

    public abstract void execute(Player player, String[] args);

    protected boolean playerIsInGuildWithMessage(Player player, Guild playerGuild, PlayerAccount playerAccount) {
        if (!playerIsInGuild(playerGuild, playerAccount)) {
            player.sendMessage(messagesManager.getString("player-not-in-guild"));

            return false;
        }

        return true;
    }

    protected boolean playerIsInGuild(Guild playerGuild, PlayerAccount playerAccount) {
        return (playerAccount.getGuildRank() != GuildRank.NO_GUILD || playerGuild != null) &&
                (!playerGuild.isDeleted() || playerAccount.getGuildRank() != GuildRank.NO_GUILD);
    }

    private boolean checkPlayerGuildRank(PlayerAccount playerAccount, GuildRank guildRank) {
        return playerAccount.getGuildRank() == guildRank;
    }

    protected boolean guildRankIsMaster(Player player, PlayerAccount playerAccount) {
        if (checkPlayerGuildRank(playerAccount, GuildRank.MASTER)) {
            player.sendMessage("player-guild-permission-error");

            return false;
        }

        return true;
    }

    protected boolean guildRankIsMember(Player player, PlayerAccount playerAccount) {
        if (checkPlayerGuildRank(playerAccount, GuildRank.MEMBER)) {
            player.sendMessage("player-guild-permission-error");

            return false;
        }

        return true;
    }

    protected String joinArgs(String[] args) {
        final StringBuilder stringBuilder = new StringBuilder();

        String prefix = "";
        for (int i = 1; i < args.length; i++) {
            stringBuilder.append(prefix);
            prefix = " ";
            stringBuilder.append(args[i]);
        }

        return stringBuilder.toString();
    }
}
