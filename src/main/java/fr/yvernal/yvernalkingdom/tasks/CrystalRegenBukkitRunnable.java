package fr.yvernal.yvernalkingdom.tasks;

import fr.yvernal.yvernalkingdom.Main;
import org.bukkit.scheduler.BukkitRunnable;

public class CrystalRegenBukkitRunnable extends BukkitRunnable {

    @Override
    public void run() {
        final Main main = Main.getInstance();

        main.getDataManager().getCrystalDataManager().getCrystals().forEach(crystal -> {
            final double crystalHealth = crystal.getCrystalData().getHealth();

            if (crystalHealth < crystal.getCrystalData().getMaxHealth()) {
                if (!crystal.getCrystalData().isDestroyed()) {
                    crystal.getCrystalData().setHealth(crystalHealth +
                            main.getConfigManager().getGameConfigManager().get("crystal-regen-health", double.class));
                }
            }
        });
    }
}
