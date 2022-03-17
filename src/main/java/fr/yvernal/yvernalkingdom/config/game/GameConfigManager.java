package fr.yvernal.yvernalkingdom.config.game;

import fr.yvernal.yvernalkingdom.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class GameConfigManager {
    private final FileConfiguration config;

    public GameConfigManager() {
        this.config = Main.getInstance().getConfig();
    }

    public String getString(String path) {
        return config.getString("game." + path);
    }
}
