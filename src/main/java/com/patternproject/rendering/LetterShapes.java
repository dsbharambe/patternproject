package com.patternproject.rendering;

import java.io.PrintStream;

/**
 * Alphabet (A–Z) patterns. Row width is capped at 26 letters from {@code A}; larger {@code n} only adds
 * further rows that continue with {@code A…Z} wrapping, except where noted.
 */
public final class LetterShapes {

    private static final int SPAN = 26;

    private LetterShapes() {
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
