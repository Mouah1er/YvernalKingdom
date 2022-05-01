package fr.yvernal.yvernalkingdom.teams;

import fr.twah2em.nametag.Relation;
import fr.twah2em.nametag.Team;
import fr.twah2em.nametag.TeamManager;
import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.data.accounts.PlayerAccount;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

public class YvernalTeamManager extends TeamManager {

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

                    Team playerTeam = this.teamByName(playerTeamName);
                    Team targetPlayerTeam = this.teamByName(targetPlayerTeamName);

                    if (playerTeam == null) {
                        playerTeam = createTeam(playerTeamName, Relation.ALLY.getPrefix(), "");
                    }

                    if (targetPlayerTeam == null) {
                        targetPlayerTeam = createTeam(targetPlayerTeamName, Relation.ALLY.getPrefix(), "");
                    }

                    final Relation relationWith = targetPlayerAccount.getRelationWith(playerAccount);

                    switch (relationWith) {
                        case ALLY:
                            playerTeam.setPrefix(Relation.ALLY.getPrefix());
                            targetPlayerTeam.setPrefix(Relation.ALLY.getPrefix());

                            break;
                        case NEUTRAL:
                            playerTeam.setPrefix(Relation.NEUTRAL.getPrefix());
                            targetPlayerTeam.setPrefix(Relation.NEUTRAL.getPrefix());

                            break;
                        case ENEMY:
                            playerTeam.setPrefix(Relation.ENEMY.getPrefix());
                            targetPlayerTeam.setPrefix(Relation.ENEMY.getPrefix());
                        default:
                            break;
                    }

                    playerTeam.addPlayer(player);
                    playerTeam.addReceiver(targetPlayer);

                    targetPlayerTeam.addPlayer(targetPlayer);
                    targetPlayerTeam.addReceiver(player);
                });
    }
}
