package fr.yvernal.yvernalkingdom.config.game;

import fr.twah2em.mcreflection.PrimitiveAndWrapper;
import fr.yvernal.yvernalkingdom.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class GameConfigManager {
    private final FileConfiguration config;

    public GameConfigManager() {
        this.config = Main.getInstance().getConfig();
    }

    public <T> T get(String path, Class<? extends T> clazz) {
        Object o = config.get("game." + path);

        if (o != null) {
            if (clazz.isAssignableFrom(o.getClass())) {
                return clazz.cast(o);
            }

            if (clazz.isPrimitive()) {
                final boolean objectIsNumber = o instanceof Number;

                clazz = (Class<? extends T>) PrimitiveAndWrapper.Companion.wrapper(clazz);

                if (objectIsNumber) {
                    if (clazz == Double.class) {
                        o = ((Number) o).doubleValue();
                    } else if (clazz == Float.class) {
                        o = ((Number) o).floatValue();
                    } else if (clazz == Long.class) {
                        o = ((Number) o).longValue();
                    } else if (clazz == Integer.class) {
                        o = ((Number) o).intValue();
                    } else if (clazz == Short.class) {
                        o = ((Number) o).shortValue();
                    } else if (clazz == Byte.class) {
                        o = ((Number) o).byteValue();
                    }

                    return clazz.cast(o);
                }
            }
        }

        return null;
    }

    public String getString(String path) {
        return get(path, String.class);
    }
}
