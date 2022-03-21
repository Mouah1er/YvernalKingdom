package fr.yvernal.yvernalkingdom;

import fr.yvernal.yvernalkingdom.commands.YvernalCommand;
import fr.yvernal.yvernalkingdom.config.ConfigManager;
import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccountManager;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdoms;
import fr.yvernal.yvernalkingdom.listeners.YvernalListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    private ConfigManager configManager;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        getLogger().info("Kingdom plugin has started !");

        saveDefaultConfig();

        this.configManager = new ConfigManager();
        this.dataManager = new DataManager(getDataFolder().getAbsolutePath() + File.separator + "database.db");

        YvernalListener.registerListeners();
        YvernalCommand.registerCommands();

        for (Kingdoms value : Kingdoms.values()) {
            Main.getInstance().getDataManager().getKingdomDataManager().getKingdoms().add(value.getKingdom());
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final PlayerAccountManager playerAccountManager = Main.getInstance().getDataManager().getPlayerAccountManager();

            playerAccountManager.updatePlayerAccountToDatabase(playerAccountManager.getPlayerAccount(player.getUniqueId()));
            playerAccountManager.getAccounts().remove(playerAccountManager.getPlayerAccount(player.getUniqueId()));
            
            player.kickPlayer("§aLe serveur redémarre !");
        });

        getDataManager().getGuildDataManager().getGuilds().forEach(guild -> {
            getDataManager().getGuildDataManager().updateGuildToDatabase(guild);
            getDataManager().getClaimManager().getClaims(guild).forEach(claim -> getDataManager().getClaimManager().updateClaimToDatabase(guild, claim));
        });
    }

    public static Main getInstance() {
        return getPlugin(Main.class);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
