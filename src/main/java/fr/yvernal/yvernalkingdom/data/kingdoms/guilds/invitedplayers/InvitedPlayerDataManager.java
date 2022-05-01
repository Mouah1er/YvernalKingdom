package fr.yvernal.yvernalkingdom.data.kingdoms.guilds.invitedplayers;

import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.data.DataManagerTemplate;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.Guild;
import fr.yvernal.yvernalkingdom.kingdoms.guilds.invitedplayers.InvitedPlayer;
import fr.yvernal.yvernalkingdom.utils.list.GlueList;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class InvitedPlayerDataManager implements DataManagerTemplate<InvitedPlayer> {
    private final DataManager dataManager;
    private final List<InvitedPlayer> invitedPlayers;

    public InvitedPlayerDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.invitedPlayers = new GlueList<>();
    }

    @Override
    public List<InvitedPlayer> getAllFromDatabase() {
        final List<InvitedPlayer> invitedPlayers = new GlueList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM invitedPlayers", resultSet -> {
            try {
                while (resultSet.next()) {
                    invitedPlayers.add(getFromDatabase(resultSet.getString("uniqueId")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return invitedPlayers;
    }

    public InvitedPlayer getFromDatabase(String uniqueId) {
        final AtomicReference<InvitedPlayer> invitedPlayer = new AtomicReference<>();

        dataManager.getDatabaseManager().query("SELECT * FROM invitedPlayers WHERE uniqueId=?", resultSet -> {
            try {
                if (resultSet.next()) {
                    final Guild guild = dataManager.getGuildDataManager().getGuildByUniqueId(UUID.fromString(resultSet.getString("guildUniqueId")));

                    invitedPlayer.set(new InvitedPlayer(new InvitedPlayerData(UUID.fromString(uniqueId), guild), true, false));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, uniqueId);

        return invitedPlayer.get();
    }

    @Override
    public void deleteFromDatabase(InvitedPlayer invitedPlayer) {
        dataManager.getDatabaseManager().update("DELETE FROM invitedPlayers " +
                "WHERE uniqueId=? AND " +
                "guildUniqueId=?",
                invitedPlayer.getInvitedPlayerData().getUniqueId().toString(),
                invitedPlayer.getInvitedPlayerData().getGuild().getGuildData().getGuildUniqueId());
    }

    @Override
    public void addToDatabase(InvitedPlayer invitedPlayer) {
        dataManager.getDatabaseManager().update("INSERT INTO invitedPlayers (guildUniqueId, uniqueId) VALUES (" +
                "?, " +
                "?)",
                invitedPlayer.getInvitedPlayerData().getGuild().getGuildData().getGuildUniqueId(),
                invitedPlayer.getInvitedPlayerData().getUniqueId());
    }

    @Override
    public void updateToDatabase(InvitedPlayer invitedPlayer) {
        if (!invitedPlayer.isStillInvited() || invitedPlayer.isNew()) {
            if (!invitedPlayer.isStillInvited()) {
                deleteFromDatabase(invitedPlayer);
            }
            if (invitedPlayer.isNew()) {
                addToDatabase(invitedPlayer);
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
