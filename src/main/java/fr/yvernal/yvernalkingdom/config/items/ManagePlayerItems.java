package fr.yvernal.yvernalkingdom.config.items;

public class ManagePlayerItems {
    private final String inventoryName;

    private final String firstGlassName;
    private final String secondGlassName;
    private final String thirdGlassName;

    public ManagePlayerItems(final String inventoryName, String firstGlassName, String secondGlassName, String thirdGlassName) {
        this.inventoryName = inventoryName;
        this.firstGlassName = firstGlassName;
        this.secondGlassName = secondGlassName;
        this.thirdGlassName = thirdGlassName;
    }

    public String getInventoryName() {
        return this.inventoryName;
    }

    public String getFirstGlassName() {
        return this.firstGlassName;
    }

    public String getSecondGlassName() {
        return this.secondGlassName;
    }

    public String getThirdGlassName() {
        return this.thirdGlassName;
    }
}
