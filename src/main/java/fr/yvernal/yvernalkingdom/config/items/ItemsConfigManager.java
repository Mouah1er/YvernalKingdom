package fr.yvernal.yvernalkingdom.config.items;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.utils.ConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class ItemsConfigManager {
    private final FileConfiguration chooseKingdomItemsConfig;
    private final FileConfiguration showGuildItemsConfig;

    public ItemsConfigManager() {
        final String parentFileName = Main.getInstance().getDataFolder() + File.separator + "items";
        final File chooseKingdomItemsFile = new File(parentFileName, "choosekingdomitems.yml");
        final File showGuildItemsFile = new File(parentFileName, "showguilditems.yml");

        ConfigUtils.createFileIfNotExists(chooseKingdomItemsFile);
        ConfigUtils.writeToFile("items", chooseKingdomItemsFile);
        ConfigUtils.createFileIfNotExists(showGuildItemsFile);
        ConfigUtils.writeToFile("items", showGuildItemsFile);

        this.chooseKingdomItemsConfig = YamlConfiguration.loadConfiguration(chooseKingdomItemsFile);
        this.showGuildItemsConfig = YamlConfiguration.loadConfiguration(showGuildItemsFile);
    }

    public ShowGuildItems getShowGuildItems() {
        final String inventoryName = showGuildItemsConfig.getString("inventory_name");

        final String enchantmentTableName = showGuildItemsConfig.getString("enchantment_table_name");
        final List<String> enchantmentTableLore = showGuildItemsConfig.getStringList("enchantment_table_lore");

        final String firstBookName = showGuildItemsConfig.getString("first_book_name");
        final List<String> firstBookLore = showGuildItemsConfig.getStringList("first_book_lore");
        final String secondBookName = showGuildItemsConfig.getString("second_book_name");
        final List<String> secondBookLore = showGuildItemsConfig.getStringList("second_book_lore");
        final String thirdBookName = showGuildItemsConfig.getString("third_book_name");
        final List<String> thirdBookLore = showGuildItemsConfig.getStringList("third_book_lore");
        final String fourthBookName = showGuildItemsConfig.getString("fourth_book_name");
        final List<String> fourthBookLore = showGuildItemsConfig.getStringList("fourth_book_lore");

        final String ownerItemName = showGuildItemsConfig.getString("owner_item_name");
        final List<String> ownerItemLore = showGuildItemsConfig.getStringList("owner_item_lore");
        final String playerItemName = showGuildItemsConfig.getString("player_item_name");
        final List<String> playerItemLore = showGuildItemsConfig.getStringList("player_item_lore");
        final String paperItemName = showGuildItemsConfig.getString("paper_item_name");
        final List<String> paperItemLore = showGuildItemsConfig.getStringList("paper_item_lore");

        return new ShowGuildItems(inventoryName, enchantmentTableName, enchantmentTableLore, firstBookName, firstBookLore, secondBookName,
                secondBookLore, thirdBookName, thirdBookLore, fourthBookName, fourthBookLore, ownerItemName, ownerItemLore,
                playerItemName, playerItemLore, paperItemName, paperItemLore);
    }

    public ChooseKingdomItems getChooseKingdomItems() {
        final String bookName = chooseKingdomItemsConfig.getString("book_name");
        final List<String> bookLore = chooseKingdomItemsConfig.getStringList("book_lore");
        final String compassName = chooseKingdomItemsConfig.getString("compass_name");
        final List<String> compassLore = chooseKingdomItemsConfig.getStringList("compass_lore");
        final String paperName = chooseKingdomItemsConfig.getString("paper_name");
        final List<String> paperLore = chooseKingdomItemsConfig.getStringList("paper_lore");
        final String emptyMap1Name = chooseKingdomItemsConfig.getString("emptymap1_name");
        final List<String> emptyMap1Lore = chooseKingdomItemsConfig.getStringList("emptymap1_lore");
        final String emptyMap2Name = chooseKingdomItemsConfig.getString("emptymap2_name");
        final List<String> emptyMap2Lore = chooseKingdomItemsConfig.getStringList("emptymap2_lore");
        final String emptyMap3Name = chooseKingdomItemsConfig.getString("emptymap3_name");
        final List<String> emptyMap3Lore = chooseKingdomItemsConfig.getStringList("emptymap3_lore");
        final String emptyMap4Name = chooseKingdomItemsConfig.getString("emptymap4_name");
        final List<String> emptyMap4Lore = chooseKingdomItemsConfig.getStringList("emptymap4_lore");
        final String waitingLineTrueName = chooseKingdomItemsConfig.getString("waiting_line_true_name");
        final List<String> waitingLineTrueLore = chooseKingdomItemsConfig.getStringList("waiting_line_true_lore");
        final String waitingLineFalseName = chooseKingdomItemsConfig.getString("waiting_line_false_name");
        final List<String> waitingLineFalseLore = chooseKingdomItemsConfig.getStringList("waiting_line_false_lore");

        return new ChooseKingdomItems(bookName, bookLore, compassName, compassLore, paperName, paperLore, emptyMap1Name, emptyMap1Lore, emptyMap2Name,
                emptyMap2Lore, emptyMap3Name, emptyMap3Lore, emptyMap4Name, emptyMap4Lore, waitingLineTrueName, waitingLineTrueLore, waitingLineFalseName,
                waitingLineFalseLore);
    }
}
