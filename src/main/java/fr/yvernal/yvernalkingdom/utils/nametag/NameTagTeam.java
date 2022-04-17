package fr.yvernal.yvernalkingdom.utils.nametag;

import fr.yvernal.yvernalkingdom.utils.reflection.MCReflection;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

public class NameTagTeam {
    // REFLECTION
    private final Constructor<?> packetPlayOutScoreboardTeamConstructor;
    private final boolean doABCRequireString, doHRequireInteger;

    // MAIN INFORMATIONS
    private final Set<OfflinePlayer> teamMates;
    private String prefix, suffix;
    private final String name;

    // RECEIVERS
    private final Set<UUID> receivers;
    private boolean visible;

    // FRIENDLY FIRE
    private boolean friendlyFire;

    public NameTagTeam(String name, String prefix, String suffix) {
        this.name = name;
        this.teamMates = new HashSet<>();

        this.visible = true;
        this.friendlyFire = false;
        this.receivers = new HashSet<>();

        setPrefix(prefix);
        setSuffix(suffix);

        final Class<?> packetPlayOutScoreboardTeamClass = Objects.requireNonNull(MCReflection.getNMSClass("PacketPlayOutScoreboardTeam"));

        this.packetPlayOutScoreboardTeamConstructor = MCReflection.getConstructor(packetPlayOutScoreboardTeamClass);
        this.doABCRequireString = Objects.requireNonNull(MCReflection.getField(packetPlayOutScoreboardTeamClass, "b")).getType().isAssignableFrom(String.class);
        this.doHRequireInteger = Objects.requireNonNull(MCReflection.getField(packetPlayOutScoreboardTeamClass, "h")).getType().isAssignableFrom(int.class);
    }

    public String getName() {
        return this.name;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        this.receivers.stream().filter(receiver -> Bukkit.getOfflinePlayer(receiver).isOnline()).forEach(receiver ->
                MCReflection.sendPacket(Bukkit.getPlayer(receiver), this.createPacket(2)));
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
        this.receivers.stream().filter(receiver -> Bukkit.getOfflinePlayer(receiver).isOnline()).forEach(receiver ->
                MCReflection.sendPacket(Bukkit.getPlayer(receiver), this.createPacket(2)));
    }

    public boolean isTeamMate(UUID uuid) {
        return this.teamMates.stream().anyMatch(offlinePlayer -> offlinePlayer.getUniqueId().equals(uuid));
    }

    protected void destroy() {
        this.receivers.forEach(this::removeReceiver);
    }

    private Object createPacket(int mode) {
        final Object packetPlayOutScoreboardTeam = MCReflection.callConstructor(this.packetPlayOutScoreboardTeamConstructor);

        this.setField(packetPlayOutScoreboardTeam, "a", this.name);

        if (this.doABCRequireString) {
            this.setField(packetPlayOutScoreboardTeam, "b", "");
            this.setField(packetPlayOutScoreboardTeam, "c", this.prefix);
            this.setField(packetPlayOutScoreboardTeam, "d", this.suffix);
        } else {
            this.setField(packetPlayOutScoreboardTeam, "b", new ChatMessage(""));
            this.setField(packetPlayOutScoreboardTeam, "c", new ChatMessage(this.prefix));
            this.setField(packetPlayOutScoreboardTeam, "d", new ChatMessage(this.suffix));
        }

        this.setField(packetPlayOutScoreboardTeam, "e", "always");
        this.setField(packetPlayOutScoreboardTeam, "f", 0);

        if (this.doHRequireInteger)
            this.setField(packetPlayOutScoreboardTeam, "h", mode);
        else
            this.setField(packetPlayOutScoreboardTeam, "h", this.teamMates.stream().map(OfflinePlayer::getName).collect(Collectors.toList()));

        this.setField(packetPlayOutScoreboardTeam, "i", 0);

        return packetPlayOutScoreboardTeam;
    }

    private Object addOrRemovePlayer(boolean add, String playerName) {
        final Object packetPlayOutScoreboardTeam = MCReflection.callConstructor(this.packetPlayOutScoreboardTeamConstructor);

        this.setField(packetPlayOutScoreboardTeam, "a", this.name);
        if (this.doHRequireInteger) {
            this.setField(packetPlayOutScoreboardTeam, "h", (add ? 3 : 4));
            this.setField(packetPlayOutScoreboardTeam, "g", Collections.singletonList(playerName));
        } else
            this.setField(packetPlayOutScoreboardTeam, "h", this.teamMates.stream().map(OfflinePlayer::getName).collect(Collectors.toList()));

        return packetPlayOutScoreboardTeam;
    }

    public void addPlayer(OfflinePlayer player) {
        this.teamMates.add(player);

        if (player.isOnline()) {
            this.receivers.forEach(receiver -> {
                final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(receiver);

                if (offlinePlayer != null && offlinePlayer.isOnline())
                    MCReflection.sendPacket(offlinePlayer.getPlayer(), this.addOrRemovePlayer(true, player.getName()));
            });
        }
    }

    public void addPlayers(Collection<? extends Player> players) {
        players.forEach(this::addPlayer);
    }

    public void removePlayer(UUID playerId) {
        this.removePlayer(Bukkit.getOfflinePlayer(playerId));
    }

    public void removePlayer(OfflinePlayer offlineTarget) {
        if (offlineTarget != null) {
            this.teamMates.remove(offlineTarget);

            if (this.doABCRequireString)
                this.receivers.forEach(receiver -> {
                    final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(receiver);

                    if (offlinePlayer != null && offlinePlayer.isOnline())
                        MCReflection.sendPacket(offlinePlayer.getPlayer(), this.addOrRemovePlayer(false, offlineTarget.getName()));
                });
        }
    }

    public void addReceiver(OfflinePlayer player) {
        this.addReceiver(player.getUniqueId());
    }

    public void addReceiver(UUID playerId) {
        this.receivers.add(playerId);

        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerId);

        if (offlinePlayer == null || !offlinePlayer.isOnline())
            return;

        MCReflection.sendPacket(offlinePlayer.getPlayer(), this.createPacket(0));
        this.teamMates.stream().filter(OfflinePlayer::isOnline).forEach(offlinePlayer1 ->
                MCReflection.sendPacket(offlinePlayer.getPlayer(), addOrRemovePlayer(true, offlinePlayer1.getName())));
    }

    public void addReceivers(Collection<? extends Player> receivers) {
        receivers.forEach(this::addReceiver);
    }

    public void removeReceiver(UUID playerId) {
        this.receivers.remove(playerId);

        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerId);

        if (offlinePlayer != null && offlinePlayer.isOnline())
            MCReflection.sendPacket(offlinePlayer.getPlayer(), this.createPacket(1));
    }

    public boolean isReceiver(UUID playerId) {
        return this.receivers.stream().anyMatch(receiverId -> receiverId.equals(playerId));
    }

    public void setVisible(boolean option) {
        this.visible = option;
        //addReceivers(Bukkit.getOnlinePlayers());
    }

    public boolean isVisible() {
        return this.visible;
    }

    public boolean isFriendlyFire() {
        return this.friendlyFire;
    }

    public void setFriendlyFire(boolean friendlyFire) {
        this.friendlyFire = friendlyFire;
        this.receivers.stream().filter(receiver -> Bukkit.getOfflinePlayer(receiver).isOnline()).forEach(receiver ->
                MCReflection.sendPacket(Bukkit.getPlayer(receiver), this.createPacket(2)));
    }

    private void setField(Object instance, String fieldName, Object value) {
        MCReflection.setField(MCReflection.getField(instance.getClass(), fieldName), instance, value);
    }
}
