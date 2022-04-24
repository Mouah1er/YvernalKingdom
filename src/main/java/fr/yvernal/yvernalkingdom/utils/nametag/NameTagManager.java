package fr.yvernal.yvernalkingdom.utils.nametag;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import fr.yvernal.yvernalkingdom.kingdoms.Relations;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.persistence.EntityExistsException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class NameTagManager {
    private final Set<NameTagTeam> nameTagTeams;

    /**
     * This manager allows you to add or remove prefix/suffix (which is displayed either on a player's head
     * or in the tab) to a certain player or create a team which do the same for multiple players.
     *
     * @param javaPlugin The main instance of your plugin
     * @author Pseudow
     */
    public NameTagManager(JavaPlugin javaPlugin) {
        Validate.notNull(javaPlugin, "Error, Java Plugin instance cannot be null!");

        this.nameTagTeams = new HashSet<>();
    }

    public void onDisable() {
        this.removeAllTeams();
    }

    private void removeAllTeams() {
        this.nameTagTeams.forEach(NameTagTeam::destroy);
        this.nameTagTeams.clear();
    }

    public NameTagTeam createNewTeam(String prefix) {
        String teamName = Integer.toString(ThreadLocalRandom.current().nextInt(999));

        while (this.getTeamByName(teamName) != null) {
            teamName = Integer.toString(ThreadLocalRandom.current().nextInt(999));
        }

        return this.createNewTeam(teamName, prefix);
    }

    public NameTagTeam createNewTeam(String name, String prefix) {
        return this.createNewTeam(name, prefix, "");
    }

    public NameTagTeam createNewTeam(String name, String prefix, String suffix) {
        return this.createNewTeam(name, prefix, suffix, true);
    }

    /**
     * Create a new team which allows you to set a prefix/suffix to (a) player(s) and display it
     * to players you want to display.
     *
     * @param name    The name of the team. (It won't be displayed)
     * @param prefix  The prefix which will be displayed before the player's name.
     * @param suffix  The suffix which will be displayed after the player's name.
     * @param visible If the team will be displayed to everyone.
     * @return A new team to allow you to modify it or add players or destroy it and so on...
     * @throws javax.persistence.EntityExistsException If the name is already existing! So please don't use player's uuid as name there is
     *                                                 some chance to be already used!
     */
    public NameTagTeam createNewTeam(String name, String prefix, String suffix, boolean visible) {
        if (this.getTeamByName(name) != null)
            throw new EntityExistsException("Error! The team name is already used!");

        final NameTagTeam nameTagTeam = new NameTagTeam(name, prefix, suffix);
        nameTagTeam.setVisible(visible);

        this.nameTagTeams.add(nameTagTeam);

        return nameTagTeam;
    }

    /**
     * Remove a team players inside this team won't have the name tag after doing this!
     *
     * @param parameter By default, we start by checking the name.
     */
    public void removeTeamByName(String parameter) {
        nameTagTeams.removeIf(team -> {
            if (team.getName().equals(parameter)) {
                team.destroy();
                return true;
            }

            return false;
        });
    }

    public void showPlayerNameTag(Player player) {
        final PlayerAccount playerAccount = Main.getInstance().getDataManager().getPlayerAccountManager().getPlayerAccount(player.getUniqueId());

        Bukkit.getOnlinePlayers()
                .stream()
                .filter(targetPlayer -> !targetPlayer.getUniqueId().equals(player.getUniqueId()))
                .forEach(targetPlayer -> {
                    final PlayerAccount targetPlayerAccount = Main.getInstance().getDataManager().getPlayerAccountManager()
                            .getPlayerAccount(targetPlayer.getUniqueId());

                    final Byte[] namesBytesForPlayer = Stream.concat(Arrays.stream(ArrayUtils.toObject(player.getName().getBytes(StandardCharsets.UTF_8))),
                                    Arrays.stream(ArrayUtils.toObject(targetPlayer.getName().getBytes(StandardCharsets.UTF_8))))
                            .toArray(Byte[]::new);

                    final Byte[] namesBytesForTargetPlayer = Stream.concat(Arrays.stream(ArrayUtils.toObject(targetPlayer.getName().getBytes(StandardCharsets.UTF_8))),
                                    Arrays.stream(ArrayUtils.toObject(player.getName().getBytes(StandardCharsets.UTF_8))))
                            .toArray(Byte[]::new);

                    final String playerTeamName = UUID.nameUUIDFromBytes(ArrayUtils.toPrimitive(namesBytesForPlayer)).toString().substring(0, 16);
                    final String targetPlayerTeamName = UUID.nameUUIDFromBytes(ArrayUtils.toPrimitive(namesBytesForTargetPlayer)).toString().substring(0, 16);

                    NameTagTeam playerTeam = this.getTeamByName(playerTeamName);
                    NameTagTeam targetPlayerTeam = this.getTeamByName(targetPlayerTeamName);

                    if (playerTeam == null) {
                        playerTeam = createNewTeam(playerTeamName, Relations.ALLY.getPrefix(), "", false);
                    }

                    if (targetPlayerTeam == null) {
                        targetPlayerTeam = createNewTeam(targetPlayerTeamName, Relations.ALLY.getPrefix(), "", false);
                    }

                    final Relations relationWith = targetPlayerAccount.getRelationWith(playerAccount);

                    switch (relationWith) {
                        case ALLY:
                            playerTeam.setPrefix(Relations.ALLY.getPrefix());
                            targetPlayerTeam.setPrefix(Relations.ALLY.getPrefix());

                            break;
                        case NEUTRAL:
                            playerTeam.setPrefix(Relations.NEUTRAL.getPrefix());
                            targetPlayerTeam.setPrefix(Relations.NEUTRAL.getPrefix());

                            break;
                        case ENEMY:
                            playerTeam.setPrefix(Relations.ENEMY.getPrefix());
                            targetPlayerTeam.setPrefix(Relations.ENEMY.getPrefix());
                        default:
                            break;
                    }

                    playerTeam.addPlayer(player);
                    playerTeam.addReceiver(targetPlayer);

                    targetPlayerTeam.addPlayer(targetPlayer);
                    targetPlayerTeam.addReceiver(player);
                });
    }

    /**
     * Obtain a team by checking with the parameter. It will returns null if the team hasn't been found!
     *
     * @param parameter By default, we start by checking the name.
     * @return The team found!
     */
    public NameTagTeam getTeamByName(String parameter) {
        return this.nameTagTeams.stream().filter(nameTagTeam ->
                nameTagTeam.getName().equals(parameter)).findFirst().orElse(null);
    }

    public NameTagTeam getPlayerTeam(UUID playerId) {
        return this.nameTagTeams.stream().filter(nameTagTeam -> nameTagTeam.isTeamMate(playerId))
                .findFirst().orElse(null);
    }

    public Set<NameTagTeam> getNameTagTeams() {
        return nameTagTeams;
    }
}
