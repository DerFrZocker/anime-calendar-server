package de.derfrzocker.anime.calendar.core.util;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class ValidatorUtil {

    public static boolean isValidId(@NotNull String input, int length, char prefix) {
        Objects.requireNonNull(input, "input must not be null.");

        if (!isValidLength(input, length)) {
            return false;
        }

        if (!isValidStartChar(input, prefix)) {
            return false;
        }

        return hasOnlyValidChars(
                input,
                ValidChars.A_TO_Z_UPPERCASE,
                ValidChars.DIGITS,
                ValidChars.DISALLOW_ZERO,
                ValidChars.DISALLOW_O);
    }

    public static boolean isLengthBetween(@NotNull String input, int minInclusive, int maxInclusive) {
        Objects.requireNonNull(input, "input must not be null.");

        return input.length() >= minInclusive && input.length() <= maxInclusive;
    }

    public static boolean isValidLength(@NotNull String input, int length) {
        Objects.requireNonNull(input, "input must not be null.");

        return input.length() == length;
    }

    public static boolean isValidStartChar(@NotNull String input, char prefix) {
        Objects.requireNonNull(input, "input must not be null.");

        return isValidChar(input, prefix, 0);
    }

    public static boolean isValidChar(@NotNull String input, char prefix, int position) {
        Objects.requireNonNull(input, "input must not be null.");

        if (position >= input.length()) {
            return false;
        }

        if (position < 0) {
            return false;
        }

        return input.charAt(position) == prefix;
    }

    public static boolean hasOnlyValidChars(@NotNull String input, @NotNull ValidChars... validChars) {
        Objects.requireNonNull(input, "input must not be null.");
        Objects.requireNonNull(validChars, "validChars must not be null.");

        boolean aTzUppercase = ValidChars.contains(validChars, ValidChars.A_TO_Z_UPPERCASE);
        boolean aTzLowercase = ValidChars.contains(validChars, ValidChars.A_TO_Z_LOWERCASE);
        boolean digits = ValidChars.contains(validChars, ValidChars.DIGITS);
        boolean disallowZero = ValidChars.contains(validChars, ValidChars.DISALLOW_ZERO);
        boolean disallowO = ValidChars.contains(validChars, ValidChars.DISALLOW_O);

        for (char c : input.toCharArray()) {
            if (aTzUppercase && c >= 'A' && c <= 'Z') {
                if (disallowO && c == 'O') {
                    return false;
                }
                continue;
            }

            if (aTzLowercase && c >= 'a' && c <= 'z') {
                if (disallowO && c == 'o') {
                    return false;
                }
                continue;
            }

            if (digits && c >= '0' && c <= '9') {
                if (disallowZero && c == '0') {
                    return false;
                }
                continue;
            }
            return false;
        }

        return true;
    }

    public enum ValidChars {

        A_TO_Z_UPPERCASE, A_TO_Z_LOWERCASE, DIGITS, DISALLOW_ZERO, DISALLOW_O;

        public static boolean contains(@NotNull ValidChars[] validChars, @NotNull ValidChars target) {
            Objects.requireNonNull(validChars, "validChars must not be null.");
            Objects.requireNonNull(target, "target must not be null.");

            for (ValidChars validChar : validChars) {
                if (validChar == target) {
                    return true;
                }
            }

            return false;
        }
    }

    private ValidatorUtil() {

    }
}
