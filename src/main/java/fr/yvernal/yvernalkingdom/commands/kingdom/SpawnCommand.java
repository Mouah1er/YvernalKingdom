package fr.yvernal.yvernalkingdom.commands.kingdom;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.commands.YvernalCommand;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.utils.locations.LocationUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SpawnCommand extends YvernalCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            final Kingdom kingdom = dataManager.getKingdomDataManager().getKingdomByUniqueId(player.getUniqueId());

            if (kingdom != null) {
                player.teleport(kingdom.getKingdomProperties().getSpawnLocation());
            } else {
                player.teleport(LocationUtils.stringToLocation(Main.getInstance().getConfigManager().getGameConfigManager().getString("spawn-location")));
            }
        } else {
            sender.sendMessage(messagesManager.getString("must-be-player"));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        return null;
    }
}
