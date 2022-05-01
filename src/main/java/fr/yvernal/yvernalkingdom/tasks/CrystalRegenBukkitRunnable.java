package fr.yvernal.yvernalkingdom.tasks;

import fr.yvernal.yvernalkingdom.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class CrystalRegenBukkitRunnable extends BukkitRunnable {

    @Override
    public void run() {
        final Main main = Main.getInstance();

        main.getDataManager().getCrystalDataManager().getCrystals().forEach(crystal -> {
            if (crystal.getCrystalData().getHealth() != crystal.getCrystalData().getMaxHealth()) {
                if (!crystal.getCrystalData().isDestroyed()) {
                    crystal.getCrystalData().setHealth(crystal.getCrystalData().getHealth() +
                            main.getConfigManager().getGameConfigManager().get("crystal-regen-health", double.class));
                }
            }
        });
    }
}
