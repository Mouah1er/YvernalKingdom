package fr.yvernal.yvernalkingdom.listeners;

import fr.twah2em.nametag.Relation;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.events.CrystalDamageByPlayerEvent;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener extends YvernalListener<EntityDamageByEntityEvent> {

    @Override
    @EventHandler
    public void onEvent(EntityDamageByEntityEvent event) {
        final Entity entity = event.getEntity();
        final Entity damager = event.getDamager();

        if (entity instanceof Player && damager instanceof Player) {
            final PlayerAccount playerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(entity.getUniqueId());
            final PlayerAccount damagerAccount = dataManager.getPlayerAccountManager().getPlayerAccount(damager.getUniqueId());

            if (playerAccount.getKingdom().getKingdomProperties().getNumber().equals(damagerAccount.getKingdom().getKingdomProperties().getNumber())) {
                event.setCancelled(true);
                damager.sendMessage(messagesManager.getString("player-cant-attack-kingdom-member")
                        .replace("%player%", entity.getName()));
            } else {
                final Kingdom kingdomAt = dataManager.getKingdomDataManager().getKingdomByLocation(entity.getLocation());
                final Kingdom damagerKingdom = damagerAccount.getKingdom();
                final Kingdom playerKingdom = playerAccount.getKingdom();

                if (kingdomAt != null && damagerKingdom != null && playerKingdom != null) {
                    if (playerAccount.getRelationWith(damagerAccount) == Relation.ENEMY) {
                        if (kingdomAt.getKingdomProperties().getTotalTerritoryCuboid().isIn(entity) ||
                                kingdomAt.getKingdomProperties().getTotalTerritoryCuboid().isIn(damager)) {
                            if (kingdomAt.getKingdomData().getPlayersIn().contains(damager.getUniqueId()) &&
                                    !kingdomAt.getKingdomData().getPlayersIn().contains(entity.getUniqueId())) {
                                if (!playerAccount.getPlayerWithWhomInBattle().contains(damager.getUniqueId()) &&
                                        !damagerAccount.getPlayerWithWhomInBattle().contains(entity.getUniqueId())) {
                                    playerAccount.getPlayerWithWhomInBattle().add(damager.getUniqueId());
                                    damagerAccount.getPlayerWithWhomInBattle().add(entity.getUniqueId());
                                    entity.sendMessage(messagesManager.getString("player-in-battle")
                                            .replace("%player%", damager.getName()));
                                    damager.sendMessage(messagesManager.getString("player-in-battle")
                                            .replace("%player%", entity.getName()));
                                }
                            } else {
                                if (!playerAccount.getPlayerWithWhomInBattle().contains(damager.getUniqueId()) &&
                                        !damagerAccount.getPlayerWithWhomInBattle().contains(entity.getUniqueId())) {
                                    damager.sendMessage(messagesManager.getString("player-cant-attack-because-not-in-battle")
                                            .replace("%player%", entity.getName()));
                                    event.setCancelled(true);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (entity instanceof EnderCrystal && damager instanceof Player) {
            final Kingdom kingdomAt = dataManager.getKingdomDataManager().getKingdomByLocation(entity.getLocation());

            final CrystalDamageByPlayerEvent crystalDamageByPlayerEvent = new CrystalDamageByPlayerEvent((Player) damager, kingdomAt.getCrystal(),
                    event.getFinalDamage());

            Bukkit.getPluginManager().callEvent(crystalDamageByPlayerEvent);

            event.setCancelled(crystalDamageByPlayerEvent.isCancelled());
        }
    }
}