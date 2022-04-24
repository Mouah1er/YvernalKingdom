package fr.yvernal.yvernalkingdom;

import fr.yvernal.yvernalkingdom.commands.YvernalCommand;
import fr.yvernal.yvernalkingdom.config.ConfigManager;
import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccountManager;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdoms;
import fr.yvernal.yvernalkingdom.listeners.YvernalListener;
import fr.yvernal.yvernalkingdom.utils.CrystalUtils;
import fr.yvernal.yvernalkingdom.utils.GroupManagerHook;
import fr.yvernal.yvernalkingdom.utils.nametag.NameTagManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
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

        Bukkit.getWorld(configManager.getGameConfigManager().getString("world-name")).getEntities()
                .forEach(Entity::remove);

        for (Kingdoms value : Kingdoms.values()) {
            final Kingdom kingdom = new Kingdom(null, null, null);
            value.getConsumer().accept(kingdom);
            value.setKingdom(kingdom);
        }

        dataManager.init();

        CrystalUtils.spawnCrystals();

        this.messageWaiter = new HashMap<>();
        this.spawnedCreepersByPlayer = new HashMap<>();
        this.groupManagerHook = new GroupManagerHook(this);
        this.nameTagManager = new NameTagManager(this);

        YvernalListener.registerListeners();
        YvernalCommand.registerCommands();

        CrystalUtils.startRebuildCrystalBukkitRunnable();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final PlayerAccountManager playerAccountManager = Main.getInstance().getDataManager().getPlayerAccountManager();
            playerAccountManager.updateToDatabase(playerAccountManager.getPlayerAccount(player.getUniqueId()));
            playerAccountManager.getAccounts().remove(playerAccountManager.getPlayerAccount(player.getUniqueId()));

            player.kickPlayer("§aLe serveur redémarre !");
        });

        getDataManager().getGuildDataManager().getGuilds().forEach(guild -> {
            getDataManager().getGuildDataManager().updateToDatabase(guild);
            guild.getGuildData().getClaims()
                    .forEach(claim -> getDataManager().getClaimManager().updateToDatabase(claim));
            guild.getGuildData().getInvitedPlayers()
                    .forEach(invitedPlayer -> getDataManager().getInvitedPlayerDataManager().updateToDatabase(invitedPlayer));
        });

        getDataManager().getCrystalDataManager().getCrystals().forEach(crystal -> getDataManager().getCrystalDataManager().updateToDatabase(crystal));

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
