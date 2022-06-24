package fr.yvernal.yvernalkingdom.data.kingdoms.crystal;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import org.bukkit.Location;

import java.util.Date;

public class CrystalData {
    private final Kingdom kingdom;

    private double health;
    private double maxHealth;
    private double exp;
    private int level;

    private boolean isDestroyed;
    private Date destructionDate;

    public CrystalData(Kingdom kingdom, double health, double exp, int level, boolean isDestroyed, Date destructionDate) {
        this.kingdom = kingdom;
        this.health = health;
        this.exp = exp;
        this.level = level;
        this.isDestroyed = isDestroyed;
        this.destructionDate = destructionDate;
        this.maxHealth = Main.getInstance().getConfigManager().getGameConfigManager().get("base-crystal-health", double.class) +
                (level == 1 ? 0 : 10 * level);
    }

    public Kingdom getKingdom() {
        return this.kingdom;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public Date getDestructionDate() {
        return destructionDate;
    }

    public void setDestructionDate(Date destructionDate) {
        this.destructionDate = destructionDate;
    }

    public Location getLocation() {
        return kingdom.getKingdomProperties().getCrystalLocation();
    }

    @Override
    public String toString() {
        return "CrystalData{" +
                "kingdom=" + kingdom +
                ", health=" + health +
                ", maxHealth=" + maxHealth +
                ", exp=" + exp +
                ", level=" + level +
                ", isDestroyed=" + isDestroyed +
                ", destructionDate=" + destructionDate +
                '}';
    }
}
