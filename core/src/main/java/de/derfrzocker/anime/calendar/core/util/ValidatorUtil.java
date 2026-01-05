package de.derfrzocker.anime.calendar.core.util;

import org.jetbrains.annotations.NotNull;

public final class ValidatorUtil {

    public static boolean isValidId(@NotNull String input, int length, char prefix) {
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
        return input.length() >= minInclusive && input.length() <= maxInclusive;
    }

    public static boolean isValidLength(@NotNull String input, int length) {
        return input.length() == length;
    }

    public static boolean isValidStartChar(@NotNull String input, char prefix) {
        return isValidChar(input, prefix, 0);
    }

    public static boolean isValidChar(@NotNull String input, char prefix, int postion) {
        if (postion > input.length()) {
            return false;
        }

        if (postion < 0) {
            return false;
        }

        return input.charAt(postion) == prefix;
    }

    public static boolean hasOnlyValidChars(@NotNull String input, ValidChars... validChars) {
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

        public static boolean contains(ValidChars[] validChars, ValidChars target) {
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
