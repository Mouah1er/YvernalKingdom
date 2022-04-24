package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.events.CrystalDamageByPlayerEvent;
import fr.yvernal.yvernalkingdom.events.CrystalKilledByPlayerEvent;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.crystal.Crystal;
import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public class CrystalDamageByPlayerListener extends YvernalListener<CrystalDamageByPlayerEvent> {

    @Override
    @EventHandler
    public void onEvent(CrystalDamageByPlayerEvent event) {
        final Player player = event.getPlayer();
        final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

        final Crystal crystal = event.getCrystal();
        final Kingdom kingdom = crystal.getCrystalData().getKingdom();

        final LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Paris"));

        final int hour = now.getHour();
        final int minute = now.getMinute();

        event.setCancelled(true);

        if (crystal.getCrystalData().isDestroyed()) {
            player.sendMessage(messagesManager.getString("crystal-already-destroyed")
                    .replace("%kingdom%", kingdom.getKingdomProperties().getName()));
        } else {
        /*if ((hour < 18 && minute < 30) || (hour > 19 && minute > 30)) {
            player.sendMessage(messagesManager.getString("player-cant-attack-crystal-at-this-time"));
        } else {*/
            if (kingdom.getKingdomProperties().getNumber().equals(playerAccount.getKingdom().getKingdomProperties().getNumber())) {
                player.sendMessage(messagesManager.getString("player-cant-damage-crystal"));
            } else {
                crystal.getCrystalData().setHealth(crystal.getCrystalData().getHealth() - event.getDamage());

                if (!crystal.isAttacked()) {
                    crystal.setAttacked(true);

                    kingdom.getKingdomData().getPlayersIn()
                            .stream()
                            .map(Bukkit::getPlayer)
                            .filter(Objects::nonNull)
                            .forEach(player1 -> player1.sendMessage(messagesManager.getString("crystal-attacked-by-player")
                                    .replace("%player%", player.getName())
                                    .replace("%kingdom%", playerAccount.getKingdom().getKingdomProperties().getName())));

                    playerAccount.getKingdom().getKingdomData().getPlayersIn()
                            .stream()
                            .map(Bukkit::getPlayer)
                            .filter(Objects::nonNull)
                            .forEach(player1 -> player1.sendMessage(messagesManager.getString("player-attacked-crystal")
                                    .replace("%player%", player.getName())
                                    .replace("%kingdom%", kingdom.getKingdomProperties().getName())));
                }

                kingdom.getKingdomData().getPlayersIn()
                        .stream()
                        .map(Bukkit::getPlayer)
                        .filter(Objects::nonNull)
                        .forEach(player1 -> {
                            final String name = messagesManager.getString("crystal-health")
                                    .replace("%current_health%", String.valueOf(crystal.getCrystalData().getHealth()))
                                    .replace("%max_health%", String.valueOf(crystal.getCrystalData().getMaxHealth()))
                                    .replace("%heart_icon%", "❤");
                            final float health = ((float) ((crystal.getCrystalData().getHealth() / crystal.getCrystalData().getMaxHealth()) * 100) < 0 ? 0 :
                                    (float) ((crystal.getCrystalData().getHealth() / crystal.getCrystalData().getMaxHealth()) * 100));

                            BarAPI.setMessage(player1, name);
                            BarAPI.setHealth(player1, health);
                        });

                if (crystal.getCrystalData().getHealth() <= 0) {
                    final CrystalKilledByPlayerEvent crystalKilledByPlayerEvent = new CrystalKilledByPlayerEvent(player, crystal);

                    Bukkit.getPluginManager().callEvent(crystalKilledByPlayerEvent);
                }
            }
            //}
        }
    }
}
