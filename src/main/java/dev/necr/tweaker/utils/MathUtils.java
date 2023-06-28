package dev.necr.tweaker.utils;

import dev.necr.tweaker.callbacks.IsDoubleCallback;
import dev.necr.tweaker.callbacks.IsIntegerCallback;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtils {

    /**
     * Checks if a string is parsable to an Integer
     *
     * @param s the string
     * @return {@link IsIntegerCallback}
     */
    public IsIntegerCallback isInteger(String s) {
        IsIntegerCallback callback = new IsIntegerCallback(false, 0);
        if (s == null) {
            return callback;
        }

        try {
            return callback.setInteger(true).setValue(Integer.parseInt(s));
        } catch (Exception ignored) {
            return callback;
        }
    }

    /**
     * Checks if a string is parsable to a double
     *
     * @param s the string
     * @return {@link IsDoubleCallback}
     */
    public IsDoubleCallback isDouble(String s) {
        IsDoubleCallback callback = new IsDoubleCallback(false, 0.0);
        if (s == null) {
            return callback;
        }

        try {
            return callback.setDouble(true).setValue(Double.parseDouble(s));
        } catch (Exception ignored) {
            return callback;
        }
    }
}
