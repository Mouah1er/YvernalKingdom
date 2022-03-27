package fr.yvernal.yvernalkingdom.kingdoms.guilds;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public enum GuildRank {
    MASTER("**Maître de guilde", ChatColor.RED),
    OFFICER("*Offcier", ChatColor.AQUA),
    SENIOR_MEMBER("Membre+", ChatColor.GREEN),
    MEMBER("Membre", ChatColor.DARK_GREEN),
    NO_GUILD("no-guild", ChatColor.GRAY)

    ;

    private static final Map<String, GuildRank> BY_NAME = new HashMap<>();

    static {
        for (GuildRank value : values()) {
            BY_NAME.put(value.getName(), value);
        }
    }

    private final String name;
    private final ChatColor chatColor;

    GuildRank(String name, ChatColor chatColor) {
        this.name = name;
        this.chatColor = chatColor;
    }

    public String getName() {
        return name;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public String getColorizedName() {
        return getChatColor() + getName();
    }

    public static GuildRank getByName(String name) {
        return BY_NAME.get(name);
    }
}
