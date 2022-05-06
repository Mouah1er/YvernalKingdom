package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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

                    final Kingdom kingdomAt = dataManager.getKingdomDataManager().getKingdomByLocation(event.getClickedBlock().getLocation());
                    final Location spawn = event.getClickedBlock().getLocation()
                            .clone()
                            .add(0, 2, 0);

                    Entity entityToSpawn = null;
                    if (itemType == Material.MONSTER_EGG) {
                        if (!kingdomAt.getKingdomProperties().getSpawnCuboid().isIn(event.getClickedBlock().getLocation())) {
                            entityToSpawn = player.getWorld().spawnEntity(spawn, EntityType.CREEPER);
                        }
                    } else if (itemType == Material.EXPLOSIVE_MINECART) {
                        if (!kingdomAt.getKingdomProperties().getSpawnCuboid().isIn(event.getClickedBlock().getLocation())) {
                            entityToSpawn = player.getWorld().spawnEntity(event.getClickedBlock().getLocation(), EntityType.MINECART_TNT);
                        }
                    } else {
                        if (!kingdomAt.getKingdomProperties().getSpawnCuboid().isIn(event.getClickedBlock().getLocation())) {
                            event.getClickedBlock().setType(Material.AIR);
                            entityToSpawn = player.getWorld().spawnEntity(event.getClickedBlock().getLocation(), EntityType.PRIMED_TNT);
                        }
                    }

                    if (entityToSpawn != null)
                        Main.getInstance().getSpawnedDangerousEntitiesByPlayer().put(entityToSpawn.getUniqueId(), playerAccount);
                }
            } else {
                final Claim claim = dataManager.getClaimManager().getClaimAt(event.getClickedBlock().getChunk().getX(),
                        event.getClickedBlock().getChunk().getZ());

                if (claim != null) {
                    if (claim.isActivated()) {
                        if (!claim.isUnClaim()) {
                            final Guild playerGuild = playerAccount.getGuild();
                            //if (event.getClickedBlock().getState() != null) {
                            if (playerGuild != null) {
                                if (!claim.getClaimData().getGuild().getGuildData().getGuildUniqueId().equals(playerGuild.getGuildData().getGuildUniqueId())) {
                                    event.setCancelled(true);
                                    //player.sendMessage(messagesManager.getString("player-cant-interact-in-claim"));
                                }
                            } else {
                                event.setCancelled(true);
                                //player.sendMessage(messagesManager.getString("player-cant-interact-in-claim"));
                            }
                            //}
                        }
                    }
                }
            }
        }
    }
}
