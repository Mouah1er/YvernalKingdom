package fr.yvernal.yvernalkingdom.config.items;

import java.util.List;

public class ShowGuildItems {
    private final String inventoryName;

    private final String enchantmentTableName;
    private final List<String> enchantmentTableLore;

    private final String firstBookName;
    private final List<String> firstBookLore;
    private final String secondBookName;
    private final List<String> secondBookLore;
    private final String thirdBookName;
    private final List<String> thirdBookLore;
    private final String fourthBookName;
    private final List<String> fourthBookLore;

    private final String ownerItemName;
    private final List<String> ownerItemLore;
    private final String playerItemName;
    private final List<String> playerItemLore;
    private final String paperItemName;
    private final List<String> paperItemLore;

    public ShowGuildItems(String inventoryName, String enchantmentTableName, List<String> enchantmentTableLore, String firstBookName,
                          List<String> firstBookLore, String secondBookName, List<String> secondBookLore, String thirdBookName,
                          List<String> thirdBookLore, String fourthBookName, List<String> fourthBookLore, String ownerItemName, List<String> ownerItemLore, String playerItemName,
                          List<String> playerItemLore, String paperItemName, List<String> paperItemLore) {
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

    public List<String> getEnchantmentTableLore() {
        return enchantmentTableLore;
    }

    public String getFirstBookName() {
        return firstBookName;
    }

    public List<String> getFirstBookLore() {
        return firstBookLore;
    }

    public String getSecondBookName() {
        return secondBookName;
    }

    public List<String> getSecondBookLore() {
        return secondBookLore;
    }

    public String getThirdBookName() {
        return thirdBookName;
    }

    public List<String> getThirdBookLore() {
        return thirdBookLore;
    }

    public String getFourthBookName() {
        return fourthBookName;
    }

    public List<String> getFourthBookLore() {
        return fourthBookLore;
    }

    public String getOwnerItemName() {
        return ownerItemName;
    }

    public List<String> getOwnerItemLore() {
        return ownerItemLore;
    }

    public String getPlayerItemName() {
        return playerItemName;
    }

    public List<String> getPlayerItemLore() {
        return playerItemLore;
    }

    public String getPaperItemName() {
        return paperItemName;
    }

    public List<String> getPaperItemLore() {
        return paperItemLore;
    }
}