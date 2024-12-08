package de.derfrzocker.anime.calendar.server.validation;

final class StringValidator {

    static boolean isValidLength(String input, int length) {
        return input.length() == length;
    }

    static boolean isValidStartChar(String input, char prefix) {
        return isValidChar(input, prefix, 0);
    }

    static boolean isValidChar(String input, char prefix, int postion) {
        if (postion > input.length()) {
            return false;
        }

        if (postion < 0) {
            return false;
        }

        return input.charAt(postion) == prefix;
    }

    static boolean isValidChars(String input) {
        for (char c : input.toCharArray()) {
            if (c >= 'A' && c <= 'Z' && c != 'O') {
                continue;
            }
            if (c >= '1' && c <= '9') {
                continue;
            }
            return false;
        }

        return true;
    }

    private StringValidator() {

    }
}
