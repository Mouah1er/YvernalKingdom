package fr.yvernal.yvernalkingdom.config.items;

import fr.yvernal.yvernalkingdom.utils.list.GlueList;

import java.util.List;

public class ShowGuildItems {
    private final String inventoryName;

    private final String enchantmentTableName;
    private final GlueList<String> enchantmentTableLore;

    private final String firstBookName;
    private final GlueList<String> firstBookLore;
    private final String secondBookName;
    private final GlueList<String> secondBookLore;
    private final String thirdBookName;
    private final GlueList<String> thirdBookLore;
    private final String fourthBookName;
    private final GlueList<String> fourthBookLore;

    private final String ownerItemName;
    private final GlueList<String> ownerItemLore;
    private final String playerItemName;
    private final GlueList<String> playerItemLore;
    private final String paperItemName;
    private final GlueList<String> paperItemLore;

    public ShowGuildItems(String inventoryName, String enchantmentTableName, GlueList<String> enchantmentTableLore, String firstBookName,
                          GlueList<String> firstBookLore, String secondBookName, GlueList<String> secondBookLore, String thirdBookName,
                          GlueList<String> thirdBookLore, String fourthBookName, GlueList<String> fourthBookLore, String ownerItemName, GlueList<String> ownerItemLore,
                          String playerItemName, GlueList<String> playerItemLore, String paperItemName, GlueList<String> paperItemLore) {
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

    public GlueList<String> getEnchantmentTableLore() {
        return enchantmentTableLore;
    }

    public String getFirstBookName() {
        return firstBookName;
    }

    public GlueList<String> getFirstBookLore() {
        return firstBookLore;
    }

    public String getSecondBookName() {
        return secondBookName;
    }

    public GlueList<String> getSecondBookLore() {
        return secondBookLore;
    }

    public String getThirdBookName() {
        return thirdBookName;
    }

    public GlueList<String> getThirdBookLore() {
        return thirdBookLore;
    }

    public String getFourthBookName() {
        return fourthBookName;
    }

    public GlueList<String> getFourthBookLore() {
        return fourthBookLore;
    }

    public String getOwnerItemName() {
        return ownerItemName;
    }

    public GlueList<String> getOwnerItemLore() {
        return ownerItemLore;
    }

    public String getPlayerItemName() {
        return playerItemName;
    }

    public GlueList<String> getPlayerItemLore() {
        return playerItemLore;
    }

    public String getPaperItemName() {
        return paperItemName;
    }

    public GlueList<String> getPaperItemLore() {
        return paperItemLore;
    }
}