package fr.yvernal.yvernalkingdom.commands.args;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.messages.MessagesManager;
import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.GuildData;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.GuildRank;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.UUID;

public class CreateArg implements YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final DataManager dataManager = Main.getInstance().getDataManager();
        final MessagesManager messagesManager = Main.getInstance().getConfigManager().getMessagesManager();
        final Guild playerGuild = dataManager.getGuildDataManager().getGuild(player.getUniqueId());
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

        if (playerGuild != null && playerAccount.getGuildRank() != GuildRank.NO_GUILD) {
            player.sendMessage(messagesManager.getString("already-in-guild"));
        } else {
            final StringBuilder stringBuilder = new StringBuilder();

            for (int i = 1; i < args.length; i++) {
                stringBuilder.append(args[i]).append(" ");
            }

            if (stringBuilder.length() > 16) {
                player.sendMessage(messagesManager.getString("guild-name-too-big"));
            } else {
                final String guildUniqueId = UUID.randomUUID().toString();
                final Guild guild = new Guild(new GuildData(guildUniqueId, playerAccount.getKingdomName(), stringBuilder.toString(),
                        "", 10, null, player.getUniqueId(), Collections.singletonList(player.getUniqueId())),
                        false, true);

                dataManager.getGuildDataManager().getGuilds().add(guild);
                dataManager.getKingdomDataManager().getKingdom(playerAccount.getKingdomName()).getKingdomData().getGuildsIn().add(guild);
                playerAccount.setGuildName(stringBuilder.toString());
                playerAccount.setGuildRank(GuildRank.MASTER);
                playerAccount.setGuildUniqueId(guildUniqueId);

                player.sendMessage(messagesManager.getString("successfully-created-guild")
                        .replace("%guild%", stringBuilder.toString()));
            }
        }
    }
}
