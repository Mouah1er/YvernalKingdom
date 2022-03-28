package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.chat.ChatFormat;
import fr.yvernal.yvernalkingdom.chat.ChatMode;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.utils.GroupManagerHook;
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

            Bukkit.broadcastMessage(ChatFormat.format(player, event.getMessage(), ChatMode.GLOBAL));
        }
    }
}
