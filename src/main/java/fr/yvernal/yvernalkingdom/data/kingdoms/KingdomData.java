package fr.yvernal.yvernalkingdom.data.kingdoms;

import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;

import java.util.List;
import java.util.UUID;

/**
 * Représente les données d'un royaume
 */
public class KingdomData {
    private final List<Guild> guildsIn;
    private final List<UUID> waitingPlayers;
    private final List<UUID> playersIn;

    public KingdomData(List<Guild> guildsIn, List<UUID> waitingPlayers, List<UUID> playersIn) {
        this.guildsIn = guildsIn;
        this.waitingPlayers = waitingPlayers;
        this.playersIn = playersIn;
    }

    public List<Guild> getGuildsIn() {
        return guildsIn;
    }

    public List<UUID> getWaitingPlayers() {
        return waitingPlayers;
    }

    public List<UUID> getPlayersIn() {
        return playersIn;
    }
}
