package fr.yvernal.yvernalkingdom.utils;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.kingdoms.crystal.Crystal;
import fr.yvernal.yvernalkingdom.kingdoms.crystal.CrystalHologram;
import fr.yvernal.yvernalkingdom.tasks.CrystalRegenBukkitRunnable;
import fr.yvernal.yvernalkingdom.tasks.RebuildCrystalBukkitRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

import java.util.concurrent.atomic.AtomicBoolean;

public class CrystalUtils {

    public static void spawnCrystals() {
        Main.getInstance().getDataManager().getCrystalDataManager().getCrystals()
                .stream()
                .map(crystal -> crystal.getCrystalData().getLocation())
                .forEach(CrystalUtils::spawnCrystal);
    }

    public static void spawnCrystal(Location crystalLocation) {
        if (!crystalLocation.getChunk().isLoaded()) crystalLocation.getChunk().load();

        final Crystal crystal = Main.getInstance().getDataManager().getCrystalDataManager().getCrystalByLocation(crystalLocation);

        if (crystal != null) {
            Bukkit.getWorld(Main.getInstance().getConfigManager().getGameConfigManager().getString("world-name"))
                    .spawnEntity(crystalLocation, EntityType.ENDER_CRYSTAL);

            startUpdateCrystalHologramTask(crystal);
        }
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
