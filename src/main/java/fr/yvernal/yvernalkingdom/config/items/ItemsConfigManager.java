package fr.yvernal.yvernalkingdom.config.items;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.utils.ConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ItemsConfigManager {
    private final FileConfiguration chooseKingdomItemsConfig;

    public ItemsConfigManager() {
        final File chooseKingdomItemsFile = new File(Main.getInstance().getDataFolder() + File.separator + "items",
                "choosekingdomitems.yml");

        if (!chooseKingdomItemsFile.getParentFile().exists()) chooseKingdomItemsFile.getParentFile().mkdirs();
        try {
            chooseKingdomItemsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ConfigUtils.writeToFile("items", chooseKingdomItemsFile);

        this.chooseKingdomItemsConfig = YamlConfiguration.loadConfiguration(chooseKingdomItemsFile);
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
