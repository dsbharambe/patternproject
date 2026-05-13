package com.patternproject.rendering;

import java.io.PrintStream;
import java.util.Locale;

/**
 * Alphabet (A–Z) patterns. Row width is capped at 26 letters from {@code A}; larger {@code n} only adds
 * further rows that continue with {@code A…Z} wrapping, except where noted.
 */
public final class LetterShapes {

    private static final int SPAN = 26;
    private static final String[][] STAR_LETTER_GLYPHS = {
            {"01110", "10001", "10001", "11111", "10001", "10001", "10001"}, // A
            {"11110", "10001", "10001", "11110", "10001", "10001", "11110"}, // B
            {"01110", "10001", "10000", "10000", "10000", "10001", "01110"}, // C
            {"11110", "10001", "10001", "10001", "10001", "10001", "11110"}, // D
            {"11111", "10000", "10000", "11110", "10000", "10000", "11111"}, // E
            {"11111", "10000", "10000", "11110", "10000", "10000", "10000"}, // F
            {"01110", "10001", "10000", "10000", "10011", "10001", "01110"}, // G
            {"10001", "10001", "10001", "11111", "10001", "10001", "10001"}, // H
            {"11111", "00100", "00100", "00100", "00100", "00100", "11111"}, // I
            {"00001", "00001", "00001", "00001", "10001", "10001", "01110"}, // J
            {"10001", "10010", "10100", "11000", "10100", "10010", "10001"}, // K
            {"10000", "10000", "10000", "10000", "10000", "10000", "11111"}, // L
            {"10001", "11011", "10101", "10101", "10001", "10001", "10001"}, // M
            {"10001", "11001", "10101", "10011", "10001", "10001", "10001"}, // N
            {"01110", "10001", "10001", "10001", "10001", "10001", "01110"}, // O
            {"11110", "10001", "10001", "11110", "10000", "10000", "10000"}, // P
            {"01110", "10001", "10001", "10001", "10101", "10010", "01101"}, // Q
            {"11110", "10001", "10001", "11110", "10100", "10010", "10001"}, // R
            {"01111", "10000", "10000", "01110", "00001", "00001", "11110"}, // S
            {"11111", "00100", "00100", "00100", "00100", "00100", "00100"}, // T
            {"10001", "10001", "10001", "10001", "10001", "10001", "01110"}, // U
            {"10001", "10001", "10001", "10001", "10001", "01010", "00100"}, // V
            {"10001", "10001", "10001", "10101", "10101", "10101", "01010"}, // W
            {"10001", "10001", "01010", "00100", "01010", "10001", "10001"}, // X
            {"10001", "10001", "01010", "00100", "00100", "00100", "00100"}, // Y
            {"11111", "00001", "00010", "00100", "01000", "10000", "11111"}  // Z
    };

    private LetterShapes() {
    }

