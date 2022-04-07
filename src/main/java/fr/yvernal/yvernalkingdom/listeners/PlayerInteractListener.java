package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener extends YvernalListener<PlayerInteractEvent> {

    @Override
    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_BLOCK) {
            final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

            if (event.getItem() != null) {
                final Material itemType = event.getItem().getType();
                final Material blockType = event.getClickedBlock().getType();

                if ((itemType == Material.MONSTER_EGG && event.getItem().getDurability() == 50) ||
                        (blockType == Material.TNT && itemType == Material.FLINT_AND_STEEL) ||
                        (itemType == Material.EXPLOSIVE_MINECART && blockType.name().contains("RAIL"))) {
                    event.setCancelled(true);

                    Entity entitytoSpawn;
                    if (itemType == Material.MONSTER_EGG) {
                        entitytoSpawn = player.getWorld().spawnEntity(event.getClickedBlock().getLocation(), EntityType.CREEPER);
                    } else if (itemType == Material.EXPLOSIVE_MINECART) {
                        entitytoSpawn = player.getWorld().spawnEntity(event.getClickedBlock().getLocation(), EntityType.MINECART_TNT);
                    } else {
                        event.getClickedBlock().setType(Material.AIR);
                        entitytoSpawn = player.getWorld().spawnEntity(event.getClickedBlock().getLocation(), EntityType.PRIMED_TNT);
                    }

                    if (entitytoSpawn != null)
                        Main.getInstance().getSpawnedCreepersByPlayer().put(entitytoSpawn.getUniqueId(), playerAccount);
                }
            } else {
                final Claim claim = dataManager.getClaimManager().getClaimAt(event.getClickedBlock().getChunk().getX(),
                        event.getClickedBlock().getChunk().getZ());

                if (claim != null && !claim.isUnClaim()) {
                    if (!claim.getClaimData().getGuildUniqueId().equals(playerAccount.getGuildUniqueId())) {
                        event.setCancelled(true);
                        player.sendMessage(messagesManager.getString("player-cant-interact-in-claim"));
                    }
                }
            }
        }
    }
}
