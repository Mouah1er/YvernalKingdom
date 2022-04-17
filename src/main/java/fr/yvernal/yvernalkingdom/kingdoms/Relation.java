package fr.yvernal.yvernalkingdom.kingdoms;

public enum Relation {
    ALLY("§a"),
    NEUTRAL("§e"),
    ENEMY("§c"),

    ;

    private final String prefix;

    Relation(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
