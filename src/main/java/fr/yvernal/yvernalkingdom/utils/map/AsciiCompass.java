package fr.yvernal.yvernalkingdom.utils.map;

import fr.yvernal.yvernalkingdom.utils.list.GlueList;

import java.util.List;

import static fr.yvernal.yvernalkingdom.utils.map.AsciiCompassDirection.*;

/**
 * @author MassiveCraft
 */
public class AsciiCompass {

    public static List<String> getAsciiCompass(double degrees) {
        return getAsciiCompass(AsciiCompassDirection.getByDegrees(degrees));
    }

    private static List<String> getAsciiCompass(AsciiCompassDirection directionFacing) {
        final List<String> list = new GlueList<>();

        list.add(visualizeRow(directionFacing, NW, N, NE));
        list.add(visualizeRow(directionFacing, W, NONE, E));
        list.add(visualizeRow(directionFacing, SW, S, SE));

        return list;
    }

    private static String visualizeRow(AsciiCompassDirection directionFacing, AsciiCompassDirection... cardinals) {
        if (cardinals == null) throw new NullPointerException("cardinals");

        final StringBuilder rows = new StringBuilder(cardinals.length);

        for (AsciiCompassDirection asciiCardinal : cardinals) {
            rows.append(asciiCardinal.visualize(directionFacing));
        }

        return rows.toString();
    }
}
