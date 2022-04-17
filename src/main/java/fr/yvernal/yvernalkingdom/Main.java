package fr.yvernal.yvernalkingdom;

import fr.yvernal.yvernalkingdom.commands.YvernalCommand;
import fr.yvernal.yvernalkingdom.config.ConfigManager;
import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccountManager;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdoms;
import fr.yvernal.yvernalkingdom.listeners.YvernalListener;
import fr.yvernal.yvernalkingdom.utils.GroupManagerHook;
import fr.yvernal.yvernalkingdom.utils.nametag.NameTagManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Main extends JavaPlugin {
    private ConfigManager configManager;
    private DataManager dataManager;
    private HashMap<UUID, CompletableFuture<String>> messageWaiter;
    private HashMap<UUID, PlayerAccount> spawnedCreepersByPlayer;
    private GroupManagerHook groupManagerHook;
    private NameTagManager nameTagManager;

    @Override
    public void onEnable() {
        getLogger().info("Kingdom plugin has started !");

        saveDefaultConfig();

        this.configManager = new ConfigManager();
        this.dataManager = new DataManager(getDataFolder().getAbsolutePath() + File.separator + "database.db");

        for (Kingdoms value : Kingdoms.values()) {
            dataManager.getKingdomDataManager().getKingdoms().add(value.getKingdom());
        }

        dataManager.init();

        this.messageWaiter = new HashMap<>();
        this.spawnedCreepersByPlayer = new HashMap<>();
        this.groupManagerHook = new GroupManagerHook(this);
        this.nameTagManager = new NameTagManager(this);

        YvernalListener.registerListeners();
        YvernalCommand.registerCommands();
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
            guild.getGuildData().getClaims()
                    .forEach(claim -> getDataManager().getClaimManager().updateClaimToDatabase(guild, claim));
            guild.getGuildData().getInvitedPlayers()
                    .forEach(invitedPlayer -> getDataManager().getInvitedPlayerDataManager().updateInvitedPlayerToDatabase(invitedPlayer));
        });

        getNameTagManager().onDisable();
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

    public HashMap<UUID, CompletableFuture<String>> getMessageWaiter() {
        return messageWaiter;
    }

    public HashMap<UUID, PlayerAccount> getSpawnedCreepersByPlayer() {
        return spawnedCreepersByPlayer;
    }

    public GroupManagerHook getGroupManagerHook() {
        return groupManagerHook;
    }

    public NameTagManager getNameTagManager() {
        return nameTagManager;
    }
}
