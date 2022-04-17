package fr.yvernal.yvernalkingdom.kingdoms;

import fr.yvernal.yvernalkingdom.Main;

import java.util.Locale;

public enum Kingdoms {
    KINGDOM_1(new Kingdom(Main.getInstance().getConfigManager().getKingdomPropertiesManager().getProperties("kingdom_1"),
            Main.getInstance().getDataManager().getKingdomDataManager().getKingdomDataFromDatabase("kingdom_1"))),
    KINGDOM_2(new Kingdom(Main.getInstance().getConfigManager().getKingdomPropertiesManager().getProperties("kingdom_2"),
            Main.getInstance().getDataManager().getKingdomDataManager().getKingdomDataFromDatabase("kingdom_2"))),
    KINGDOM_3(new Kingdom(Main.getInstance().getConfigManager().getKingdomPropertiesManager().getProperties("kingdom_3"),
            Main.getInstance().getDataManager().getKingdomDataManager().getKingdomDataFromDatabase("kingdom_3"))),
    KINGDOM_4(new Kingdom(Main.getInstance().getConfigManager().getKingdomPropertiesManager().getProperties("kingdom_4"),
            Main.getInstance().getDataManager().getKingdomDataManager().getKingdomDataFromDatabase("kingdom_4"))),

    ;

    private final Kingdom kingdom;

    Kingdoms(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

    public Kingdom getKingdom() {
        return kingdom;
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
}
