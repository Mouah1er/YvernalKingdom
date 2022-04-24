package fr.yvernal.yvernalkingdom.kingdoms.crystal;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import fr.yvernal.yvernalkingdom.data.kingdoms.crystal.CrystalData;

public class Crystal {
    private final CrystalData crystalData;
    private final Hologram crystalHologram;

    private boolean isAttacked;

    public Crystal(CrystalData crystalData, Hologram crystalHologram) {
        this.crystalData = crystalData;
        this.crystalHologram = crystalHologram;
    }

    public CrystalData getCrystalData() {
        return this.crystalData;
    }

    public Hologram getCrystalHologram() {
        return this.crystalHologram;
    }

    public boolean isAttacked() {
        return this.isAttacked;
    }

    public void setAttacked(boolean attacked) {
        this.isAttacked = attacked;
    }

    @Override
    public String toString() {
        return "Crystal{" +
                "crystalData=" + crystalData +
                ", crystalHologram=" + crystalHologram +
                ", isAttacked=" + isAttacked +
                '}';
    }
}
