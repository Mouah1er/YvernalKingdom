package fr.yvernal.yvernalkingdom.commands;

import fr.yvernal.yvernalkingdom.Main;
import org.bukkit.command.*;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public interface YvernalCommand extends CommandExecutor, TabCompleter {

    @Override
    boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    @Override
    List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args);

    static void registerCommands() {
        final Reflections reflections = new Reflections("fr.yvernal.yvernalkingdom");

        final Set<Class<? extends YvernalCommand>> classes = reflections.getSubTypesOf(YvernalCommand.class);

        classes.forEach(aClass -> {
            try {
                final PluginCommand command = Main.getInstance().getCommand(aClass.getSimpleName().replace("Command", "")
                        .toLowerCase(Locale.ROOT));
                final YvernalCommand yvernalCommand = aClass.getConstructor().newInstance();
                command.setExecutor(yvernalCommand);
                command.setTabCompleter(yvernalCommand);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }
}
