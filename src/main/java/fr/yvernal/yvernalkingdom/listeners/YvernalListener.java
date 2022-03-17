package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public interface YvernalListener<E extends Event> extends Listener {

    void onEvent(E event);

    static void registerListeners() {
        final Reflections reflections = new Reflections("fr.yvernal.yvernalkingdom");

        final Set<Class<? extends YvernalListener>> classes = reflections.getSubTypesOf(YvernalListener.class);

        classes.forEach(aClass -> {
            try {
                Bukkit.getPluginManager().registerEvents(aClass.getConstructor().newInstance(), Main.getInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }
}
