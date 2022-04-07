package fr.yvernal.yvernalkingdom.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.kitteh.tag.TagAPI;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TagUtils {
    public static final Map<UUID, Pair<UUID, String>> tagMap = new HashMap<>();

    public static void handlePlayerDisplayName(Player player) {
        final DataManager dataManager = Main.getInstance().getDataManager();
        final Kingdom playerKingdom = dataManager.getKingdomDataManager().getKingdomByUniqueId(player.getUniqueId());

        if (playerKingdom == null) {
            Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(player1 -> !player1.getUniqueId().equals(player.getUniqueId()))
                    .forEach(targetPlayer -> {
                        player.setDisplayName("§e" + player.getName());
                        targetPlayer.setDisplayName("§e" + targetPlayer.getName());

                        final GameProfile playerProfile = ((CraftPlayer) player).getProfile();
                        final String playerSkinFromName = TagUtils.getSkinFromName(player.getName());

                        final GameProfile targetPlayerProfile = ((CraftPlayer) targetPlayer).getProfile();
                        final String targetPlayerSkinFromName = TagUtils.getSkinFromName(targetPlayer.getName());

                        if (playerSkinFromName != null && targetPlayerSkinFromName != null) {
                            playerProfile.getProperties().clear();
                            targetPlayerProfile.getProperties().clear();
                            playerProfile.getProperties().put("textures", new Property("textures", playerSkinFromName));
                            targetPlayerProfile.getProperties().put("textures", new Property("textures", targetPlayerSkinFromName));
                        }

                        tagMap.put(player.getUniqueId(), Pair.of(player.getUniqueId(), player.getDisplayName()));
                        tagMap.put(targetPlayer.getUniqueId(), Pair.of(targetPlayer.getUniqueId(), targetPlayer.getDisplayName()));

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                TagAPI.refreshPlayer(player, targetPlayer);
                                TagAPI.refreshPlayer(targetPlayer, player);
                            }
                        }.runTaskLater(Main.getInstance(), 20);
                    });
        } else {
            Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(player1 -> !player1.getUniqueId().equals(player.getUniqueId()))
                    .forEach(targetPlayer -> {
                        final Kingdom targetPlayerKingdom = dataManager.getKingdomDataManager().getKingdomByUniqueId(targetPlayer.getUniqueId());

                        String playerDisplayName = "§c" + player.getName();
                        String targetPlayerDisplayName = "§c" + targetPlayer.getName();

                        if (targetPlayerKingdom == null) {
                            playerDisplayName = "§e" + player.getName();
                            targetPlayerDisplayName = "§e" + targetPlayer.getName();
                        } else {
                            if (targetPlayerKingdom.getKingdomData().getPlayersIn().contains(player.getUniqueId())) {
                                playerDisplayName = "§a" + player.getName();
                                targetPlayerDisplayName = "§a" + targetPlayer.getName();
                            }
                        }

                        player.setDisplayName(playerDisplayName);
                        targetPlayer.setDisplayName(targetPlayerDisplayName);

                        final GameProfile playerProfile = ((CraftPlayer) player).getProfile();
                        final String playerSkinFromName = TagUtils.getSkinFromName(player.getName());
                        System.out.println(playerSkinFromName);

                        final GameProfile targetPlayerProfile = ((CraftPlayer) targetPlayer).getProfile();
                        final String targetPlayerSkinFromName = TagUtils.getSkinFromName(targetPlayer.getName());
                        System.out.println(targetPlayerSkinFromName);

                        if (playerSkinFromName != null && targetPlayerSkinFromName != null) {
                            playerProfile.getProperties().clear();
                            targetPlayerProfile.getProperties().clear();
                            
                            playerProfile.getProperties().put("textures", new Property("textures", playerSkinFromName));
                            targetPlayerProfile.getProperties().put("textures", new Property("textures", targetPlayerSkinFromName));
                        }

                        tagMap.put(player.getUniqueId(), Pair.of(player.getUniqueId(), player.getDisplayName()));
                        tagMap.put(targetPlayer.getUniqueId(), Pair.of(targetPlayer.getUniqueId(), targetPlayer.getDisplayName()));

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                TagAPI.refreshPlayer(player, targetPlayer);
                                TagAPI.refreshPlayer(targetPlayer, player);
                            }
                        }.runTaskLater(Main.getInstance(), 20);
                    });
        }
    }

    // trouvé sur spigot psk flemme
    public static String getSkinFromName(String name) {
        try {
            final URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            final InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
            final String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();
            System.out.println(uuid);

            final URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
            final InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            final JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0)
                    .getAsJsonObject();

            return textureProperty.get("value").getAsString();
        } catch (IOException e) {
            System.err.println("Could not get skin data from session servers!");
            e.printStackTrace();
            return null;
        }
    }
}
