package fr.yvernal.yvernalkingdom.listeners;

import fr.yvernal.yvernalkingdom.Main;
import fr.yvernal.yvernalkingdom.config.messages.MessagesManager;
import fr.yvernal.yvernalkingdom.data.DataManager;
import fr.yvernal.yvernalkingdom.utils.nametag.NameTagManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public abstract class YvernalListener<E extends Event> implements Listener {
    protected final DataManager dataManager = Main.getInstance().getDataManager();
    protected final MessagesManager messagesManager = Main.getInstance().getConfigManager().getMessagesManager();

    public abstract void onEvent(E event);

    public static void registerListeners() {
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
