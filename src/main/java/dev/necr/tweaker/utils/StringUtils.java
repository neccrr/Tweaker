package dev.necr.tweaker.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
@SuppressWarnings("unused")
public class StringUtils {

    /**
     * Colorizes string         
     *
     * @param text the string to colorize
     * @return colorized string
     *
     */
    public static String colorize(String text) {
        return text == null ? " " : ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String[] colorize(String[] lines) {
        return colorize(Arrays.asList(lines.clone())).toArray(new String[0]);
    }

    public static List<String> colorize(List<String> s) {
        List<String> colorized = new ArrayList<>();
        s.forEach((st) -> colorized.add(colorize(st)));
        return colorized;
    }

    /**
     * Uppercase the first letter of the given string
     *
     * @param text String to strip of color
     * @return colorized string
     */
    public static String toUppercaseFirstChar(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }
}
