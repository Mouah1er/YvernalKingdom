package fr.yvernal.yvernalkingdom.commands.guild.args.one;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.commands.YvernalArg;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HomeArg extends YvernalArg {

    @Override
    public void execute(Player player, String[] args) {
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());
        final Guild playerGuild = playerAccount.getGuild();

        if (playerIsInGuildWithMessage(player, playerGuild, playerAccount)) {
            if (playerGuild.getGuildData().getHome() == null) {
                player.sendMessage(messagesManager.getString("guild-home-not-set"));
            } else {
                playerAccount.setWaitingToTeleportToHome(true);

                new BukkitRunnable() {
                    int timer = 0;

                    @Override
                    public void run() {
                        if (playerAccount.isWaitingToTeleportToHome()) {
                            final int timeToTeleport = Main.getInstance().getConfigManager().getGameConfigManager()
                                    .get("time-to-teleport-to-guild-home", int.class);

                            player.sendMessage(messagesManager.getString("teleporting-to-home")
                                    .replace("%time%", String.valueOf(timeToTeleport - timer)));

                            if (timer == timeToTeleport) {
                                player.teleport(playerGuild.getGuildData().getHome());
                                playerAccount.setWaitingToTeleportToHome(false);
                                player.sendMessage(messagesManager.getString("teleported-to-home"));
                                this.cancel();
                            }
                        } else {
                            this.cancel();
                        }

                        timer++;
                    }
                }.runTaskTimer(Main.getInstance(), 0, 20);
            }
        }
    }
}
