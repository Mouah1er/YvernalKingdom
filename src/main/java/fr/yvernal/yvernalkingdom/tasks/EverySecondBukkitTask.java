package fr.yvernal.yvernalkingdom.tasks;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.messages.MessagesManager;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.utils.locations.Cuboid;
import fr.yvernal.yvernalkingdom.utils.locations.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

public class EverySecondBukkitTask extends BukkitRunnable {

    @Override
    public void run() {
        rebuildCrystal();
    }

    private void rebuildCrystal() {
        Main.getInstance().getDataManager().getCrystalDataManager().getCrystals().forEach(crystal -> {
            if (crystal.getCrystalData().getDestructionDate() != null) {
                final LocalDateTime destructionDate = LocalDateTime.ofInstant(crystal.getCrystalData().getDestructionDate().toInstant(),
                        ZoneId.of("Europe/Paris"));
                final LocalDateTime now = LocalDateTime.now();

                final Kingdom kingdom = crystal.getCrystalData().getKingdom();

                final MessagesManager messagesManager = Main.getInstance().getConfigManager().getMessagesManager();

                if (now.isEqual(destructionDate.plusHours(1))) {
                    kingdom.getKingdomData().getGuildsIn().forEach(guild -> guild.getGuildData().getClaims().forEach(claim -> claim.setActivated(true)));

                    Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(messagesManager.getString("reactivated-claim-success")
                            .replace("%kingdom%", kingdom.getKingdomProperties().getName())));
                }

                if (now.isEqual(destructionDate.plusDays(2))) {
                    crystal.getCrystalData().setDestroyed(false);
                    crystal.getCrystalData().setDestructionDate(null);
                    crystal.getCrystalData().setHealth(crystal.getCrystalData().getMaxHealth());

                    Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(messagesManager
                            .getString("crystal-revived-success")
                            .replace("%kingdom%", kingdom.getKingdomProperties().getName())));
                } else {
                    final List<Location> cube = LocationUtils.cube(
                            new Cuboid(
                                    kingdom.getKingdomProperties().getCrystalLocation()
                                            .clone()
                                            .add(1, 0, 1),
                                    kingdom.getKingdomProperties().getCrystalLocation()
                                            .clone()
                                            .add(-1, 3.5, -1)
                            )
                    );

                    final ParticleBuilder particleBuilder = new ParticleBuilder(ParticleEffect.VILLAGER_HAPPY);
                    Collections.shuffle(cube);

                    final List<Location> locations = cube.subList(0, 5);

                    locations.forEach(location -> {
                        particleBuilder.setLocation(location);
                        particleBuilder.display();
                    });
                }
            }
        });
    }
}