    /** Prints one uppercase letter (A-Z) drawn with stars using a fixed 5x7 glyph. */
    public static void printLetterInStarPattern(PrintStream out, String input) {
        if (input == null || input.trim().isEmpty()) {
            out.println("Please enter a letter from A to Z.");
            return;
        }
        char letter = input.trim().toUpperCase(Locale.ROOT).charAt(0);
        if (letter < 'A' || letter > 'Z') {
            out.println("Only A-Z letters are supported.");
            return;
        }

        out.println("Star pattern for letter " + letter + ":");
        String[] glyph = STAR_LETTER_GLYPHS[letter - 'A'];
        for (String row : glyph) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < row.length(); i++) {
                line.append(row.charAt(i) == '1' ? '*' : ' ');
                if (i < row.length() - 1) {
                    line.append(' ');
                }
            }
            out.println(line);
        }
    }

    /** Row {@code i}: {@code A} through the {@code i}-th letter (wraps after Z if {@code i} &gt; 26). */
    public static void simpleLetterTriangle(PrintStream out, int n) {
        for (int i = 1; i <= n; i++) {
            out.println(joinLettersForward(1, i));
        }
    }

    /** Row {@code i}: the {@code i}-th letter repeated {@code i} times. */
    public static void repeatedLetterTriangle(PrintStream out, int n) {
        for (int i = 1; i <= n; i++) {
            char c = letterAt(i);
            StringBuilder sb = new StringBuilder();
            for (int j = 1; j <= i; j++) {
                if (j > 1) {
                    sb.append(' ');
                }
                sb.append(c);
            }
            out.println(sb);
        }
    }

    /** Row {@code i}: from the {@code i}-th letter down to {@code A} (single cycle A–Z per position). */
    public static void reverseLetterTriangle(PrintStream out, int n) {
        for (int i = 1; i <= n; i++) {
            out.println(joinLettersBackward(i));
        }
    }

    /** Right-aligned growing triangle (spaces on the left). */
    public static void leftLetterTriangle(PrintStream out, int n) {
        int maxLen = joinLettersForward(1, Math.min(n, SPAN)).length();
        for (int i = 1; i <= n; i++) {
            String core = joinLettersForward(1, i);
            int pad = maxLen - core.length();
            if (pad < 0) {
                pad = 0;
            }
            out.println(spaces(pad) + core);
        }
    }

    /** Centered rows {@code A …} up to row length. */
    public static void letterPyramid(PrintStream out, int n) {
        int maxLen = joinLettersForward(1, n).length();
        for (int i = 1; i <= n; i++) {
            String core = joinLettersForward(1, i);
            int pad = (maxLen - core.length()) / 2;
            out.println(spaces(pad) + core);
        }
    }

    /** Centered palindrome: {@code A}, {@code A B A}, {@code A B C B A}, … */
    public static void palindromeLetterPyramid(PrintStream out, int n) {
        int maxLen = buildLetterPalindromeLine(n).length();
        for (int i = 1; i <= n; i++) {
            String line = buildLetterPalindromeLine(i);
            int pad = (maxLen - line.length()) / 2;
            out.println(spaces(pad) + line);
        }
    }

    /** Palindrome pyramid plus mirror below the widest row. */
    public static void letterDiamond(PrintStream out, int n) {
        if (n < 1) {
            return;
        }
        palindromeLetterPyramid(out, n);
        int maxLen = buildLetterPalindromeLine(n).length();
        for (int i = n - 1; i >= 1; i--) {
            String line = buildLetterPalindromeLine(i);
            int pad = (maxLen - line.length()) / 2;
            out.println(spaces(pad) + line);
        }
    }

    /** Inverted centered pyramid of letter runs {@code A … (n-i+1)}. */
    public static void descendingLetterPyramid(PrintStream out, int n) {
        int maxLen = joinLettersForward(1, n).length();
        for (int i = 1; i <= n; i++) {
            int len = n - i + 1;
            String core = joinLettersForward(1, len);
            int pad = (maxLen - core.length()) / 2;
            out.println(spaces(pad) + core);
        }
    }

    /** Only first and last letter on inner rows; full bottom row {@code A…}. */
    public static void hollowLetterPyramid(PrintStream out, int n) {
        int maxLen = joinLettersForward(1, n).length();
        for (int i = 1; i <= n; i++) {
            String solid = joinLettersForward(1, i);
            String hollow;
            if (i == 1 || i == n) {
                hollow = solid;
            } else {
                String last = String.valueOf(letterAt(i));
                hollow = "A" + spaces(solid.length() - 1 - last.length()) + last;
            }
            int pad = (maxLen - hollow.length()) / 2;
            if (pad < 0) {
                pad = 0;
            }
            out.println(spaces(pad) + hollow);
        }
    }

    /** {@code n×n} grid filled row-major with {@code A B C …} (wrapping past Z). */
    public static void sequentialLetterSquare(PrintStream out, int n) {
        int k = 0;
        for (int r = 0; r < n; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < n; c++) {
                if (c > 0) {
                    sb.append(' ');
                }
                sb.append((char) ('A' + (k++ % SPAN)));
            }
            out.println(sb);
        }
    }

    /** Hourglass shape using consecutive letters row by row. */
    public static void letterHourglass(PrintStream out, int n) {
        int idx = 0;
        for (int i = n; i >= 1; i--) {
            StringBuilder sb = new StringBuilder();
            sb.append(spaces((n - i) * 2));
            for (int j = 0; j < i; j++) {
                if (j > 0) {
                    sb.append(' ');
                }
                sb.append((char) ('A' + (idx++ % SPAN)));
            }
            out.println(sb);
        }
        for (int i = 2; i <= n; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(spaces((n - i) * 2));
            for (int j = 0; j < i; j++) {
                if (j > 0) {
                    sb.append(' ');
                }
                sb.append((char) ('A' + (idx++ % SPAN)));
            }
            out.println(sb);
        }
    }

    /** Butterfly: row {@code i} shows {@code A…} left and its mirror on the right. */
    public static void letterButterfly(PrintStream out, int n) {
        for (int i = 1; i <= n; i++) {
            out.println(butterflyRow(i, n));
        }
        for (int i = n; i >= 1; i--) {
            out.println(butterflyRow(i, n));
        }
    }

    private static String butterflyRow(int wing, int n) {
        String left = joinLettersForward(1, wing);
        String right = reverseLetterTokens(left);
        int gap = 2 * (n - wing);
        return left + spaces(gap) + right;
    }

    private static String reverseLetterTokens(String spacedLetters) {
        String[] parts = spacedLetters.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int k = parts.length - 1; k >= 0; k--) {
            if (k < parts.length - 1) {
                sb.append(' ');
            }
            sb.append(parts[k]);
        }
        return sb.toString();
    }

    private static char letterAt(int i1Based) {
        int x = i1Based - 1;
        if (x < 0) {
            x = 0;
        }
        return (char) ('A' + (x % SPAN));
    }

    private static String joinLettersForward(int from, int to) {
        StringBuilder sb = new StringBuilder();
        for (int j = from; j <= to; j++) {
            if (j > from) {
                sb.append(' ');
            }
            sb.append(letterAt(j));
        }
        return sb.toString();
    }

    private static String joinLettersBackward(int high1Based) {
        StringBuilder sb = new StringBuilder();
        for (int j = high1Based; j >= 1; j--) {
            if (j < high1Based) {
                sb.append(' ');
            }
            sb.append(letterAt(j));
        }
        return sb.toString();
    }

    private static String buildLetterPalindromeLine(int peak) {
        StringBuilder sb = new StringBuilder();
        for (int j = 1; j <= peak; j++) {
            if (j > 1) {
                sb.append(' ');
            }
            sb.append(letterAt(j));
        }
        for (int j = peak - 1; j >= 1; j--) {
            sb.append(' ').append(letterAt(j));
        }
        return sb.toString();
    }

    private static String spaces(int n) {
        if (n <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }
}
