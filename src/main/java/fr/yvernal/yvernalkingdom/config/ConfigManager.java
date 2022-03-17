package fr.yvernal.yvernalkingdom.config;

import fr.yvernal.yvernalkingdom.config.game.GameConfigManager;
import fr.yvernal.yvernalkingdom.config.items.ItemsConfigManager;
import fr.yvernal.yvernalkingdom.config.kingdoms.KingdomPropertiesManager;
import fr.yvernal.yvernalkingdom.config.messages.MessagesManager;

/**
 * Permet d'accéder à tous les fichiers de config
 */
public class ConfigManager {
    private final KingdomPropertiesManager kingdomPropertiesManager;
    private final ItemsConfigManager itemsConfigManager;
    private final MessagesManager messagesManager;
    private final GameConfigManager gameConfigManager;

    public ConfigManager() {
        this.kingdomPropertiesManager = new KingdomPropertiesManager();
        this.itemsConfigManager = new ItemsConfigManager();
        this.messagesManager = new MessagesManager();
        this.gameConfigManager = new GameConfigManager();
    }

    public KingdomPropertiesManager getKingdomPropertiesManager() {
        return kingdomPropertiesManager;
    }

    public ItemsConfigManager getItemsManager() {
        return itemsConfigManager;
    }

    public MessagesManager getMessagesManager() {
        return messagesManager;
    }

    public GameConfigManager getGameConfigManager() {
        return gameConfigManager;
    }
}
