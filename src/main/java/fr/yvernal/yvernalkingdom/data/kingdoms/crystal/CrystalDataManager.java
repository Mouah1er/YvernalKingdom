package fr.yvernal.yvernalkingdom.data.kingdoms.crystal;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.game.GameConfigManager;
import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.data.DataManagerTemplate;
import fr.yvernal.yvernalkingdom.kingdoms.Kingdom;
import fr.yvernal.yvernalkingdom.kingdoms.crystal.Crystal;
import fr.yvernal.yvernalkingdom.utils.list.GlueList;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CrystalDataManager implements DataManagerTemplate<Crystal> {
    private final DataManager dataManager;
    private final List<Crystal> crystals;

    public CrystalDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
        this.crystals = new GlueList<>();
    }

    @Override

    public List<Crystal> getAllFromDatabase() {
        final List<Crystal> crystals = new GlueList<>();

        dataManager.getDatabaseManager().query("SELECT * FROM crystals", resultSet -> {
            try {
                while (resultSet.next()) {
                    crystals.add(getFromDatabase(resultSet.getString("kingdomNumber")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return crystals;
    }

    @Override
    public Crystal getFromDatabase(String kingdomNumber) {
        final AtomicReference<Crystal> crystal = new AtomicReference<>();

        dataManager.getDatabaseManager().query("SELECT * FROM crystals WHERE kingdomNumber=?", resultSet -> {
            try {
                final Kingdom kingdom = dataManager.getKingdomDataManager().getKingdomByNumber(kingdomNumber);

                if (resultSet.next()) {
                    final float health = resultSet.getFloat("health");
                    final float exp = resultSet.getFloat("exp");
                    final int level = resultSet.getInt("level");
                    final boolean isDestroyed = resultSet.getBoolean("isDestroyed");
                    final Date date = resultSet.getString("destructionDate").equals("no-destruction") ? null :
                            new Date(Long.parseLong(resultSet.getString("destructionDate")));

                    crystal.set(new Crystal(new CrystalData(kingdom, health, exp, level, isDestroyed, date),
                            HologramsAPI.createHologram(Main.getInstance(), kingdom.getKingdomProperties().getCrystalLocation()
                                    .clone()
                                    .add(0, 3.5, 0))));
                } else {
                    final GameConfigManager gameConfigManager = Main.getInstance().getConfigManager().getGameConfigManager();
                    final Crystal crystal1 = new Crystal(new CrystalData(kingdom, gameConfigManager.get("base-crystal-health", float.class),
                            0, 1, false, null),
                            HologramsAPI.createHologram(Main.getInstance(), kingdom.getKingdomProperties().getCrystalLocation()
                                    .clone()
                                    .add(0, 3.5, 0)));

                    addToDatabase(crystal1);
                    crystal.set(crystal1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, kingdomNumber);

        return crystal.get();
    }

    @Override
    public void addToDatabase(Crystal crystal) {
        dataManager.getDatabaseManager().update("INSERT INTO crystals (kingdomNumber, health, exp, level, isDestroyed, destructionDate)" +
                        " VALUES (" +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?, " +
                        "?)",
                crystal.getCrystalData().getKingdom().getKingdomProperties().getNumber(),
                crystal.getCrystalData().getHealth(),
                crystal.getCrystalData().getExp(),
                crystal.getCrystalData().getLevel(),
                crystal.getCrystalData().isDestroyed(),
                crystal.getCrystalData().getDestructionDate() == null ? "no-destruction" : crystal.getCrystalData().getDestructionDate());
    }

    @Override
    public void updateToDatabase(Crystal crystal) {
        dataManager.getDatabaseManager().update("UPDATE crystals SET " +
                        "health=?, " +
                        "exp=?, " +
                        "level=?, " +
                        "isDestroyed=?, " +
                        "destructionDate=? " +
                        "WHERE kingdomNumber=?",
                crystal.getCrystalData().getHealth(),
                crystal.getCrystalData().getExp(),
                crystal.getCrystalData().getLevel(),
                crystal.getCrystalData().isDestroyed(),
                crystal.getCrystalData().getDestructionDate() == null ? "no-destruction" : crystal.getCrystalData().getDestructionDate(),
                crystal.getCrystalData().getKingdom().getKingdomProperties().getNumber());
    }

    @Override
    public void deleteFromDatabase(Crystal crystal) {
        dataManager.getDatabaseManager().update("DELETE FROM cristals " +
                        "WHERE kingdomNumber=?",
                crystal.getCrystalData().getKingdom().getKingdomProperties().getNumber());
    }

    public List<Crystal> getCrystals() {
        return crystals;
    }
}
