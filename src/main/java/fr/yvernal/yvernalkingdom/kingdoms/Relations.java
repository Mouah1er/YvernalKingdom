package fr.yvernal.yvernalkingdom.kingdoms;

public enum Relations {
    ALLY("§a"),
    NEUTRAL("§e"),
    ENEMY("§c"),

    ;

    private final String prefix;

    Relations(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
