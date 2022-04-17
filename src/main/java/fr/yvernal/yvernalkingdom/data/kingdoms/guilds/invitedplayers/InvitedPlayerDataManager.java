package fr.yvernal.yvernalkingdom.data.kingdoms.guilds.invitedplayers;

import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.invitedplayers.InvitedPlayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class InvitedPlayerDataManager {
    private final DataManager dataManager;
    private final List<InvitedPlayer> invitedPlayers;

    public InvitedPlayerDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.invitedPlayers = new ArrayList<>();
    }

    public List<InvitedPlayer> getInvitedPlayersFromDatabase() {
        final List<InvitedPlayer> invitedPlayers = new ArrayList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM invitedPlayers", resultSet -> {
            try {
                while (resultSet.next()) {
                    final UUID uniqueId = UUID.fromString(resultSet.getString("uniqueId"));
                    final Guild guild = dataManager.getGuildDataManager().getGuildByUniqueId(UUID.fromString(resultSet.getString("guildUniqueId")));

                    invitedPlayers.add(new InvitedPlayer(new InvitedPlayerData(uniqueId, guild), true, false));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return invitedPlayers;
    }

    private void deleteInviteFromDatabase(InvitedPlayer invitedPlayer) {
        dataManager.getDatabaseManager().update("DELETE FROM invitedPlayers " +
                "WHERE uniqueId=? AND " +
                "guildUniqueId=?",
                invitedPlayer.getInvitedPlayerData().getUniqueId().toString(),
                invitedPlayer.getInvitedPlayerData().getGuild().getGuildData().getGuildUniqueId());
    }

    private void createInviteToDatabase(InvitedPlayer invitedPlayer) {
        dataManager.getDatabaseManager().update("INSERT INTO invitedPlayers (guildUniqueId, uniqueId) VALUES (" +
                "?, " +
                "?)",
                invitedPlayer.getInvitedPlayerData().getGuild().getGuildData().getGuildUniqueId(),
                invitedPlayer.getInvitedPlayerData().getUniqueId());
    }

    public void updateInvitedPlayerToDatabase(InvitedPlayer invitedPlayer) {
        if (!invitedPlayer.isStillInvited() || invitedPlayer.isNew()) {
            if (!invitedPlayer.isStillInvited()) {
                deleteInviteFromDatabase(invitedPlayer);
            }
            if (invitedPlayer.isNew()) {
                createInviteToDatabase(invitedPlayer);
            }
        } else {
            dataManager.getDatabaseManager().update("UPDATE invitedPlayers SET " +
                    "guildUniqueId=? WHERE " +
                    "uniqueId=?",
                    invitedPlayer.getInvitedPlayerData().getGuild().getGuildData().getGuildUniqueId(),
                    invitedPlayer.getInvitedPlayerData().getUniqueId().toString());
        }
    }

    public List<InvitedPlayer> getInvitedPlayers() {
        return invitedPlayers;
    }

    public InvitedPlayer getInvitedPlayerByUniqueId(UUID playerUniqueId) {
        return getInvitedPlayers()
                .stream()
                .filter(Objects::nonNull)
                .filter(invitedPlayer -> invitedPlayer.getInvitedPlayerData().getUniqueId().equals(playerUniqueId))
                .findFirst()
                .orElse(null);
    }
}
