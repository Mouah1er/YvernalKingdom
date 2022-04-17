package fr.yvernal.yvernalkingdom.utils.map;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.messages.MessagesManager;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.claims.Claim;
import fr.yvernal.yvernalkingdom.utils.GuildLocation;
import me.rayzr522.jsonmessage.JSONMessage;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author Twah2em
 * très inspirée par celle de MassiveCraft
 */
public class AsciiMap {
    private static final char[] GUILD_KEY_CHARS = "\\/#?ç£$%=&^ABCDEFGHJKLMNOPQRSTUVWXYZ1234567890abcdeghjmnopqrsuvwxyz"
            .toCharArray();

    private static final int WIDTH = 49;
    private static final int WIDTH_HALF = WIDTH / 2;
    private static final int HEIGHT = 8;
    private static final int HEIGHT_HALF = HEIGHT / 2;

    private final double angle;
    private final Map<Guild, String> guildChars = new HashMap<>();
    private final Player player;
    private final GuildLocation topLeft;

    public AsciiMap(double angle, Player player) {
        this.angle = angle;
        this.player = player;
        this.topLeft = new GuildLocation(player.getLocation())
                .getRelative(-WIDTH_HALF, -HEIGHT_HALF);
    }

    public List<JSONMessage> getMap() {
        final List<JSONMessage> lines = new ArrayList<>();
        final List<String> asciiCompass = AsciiCompass.getAsciiCompass(angle);
        final MessagesManager messagesManager = Main.getInstance().getConfigManager().getMessagesManager();

        int index = 0;

        for (int deltaZ = 0; deltaZ < HEIGHT + 1; deltaZ++) {
            final JSONMessage jsonMessage = JSONMessage.create();

            if (deltaZ < 3) {
                jsonMessage.then(asciiCompass.get(deltaZ));
            }

            for (int deltaX = (deltaZ < 3 ? 6 : 3); deltaX < WIDTH + 1; deltaX++) {
                if (deltaX == WIDTH_HALF && deltaZ == HEIGHT_HALF) {
                    jsonMessage.then("+")
                            .color(ChatColor.AQUA)
                            .tooltip(messagesManager.getString("map-center-tooltip"));
                } else {
                    final GuildLocation locationAt = topLeft.getRelative(deltaX, deltaZ);
                    final Chunk chunkAt = locationAt.getChunk();
                    final Claim claimAt = Main.getInstance().getDataManager().getClaimManager().getClaimAt(chunkAt.getX(), chunkAt.getZ());

                    if (claimAt == null || claimAt.isUnClaim()) {
                        jsonMessage.then("-") // si il n'y a pas de claim, on affiche un - gris
                                .color(ChatColor.GRAY)
                                .tooltip(messagesManager.getString("map-wilderness-tooltip")
                                        .replace("%chunk_x%", String.valueOf(chunkAt.getX()))
                                        .replace("%chunk_z%", String.valueOf(chunkAt.getZ())));
                    } else {
                        final Guild guildAt = claimAt.getClaimData().getGuild();

                        String guildChar = this.guildChars.get(guildAt);

                        if (guildAt == null || guildAt.isDeleted()) {
                            jsonMessage.then("-") // si il n'y a pas de guilde, on affiche un - gris
                                    .color(ChatColor.GRAY)
                                    .tooltip(messagesManager.getString("map-wilderness-tooltip")
                                            .replace("%chunk_x%", String.valueOf(chunkAt.getX()))
                                            .replace("%chunk_z%", String.valueOf(chunkAt.getZ())));
                        } else {
                            if (guildChar != null) {
                                jsonMessage.then(guildChar);
                            } else {
                                final Guild playerGuild = Main.getInstance().getDataManager().getPlayerAccountManager().getPlayerAccount(player.getUniqueId())
                                        .getGuild();

                                ChatColor color = ChatColor.GRAY;
                                String name = guildAt.getGuildData().getName();
                                if (playerGuild != null && !playerGuild.isDeleted()) {
                                    final boolean isPlayerGuild = playerGuild.getGuildData().getGuildUniqueId()
                                            .equals(guildAt.getGuildData().getGuildUniqueId());
                                    final boolean isAlly = playerGuild.getGuildData().getKingdom().getKingdomProperties().getNumber()
                                            .equals(guildAt.getGuildData().getKingdom().getKingdomProperties().getNumber());

                                    color = isPlayerGuild ? ChatColor.AQUA : isAlly ? ChatColor.GREEN :
                                            ChatColor.RED; // si la guilde est alliée, on affiche en vert, sinon en rouge
                                }

                                guildChar = String.valueOf(GUILD_KEY_CHARS[index++]);

                                jsonMessage.then(guildChar)
                                        .color(color)
                                        .tooltip(messagesManager.getString("map-guild-tooltip")
                                                .replace("%guilde%", name)
                                                .replace("%chunk_x%", String.valueOf(chunkAt.getX()))
                                                .replace("%chunk_z%", String.valueOf(chunkAt.getZ())));

                                guildChars.put(guildAt, guildChar);
                            }
                        }
                    }
                }
            }

            jsonMessage.then("❘")
                    .style(ChatColor.BOLD)
                    .color(ChatColor.GOLD);

            lines.add(jsonMessage);
        }

        return lines;
    }

    public double getAngle() {
        return angle;
    }
}
