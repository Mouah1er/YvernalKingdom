package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.messages.MessagesManager;
import fr.yvernal.yvernalkingdom.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public abstract class YvernalListener<E extends Event> implements Listener {
    protected final DataManager dataManager = Main.getInstance().getDataManager();
    protected final MessagesManager messagesManager = Main.getInstance().getConfigManager().getMessagesManager();

    public static void registerListeners() {
        final Reflections reflections = new Reflections("fr.yvernal.yvernalkingdom");

        final Set<Class<? extends YvernalListener>> classes = reflections.getSubTypesOf(YvernalListener.class);

        classes.forEach(aClass -> {
            System.out.println(aClass.getSimpleName());
            try {
                Bukkit.getPluginManager().registerEvents(aClass.getConstructor().newInstance(), Main.getInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }

    public abstract void onEvent(E event);
}
