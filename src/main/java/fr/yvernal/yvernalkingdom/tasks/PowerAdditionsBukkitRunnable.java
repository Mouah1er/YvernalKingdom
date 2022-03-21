package fr.yvernal.yvernalkingdom.tasks;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.GuildData;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PowerAdditionsBukkitRunnable extends BukkitRunnable {
    private final Player player;
    private final PlayerAccount playerAccount;

    private int timer;

    public PowerAdditionsBukkitRunnable(Player player, PlayerAccount playerAccount) {
        this.player = player;
        this.playerAccount = playerAccount;
    }

    @Override
    public void run() {
        timer++;

        if (timer == 7200) { // 2 heures
            if (playerAccount.getPower() != 10) {
                playerAccount.setPower(playerAccount.getPower() + 2);
                final Guild playerGuild = Main.getInstance().getDataManager().getGuildDataManager()
                        .getGuildByPlayer(player.getUniqueId());
                if (playerGuild != null) {
                    playerGuild.getGuildData().setPower(playerGuild.getGuildData().getPower() + 2);
                }
                player.sendMessage(Main.getInstance().getConfigManager().getMessagesManager().getString("added-power-to-player"));
            }

            timer = 0;
        }
    }
}
