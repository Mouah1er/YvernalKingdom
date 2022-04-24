package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.events.CrystalKilledByPlayerEvent;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.crystal.Crystal;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CrystalKilledByPlayerListener extends YvernalListener<CrystalKilledByPlayerEvent> {

    @Override
    @EventHandler
    public void onEvent(CrystalKilledByPlayerEvent event) {
        final Player player = event.getPlayer();
        final Crystal crystal = event.getCrystal();
        final Kingdom kingdom = crystal.getCrystalData().getKingdom();

        crystal.getCrystalData().setHealth(0);
        crystal.getCrystalData().setDestroyed(true);
        crystal.getCrystalData().setDestructionDate(new Date(System.currentTimeMillis()));

        Bukkit.getOnlinePlayers().forEach(player1 -> {
            player1.sendMessage(messagesManager.getString("player-destroyed-crystal")
                    .replace("%player%", player.getName())
                    .replace("%kingdom%", kingdom.getKingdomProperties().getName())
                    .replace("%date%", new SimpleDateFormat("dd/MM/yyyy").format(crystal.getCrystalData().getDestructionDate())));

            player1.sendTitle(messagesManager.getString("player-destroyed-crystal-title")
                            .replace("%player%", player.getName())
                            .replace("%kingdom%", kingdom.getKingdomProperties().getName()),
                    messagesManager.getString("player-destroyed-crystal-subtitle")
                            .replace("%kingdom%", kingdom.getKingdomProperties().getName())
                            .replace("%player%", player.getName()));
        });

        // launch dragon death animation
        final EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld) player.getLocation().getWorld()).getHandle());
        final Location crystalLocation = kingdom.getKingdomProperties().getCrystalLocation();

        dragon.setLocation(crystalLocation.getX(), crystalLocation.getY(), crystalLocation.getZ(), 0, 0);

        final PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving(dragon);
        final PacketPlayOutEntityStatus statusPacket = new PacketPlayOutEntityStatus(dragon, (byte) 3);
        final PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(dragon.getId());

        Bukkit.getOnlinePlayers().forEach(player1 -> {
            ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(spawnPacket);
            ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(statusPacket);


            // destroy the dragon after 7 seconds
            new BukkitRunnable() {

                @Override
                public void run() {
                    ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(destroyPacket);
                }
            }.runTaskLater(Main.getInstance(), 20 * 7);
        });

        kingdom.getKingdomData().getGuildsIn().forEach(guild -> guild.getGuildData().getClaims().forEach(claim -> claim.setActivated(false)));
    }
}