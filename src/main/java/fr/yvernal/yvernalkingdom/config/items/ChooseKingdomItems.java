package fr.yvernal.yvernalkingdom.config.items;

import java.util.List;

public class ChooseKingdomItems {
    private final String bookName;
    private final List<String> bookLore;
    private final String compassName;
    private final List<String> compassLore;
    private final String paperName;
    private final List<String> paperLore;
    private final String emptyMap1Name;
    private final List<String> emptyMap1Lore;
    private final String emptyMap2Name;
    private final List<String> emptyMap2Lore;
    private final String emptyMap3Name;
    private final List<String> emptyMap3Lore;
    private final String emptyMap4Name;
    private final List<String> emptyMap4Lore;
    private final String waitingLineTrueName;
    private final List<String> waitingLineTrueLore;
    private final String waitingLineFalseName;
    private final List<String> waitingLineFalseLore;

    public ChooseKingdomItems(String bookName, List<String> bookLore, String compassName, List<String> compassLore, String paperName,
                              List<String> paperLore, String emptyMap1Name, List<String> emptyMap1Lore, String emptyMap2Name, List<String> emptyMap2Lore,
                              String emptyMap3Name, List<String> emptyMap3Lore, String emptyMap4Name, List<String> emptyMap4Lore, String waitingLineTrueName,
                              List<String> waitingLineTrueLore, String waitingLineFalseName, List<String> waitingLineFalseLore) {
        this.bookName = bookName;
        this.bookLore = bookLore;
        this.compassName = compassName;
        this.compassLore = compassLore;
        this.paperName = paperName;
        this.paperLore = paperLore;
        this.emptyMap1Name = emptyMap1Name;
        this.emptyMap1Lore = emptyMap1Lore;
        this.emptyMap2Name = emptyMap2Name;
        this.emptyMap2Lore = emptyMap2Lore;
        this.emptyMap3Name = emptyMap3Name;
        this.emptyMap3Lore = emptyMap3Lore;
        this.emptyMap4Name = emptyMap4Name;
        this.emptyMap4Lore = emptyMap4Lore;
        this.waitingLineTrueName = waitingLineTrueName;
        this.waitingLineTrueLore = waitingLineTrueLore;
        this.waitingLineFalseName = waitingLineFalseName;
        this.waitingLineFalseLore = waitingLineFalseLore;
    }

    public String getBookName() {
        return bookName;
    }

    public List<String> getBookLore() {
        return bookLore;
    }

    public String getCompassName() {
        return compassName;
    }

    public List<String> getCompassLore() {
        return compassLore;
    }

    public String getPaperName() {
        return paperName;
    }

    public List<String> getPaperLore() {
        return paperLore;
    }

    public String getEmptyMap1Name() {
        return emptyMap1Name;
    }

    public List<String> getEmptyMap1Lore() {
        return emptyMap1Lore;
    }

    public String getEmptyMap2Name() {
        return emptyMap2Name;
    }

    public List<String> getEmptyMap2Lore() {
        return emptyMap2Lore;
    }

    public String getEmptyMap3Name() {
        return emptyMap3Name;
    }

    public List<String> getEmptyMap3Lore() {
        return emptyMap3Lore;
    }

    public String getEmptyMap4Name() {
        return emptyMap4Name;
    }

    public List<String> getEmptyMap4Lore() {
        return emptyMap4Lore;
    }

    public String getWaitingLineTrueName() {
        return waitingLineTrueName;
    }

    public List<String> getWaitingLineTrueLore() {
        return waitingLineTrueLore;
    }

    public String getWaitingLineFalseName() {
        return waitingLineFalseName;
    }

    public List<String> getWaitingLineFalseLore() {
        return waitingLineFalseLore;
    }
}
