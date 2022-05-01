package fr.yvernal.yvernalkingdom.utils;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.kingdoms.crystal.Crystal;
import fr.yvernal.yvernalkingdom.kingdoms.crystal.CrystalHologram;
import fr.yvernal.yvernalkingdom.tasks.CrystalRegenBukkitRunnable;
import fr.yvernal.yvernalkingdom.tasks.RebuildCrystalBukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class CrystalUtils {

    public static void spawnCrystals() {
        Main.getInstance().getDataManager().getCrystalDataManager().getCrystals().forEach(crystal -> {
            final Location crystalLocation = crystal.getCrystalData().getKingdom().getKingdomProperties().getCrystalLocation();

            if (!crystalLocation.getChunk().isLoaded()) crystalLocation.getChunk().load();

            Bukkit.getWorld(Main.getInstance().getConfigManager().getGameConfigManager().getString("world-name"))
                    .spawnEntity(crystalLocation, EntityType.ENDER_CRYSTAL);

            startUpdateCrystalHologramTask(crystal);
        });
    }

    public static void startCrystalRunnables() {
        new RebuildCrystalBukkitRunnable().runTaskTimerAsynchronously(Main.getInstance(), 0, 20);
        new CrystalRegenBukkitRunnable().runTaskTimerAsynchronously(Main.getInstance(), 0,
                Main.getInstance().getConfigManager().getGameConfigManager().get("crystal-regen-time", long.class) * 20);
    }

    private static void startUpdateCrystalHologramTask(Crystal crystal) {
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> updateCrystalHologram(crystal), 0, 20);
    }

    private static void updateCrystalHologram(Crystal crystal) {
        addCrystalHologram(crystal);
    }

    private static void addCrystalHologram(Crystal crystal) {
        final CrystalHologram crystalHologram = new CrystalHologram(crystal);

        crystalHologram.setLevelLine();
        crystalHologram.setCrystalNameHologramLine();
        crystalHologram.setHealthHologramLine();
    }
}
