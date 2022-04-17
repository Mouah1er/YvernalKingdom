package fr.yvernal.yvernalkingdom.kingdoms;

import fr.yvernal.yvernalkingdom.config.kingdoms.KingdomProperties;
import fr.yvernal.yvernalkingdom.data.kingdoms.KingdomData;
import fr.yvernal.yvernalkingdom.utils.nametag.NameTagTeam;

public class Kingdom {
    private final KingdomProperties kingdomProperties;
    private final KingdomData kingdomData;

    /**
     * Représente un royaume
     *
     * @param kingdomProperties les propriétés du royaume
     * @param kingdomData       les données du royaume
     */
    public Kingdom(KingdomProperties kingdomProperties, KingdomData kingdomData) {
        this.kingdomProperties = kingdomProperties;
        this.kingdomData = kingdomData;
    }

    public KingdomProperties getKingdomProperties() {
        return kingdomProperties;
    }

    public KingdomData getKingdomData() {
        return kingdomData;
    }
}
