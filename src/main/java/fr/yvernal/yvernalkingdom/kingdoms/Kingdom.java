package fr.yvernal.yvernalkingdom.kingdoms;

import fr.yvernal.yvernalkingdom.config.kingdoms.KingdomProperties;
import fr.yvernal.yvernalkingdom.data.kingdoms.KingdomData;
import fr.yvernal.yvernalkingdom.kingdoms.crystal.Crystal;

public class Kingdom {
    private KingdomProperties kingdomProperties;
    private KingdomData kingdomData;
    private Crystal crystal;

    /**
     * Représente un royaume
     *
     * @param kingdomProperties les propriétés du royaume
     * @param kingdomData       les données du royaume
     */
    public Kingdom(KingdomProperties kingdomProperties, KingdomData kingdomData, Crystal crystal) {
        this.kingdomProperties = kingdomProperties;
        this.kingdomData = kingdomData;
        this.crystal = crystal;
    }

    public KingdomProperties getKingdomProperties() {
        return kingdomProperties;
    }

    public void setKingdomProperties(KingdomProperties kingdomProperties) {
        this.kingdomProperties = kingdomProperties;
    }

    public KingdomData getKingdomData() {
        return kingdomData;
    }

    public void setKingdomData(KingdomData kingdomData) {
        this.kingdomData = kingdomData;
    }

    public Crystal getCrystal() {
        return crystal;
    }

    public void setCrystal(Crystal crystal) {
        this.crystal = crystal;
    }

    @Override
    public String toString() {
        return "Kingdom{" +
                "kingdomProperties=" + kingdomProperties +
                ", kingdomData=" + kingdomData +
                ", crystal=" + crystal +
                '}';
    }
}
