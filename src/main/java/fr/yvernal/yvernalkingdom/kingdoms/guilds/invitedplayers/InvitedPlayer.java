package fr.yvernal.yvernalkingdom.kingdoms.guilds.invitedplayers;

import fr.yvernal.yvernalkingdom.data.kingdoms.guilds.invitedplayers.InvitedPlayerData;

public class InvitedPlayer {
    private final InvitedPlayerData invitedPlayerData;
    private boolean isStillInvited;
    private boolean isNew;

    public InvitedPlayer(InvitedPlayerData invitedPlayerData, boolean isStillInvited, boolean isNew) {
        this.invitedPlayerData = invitedPlayerData;
        this.isStillInvited = isStillInvited;
        this.isNew = isNew;
    }

    public InvitedPlayerData getInvitedPlayerData() {
        return invitedPlayerData;
    }

    public void setStillInvited(boolean stillInvited) {
        isStillInvited = stillInvited;
    }

    public boolean isStillInvited() {
        return isStillInvited;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @Override
    public String toString() {
        return "InvitedPlayer{" +
                "invitedPlayerData=" + invitedPlayerData +
                ", isStillInvited=" + isStillInvited +
                ", isNew=" + isNew +
                '}';
    }
}
