package org.coryjk.AdventOfCode.commons;

public final class Utils {
    public static int countChars(final char c, final char[] chars) {
        int count = 0;
        for (final char ch : chars) {
            if (ch == c) {
                count++;
            }
        }
        return count;
    }

    public static int countChars(final char c, final char[][] matrix) {
        int count = 0;
        for (final char[] row : matrix) {
            count += countChars(c, row);
        }
        return count;
    }
}
