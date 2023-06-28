package dev.necr.tweaker.utils;

import dev.necr.tweaker.callbacks.IsDoubleCallback;
import dev.necr.tweaker.callbacks.IsIntegerCallback;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

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
     * @deprecated use {@link StringUtils#colorizeText(String)} instead
     */
    @Deprecated
    public static String colorize(String text) {
        return text == null ? " " : ChatColor.translateAlternateColorCodes('&', text);
    }

    @Deprecated
    public static String[] colorize(String[] lines) {
        return colorize(Arrays.asList(lines.clone())).toArray(new String[0]);
    }

    @Deprecated
    public static List<String> colorize(List<String> s) {
        List<String> colorized = new ArrayList<>();
        s.forEach((st) -> colorized.add(colorize(st)));
        return colorized;
    }

    /**
     * Colorizes a string
     * <p> This method supports the following formatting codes:
     * <ul> <li> &0 - Black </li>
     * <li> &1 - Dark Blue </li>
     * <li> &2 - Dark Green </li>
     * <li> &3 - Dark Aqua </li>
     * <li> &4 - Dark Red </li>
     * <li> &5 - Dark Purple </li>
     * <li> &6 - Gold </li>
     * <li> &7 - Gray </li>
     * <li> &8 - Dark Gray </li>
     * <li> &9 - Blue </li>
     * <li> &a - Green </li>
     * <li> &b - Aqua </li>
     * <li> &c - Red </li>
     * <li> &d - Light Purple </li>
     * <li> &e - Yellow </li>
     * <li> &f - White </li>
     * <li> &k - Obfuscated </li>
     * <li> &l - Bold </li>
     * <li> &m - Strikethrough </li>
     * <li> &n - Underline </li>
     * <li> &o - Italic </li>
     * <li> &r - Reset </li> </ul>
     *
     * @param text the text to colorize
     * @return the colorized text
     */
    public static String colorizeText(String text) {
        TextColor color = NamedTextColor.WHITE; // Default color

        // Check if the text starts with "&" and has at least two characters
        if (text.startsWith("&") && text.length() >= 2) {
            char code = text.charAt(1);
            NamedTextColor namedColor = NamedTextColor.NAMES.value(String.valueOf(code)); // Get color from the code

            // If the code represents a valid color, update the color variable
            if (namedColor != null) {
                color = namedColor;
            }
        }

        // Create the colored component and apply formatting if needed
        Component component = Component.text(text.substring(2)).color(color);

        // Check for additional formatting codes
        for (int i = 2; i < text.length(); i++) {
            char code = text.charAt(i);

            switch (code) {
                case 'l' -> component = component.decorate(TextDecoration.BOLD);
                case 'o' -> component = component.decorate(TextDecoration.ITALIC);
                case 'n' -> component = component.decorate(TextDecoration.UNDERLINED);
                case 'm' -> component = component.decorate(TextDecoration.STRIKETHROUGH);
                case 'k' -> component = component.decorate(TextDecoration.OBFUSCATED);
                default -> {}
            }
        }

        return component.toString();
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
