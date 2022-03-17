package fr.yvernal.yvernalkingdom.config.messages;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.utils.ConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MessagesManager {
    private final FileConfiguration messagesConfig;

    public MessagesManager() {
        final File messagesFile = new File(Main.getInstance().getDataFolder() + File.separator + "messages",
                "messages.yml");

        if (!messagesFile.getParentFile().exists()) messagesFile.getParentFile().mkdirs();
        try {
            messagesFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ConfigUtils.writeToFile("messages", messagesFile);

        this.messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public String getString(String path) {
        return messagesConfig.getString(path);
    }

    public List<String> getStringList(String path) {
        return messagesConfig.getStringList(path);
    }
}
