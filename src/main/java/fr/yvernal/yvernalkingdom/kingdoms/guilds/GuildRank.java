package fr.yvernal.yvernalkingdom.kingdoms.guilds;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public enum GuildRank {
    MASTER("**Maître de guilde", "**", ChatColor.RED, 3),
    OFFICER("*Officier", "*", ChatColor.AQUA, 2),
    SENIOR_MEMBER("Membre+", "+", ChatColor.GREEN, 1),
    MEMBER("Membre-", "-", ChatColor.DARK_GREEN, 0),
    NO_GUILD("no-guild", "§7", ChatColor.GRAY, -1);

    private static final Map<String, GuildRank> BY_NAME = new HashMap<>();

    static {
        for (GuildRank value : values()) {
            BY_NAME.put(value.getName(), value);
        }
    }

    private final String name;
    private final String prefix;
    private final ChatColor chatColor;
    private final int power;

    GuildRank(String name, String prefix, ChatColor chatColor, int power) {
        this.name = name;
        this.prefix = prefix;
        this.chatColor = chatColor;
        this.power = power;
    }

    public static GuildRank getByName(String name) {
        return BY_NAME.get(name);
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public String getColorizedName() {
        return getChatColor() + getName();
    }

    public int getPower() {
        return power;
    }

    public GuildRank getNextRank() {
        return GuildRank.values()[this.ordinal() - 1];
    }

    public GuildRank getPreviousRank() {
        return GuildRank.values()[this.ordinal() + 1];
    }
}
