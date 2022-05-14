package fr.yvernal.yvernalkingdom.config.items;


import fr.yvernal.yvernalkingdom.utils.list.YvernalArrayList;

public class ShowGuildItems {
    private final String inventoryName;

    private final String enchantmentTableName;
    private final YvernalArrayList<String> enchantmentTableLore;

    private final String firstBookName;
    private final YvernalArrayList<String> firstBookLore;
    private final String secondBookName;
    private final YvernalArrayList<String> secondBookLore;
    private final String thirdBookName;
    private final YvernalArrayList<String> thirdBookLore;
    private final String fourthBookName;
    private final YvernalArrayList<String> fourthBookLore;

    private final String ownerItemName;
    private final YvernalArrayList<String> ownerItemLore;
    private final String playerItemName;
    private final YvernalArrayList<String> playerItemLore;
    private final String paperItemName;
    private final YvernalArrayList<String> paperItemLore;

    public ShowGuildItems(String inventoryName, String enchantmentTableName, YvernalArrayList<String> enchantmentTableLore, String firstBookName,
                          YvernalArrayList<String> firstBookLore, String secondBookName, YvernalArrayList<String> secondBookLore, String thirdBookName,
                          YvernalArrayList<String> thirdBookLore, String fourthBookName, YvernalArrayList<String> fourthBookLore, String ownerItemName, YvernalArrayList<String> ownerItemLore,
                          String playerItemName, YvernalArrayList<String> playerItemLore, String paperItemName, YvernalArrayList<String> paperItemLore) {
        this.inventoryName = inventoryName;
        this.enchantmentTableName = enchantmentTableName;
        this.enchantmentTableLore = enchantmentTableLore;
        this.firstBookName = firstBookName;
        this.firstBookLore = firstBookLore;
        this.secondBookName = secondBookName;
        this.secondBookLore = secondBookLore;
        this.thirdBookName = thirdBookName;
        this.thirdBookLore = thirdBookLore;
        this.fourthBookName = fourthBookName;
        this.fourthBookLore = fourthBookLore;
        this.ownerItemName = ownerItemName;
        this.ownerItemLore = ownerItemLore;
        this.playerItemName = playerItemName;
        this.playerItemLore = playerItemLore;
        this.paperItemName = paperItemName;
        this.paperItemLore = paperItemLore;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    public String getEnchantmentTableName() {
        return enchantmentTableName;
    }

    public YvernalArrayList<String> getEnchantmentTableLore() {
        return enchantmentTableLore;
    }

    public String getFirstBookName() {
        return firstBookName;
    }

    public YvernalArrayList<String> getFirstBookLore() {
        return firstBookLore;
    }

    public String getSecondBookName() {
        return secondBookName;
    }

    public YvernalArrayList<String> getSecondBookLore() {
        return secondBookLore;
    }

    public String getThirdBookName() {
        return thirdBookName;
    }

    public YvernalArrayList<String> getThirdBookLore() {
        return thirdBookLore;
    }

    public String getFourthBookName() {
        return fourthBookName;
    }

    public YvernalArrayList<String> getFourthBookLore() {
        return fourthBookLore;
    }

    public String getOwnerItemName() {
        return ownerItemName;
    }

    public YvernalArrayList<String> getOwnerItemLore() {
        return ownerItemLore;
    }

    public String getPlayerItemName() {
        return playerItemName;
    }

    public YvernalArrayList<String> getPlayerItemLore() {
        return playerItemLore;
    }

    public String getPaperItemName() {
        return paperItemName;
    }

    public YvernalArrayList<String> getPaperItemLore() {
        return paperItemLore;
    }
}