package fr.yvernal.yvernalkingdom.utils.map;

import org.bukkit.ChatColor;

/**
 * @author MassiveCraft
 */
public enum AsciiCompassDirection {
    N('N'),
    NE('/'),
    E('E'),
    SE('\\'),
    S('S'),
    SW('/'),
    W('W'),
    NW('\\'),
    NONE('+'),

    ;

    public static final ChatColor ACTIVE = ChatColor.RED;
    public static final ChatColor INACTIVE = ChatColor.YELLOW;
    private final char asciiChar;
    AsciiCompassDirection(final char asciiChar) {
        this.asciiChar = asciiChar;
    }

    public static AsciiCompassDirection getByDegrees(double degrees) {
        degrees = (degrees - 157) % 360;
        if (degrees < 0) degrees += 360;

        int ordinal = (int) Math.floor(degrees / 45);

        return AsciiCompassDirection.values()[ordinal];
    }

    public char getAsciiChar() {
        return this.asciiChar;
    }

    public String visualize(AsciiCompassDirection directionFacing) {
        boolean isFacing = this.isFacing(directionFacing);
        ChatColor color = this.getColor(isFacing);

        return color.toString() + this.getAsciiChar();
    }

    private boolean isFacing(AsciiCompassDirection directionFacing) {
        return this == directionFacing;
    }

    private ChatColor getColor(boolean active) {
        return active ? ACTIVE : INACTIVE;
    }
}
