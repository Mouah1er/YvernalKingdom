package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.chat.ChatFormat;
import fr.yvernal.yvernalkingdom.chat.ChatMode;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.concurrent.CompletableFuture;

public class AsyncPlayerChatListener extends YvernalListener<AsyncPlayerChatEvent> {

    @Override
    @EventHandler
    public void onEvent(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();

        if (Main.getInstance().getMessageWaiter().containsKey(player.getUniqueId())) {
            final CompletableFuture<String> completableFuture = Main.getInstance().getMessageWaiter().get(player.getUniqueId());
            completableFuture.complete(event.getMessage());
            event.setCancelled(true);
        } else {
            event.setCancelled(true);

            if (event.getMessage().startsWith("@")) {
                Bukkit.broadcastMessage(ChatFormat.format(player, event.getMessage(), ChatMode.GLOBAL));
            } else {
                final Kingdom kingdom = Main.getInstance().getDataManager().getKingdomDataManager().getKingdomByUniqueId(player.getUniqueId());

                if (kingdom != null) {
                    kingdom.getKingdomData().getPlayersIn().stream()
                            .map(Bukkit::getPlayer)
                            .forEach(targetPlayer -> targetPlayer.sendMessage(ChatFormat.format(player, event.getMessage(), ChatMode.KINGDOM)));
                } else {
                    player.sendMessage(messagesManager.getString("discord-link"));
                }
            }
        }
    }
}
