package fr.yvernal.yvernalkingdom.kingdoms.guilds;

import java.util.HashMap;
import java.util.Map;

public enum GuildRank {
    MASTER("**Maître de guilde"),
    OFFICER("*Offcier"),
    SENIOR_MEMBER("Membre+"),
    MEMBER("Membre"),
    NO_GUILD("no-guild")

    ;

    private static final Map<String, GuildRank> BY_NAME = new HashMap<>();

    static {
        for (GuildRank value : values()) {
            BY_NAME.put(value.getName(), value);
        }
    }

    private final String name;

    GuildRank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static GuildRank getByName(String name) {
        return BY_NAME.get(name);
    }
}
