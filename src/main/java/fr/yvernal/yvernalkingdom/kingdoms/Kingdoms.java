package fr.yvernal.yvernalkingdom.kingdoms;

import fr.yvernal.yvernalkingdom.Main;

import java.util.Locale;
import java.util.function.Consumer;

public enum Kingdoms {
    KINGDOM_1(kingdom1 -> {
        kingdom1.setKingdomProperties(Main.getInstance().getConfigManager().getKingdomPropertiesManager().getProperties("kingdom_1"));
        kingdom1.setKingdomData(Main.getInstance().getDataManager().getKingdomDataManager().getKingdomDataFromDatabase("kingdom_1"));

        Main.getInstance().getDataManager().getKingdomDataManager().getKingdoms().add(kingdom1);

        kingdom1.setCrystal(Main.getInstance().getDataManager().getCrystalDataManager().getFromDatabase("kingdom_1"));
        Main.getInstance().getDataManager().getCrystalDataManager().getCrystals().add(kingdom1.getCrystal());
    }),
    KINGDOM_2(kingdom2 -> {
        kingdom2.setKingdomProperties(Main.getInstance().getConfigManager().getKingdomPropertiesManager().getProperties("kingdom_2"));
        kingdom2.setKingdomData(Main.getInstance().getDataManager().getKingdomDataManager().getKingdomDataFromDatabase("kingdom_2"));

        Main.getInstance().getDataManager().getKingdomDataManager().getKingdoms().add(kingdom2);

        kingdom2.setCrystal(Main.getInstance().getDataManager().getCrystalDataManager().getFromDatabase("kingdom_2"));
        Main.getInstance().getDataManager().getCrystalDataManager().getCrystals().add(kingdom2.getCrystal());
    }),
    KINGDOM_3(kingdom3 -> {
        kingdom3.setKingdomProperties(Main.getInstance().getConfigManager().getKingdomPropertiesManager().getProperties("kingdom_3"));
        kingdom3.setKingdomData(Main.getInstance().getDataManager().getKingdomDataManager().getKingdomDataFromDatabase("kingdom_3"));

        Main.getInstance().getDataManager().getKingdomDataManager().getKingdoms().add(kingdom3);

        kingdom3.setCrystal(Main.getInstance().getDataManager().getCrystalDataManager().getFromDatabase("kingdom_3"));
        Main.getInstance().getDataManager().getCrystalDataManager().getCrystals().add(kingdom3.getCrystal());
    }),
    KINGDOM_4(kingdom4 -> {
        kingdom4.setKingdomProperties(Main.getInstance().getConfigManager().getKingdomPropertiesManager().getProperties("kingdom_4"));
        kingdom4.setKingdomData(Main.getInstance().getDataManager().getKingdomDataManager().getKingdomDataFromDatabase("kingdom_4"));

        Main.getInstance().getDataManager().getKingdomDataManager().getKingdoms().add(kingdom4);

        kingdom4.setCrystal(Main.getInstance().getDataManager().getCrystalDataManager().getFromDatabase("kingdom_4"));
        Main.getInstance().getDataManager().getCrystalDataManager().getCrystals().add(kingdom4.getCrystal());
    }),

    ;

    private final Consumer<Kingdom> consumer;
    private Kingdom kingdom;

    Kingdoms(Consumer<Kingdom> consumer) {
        this.consumer = consumer;
    }

    public static Kingdom getByNumber(String kingdomNumber) {
        try {
            return valueOf(kingdomNumber).getKingdom();
        } catch (IllegalArgumentException e) {
            try {
                return valueOf(kingdomNumber.toUpperCase(Locale.ROOT)).getKingdom();
            } catch (IllegalArgumentException ex) {
                return null;
            }
        }
    }

    public Consumer<Kingdom> getConsumer() {
        return consumer;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }
}
