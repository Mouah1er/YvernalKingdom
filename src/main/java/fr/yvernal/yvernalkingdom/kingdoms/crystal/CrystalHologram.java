package fr.yvernal.yvernalkingdom.kingdoms.crystal;

import com.gmail.filoghost.holographicdisplays.api.line.HologramLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import fr.yvernal.yvernalkingdom.Main;

public class CrystalHologram {
    private final Crystal crystal;

    public CrystalHologram(Crystal crystal) {
        this.crystal = crystal;
    }

    public void setLevelLine() {
        final int level = crystal.getCrystalData().getLevel();
        final String line = Main.getInstance().getConfigManager().getMessagesManager().getString("crystal-level")
                .replace("%level%", String.valueOf(level));

        try {
            final HologramLine hologramLine = crystal.getCrystalHologram().getLine(0);

            if (hologramLine instanceof TextLine) {
                ((TextLine) hologramLine).setText(line);
            }
        } catch (IndexOutOfBoundsException e) {
            crystal.getCrystalHologram().appendTextLine(line);

        }
    }

    public void setCrystalNameHologramLine() {
        final String line = Main.getInstance().getConfigManager().getMessagesManager().getString("crystal-kingdom-name")
                .replace("%kingdom%", crystal.getCrystalData().getKingdom().getKingdomProperties().getName());

        try {
            final HologramLine healthLine = crystal.getCrystalHologram().getLine(1);

            if (healthLine instanceof TextLine) {
                ((TextLine) healthLine).setText(line);
            }
        } catch (IndexOutOfBoundsException e) {
            crystal.getCrystalHologram().appendTextLine(line);
        }
    }

    public void setHealthHologramLine() {
        final double maxHealth = crystal.getCrystalData().getMaxHealth();
        final double health = crystal.getCrystalData().getHealth();

        final String line = Main.getInstance().getConfigManager().getMessagesManager().getString("crystal-health")
                .replace("%current_health%", String.valueOf(Math.round(health)))
                .replace("%max_health%", String.valueOf(Math.round(maxHealth)))
                .replace("%heart_icon%", "❤");

        try {
            final HologramLine healthLine = crystal.getCrystalHologram().getLine(2);

            if (healthLine instanceof TextLine) {
                ((TextLine) healthLine).setText(line);
            }
        } catch (IndexOutOfBoundsException e) {
            crystal.getCrystalHologram().appendTextLine(line);
        }
    }

    public Crystal getCrystal() {
        return crystal;
    }
}
