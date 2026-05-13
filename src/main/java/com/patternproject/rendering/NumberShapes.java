package com.patternproject.rendering;

import java.io.PrintStream;

/**
 * Number console patterns printed to a {@link PrintStream}. Intended for modest {@code n} so values fit in {@code int}.
 */
public final class NumberShapes {

    private NumberShapes() {
    }

    /** Row {@code i}: {@code 1 2 … i}. */
    public static void simpleNumberTriangle(PrintStream out, int n) {
        for (int i = 1; i <= n; i++) {
            out.println(joinRange(1, i));
        }
    }

    /** Row {@code i}: {@code i} repeated {@code i} times. */
    public static void repeatedNumberTriangle(PrintStream out, int n) {
        for (int i = 1; i <= n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 1; j <= i; j++) {
                if (j > 1) {
                    sb.append(' ');
                }
                sb.append(i);
            }
            out.println(sb);
        }
    }

    /** Row {@code i}: {@code i i-1 … 1}. */
    public static void reverseNumberTriangle(PrintStream out, int n) {
        for (int i = 1; i <= n; i++) {
            out.println(joinRangeDown(i, 1));
        }
    }

    /** Floyd: consecutive integers row by row. */
    public static void floydsTriangle(PrintStream out, int n) {
        int num = 1;
        for (int i = 1; i <= n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                if (j > 0) {
                    sb.append(' ');
                }
                sb.append(num++);
            }
            out.println(sb);
        }
    }

    /** Left-aligned triangle: {@code (r+c) % 2} at column {@code c} of row {@code r} (0-based). */
    public static void zeroOneTriangle(PrintStream out, int n) {
        for (int r = 0; r < n; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c <= r; c++) {
                if (c > 0) {
                    sb.append(' ');
                }
                sb.append((r + c) % 2);
            }
            out.println(sb);
        }
    }

    /** Centered: row {@code i} is {@code 1 … i}. */
    public static void numberPyramid(PrintStream out, int n) {
        int maxLen = joinRange(1, n).length();
        for (int i = 1; i <= n; i++) {
            String core = joinRange(1, i);
            int pad = (maxLen - core.length()) / 2;
            out.println(spaces(pad) + core);
        }
    }

    /** Centered palindrome: {@code 1}, {@code 1 2 1}, {@code 1 2 3 2 1}, … */
    public static void palindromeNumberPyramid(PrintStream out, int n) {
        int maxLen = buildPalindromeLine(n).length();
        for (int i = 1; i <= n; i++) {
            String line = buildPalindromeLine(i);
            int pad = (maxLen - line.length()) / 2;
            out.println(spaces(pad) + line);
        }
    }

    /** Palindrome pyramid plus mirror (no duplicate peak row). */
    public static void diamondNumberPattern(PrintStream out, int n) {
        if (n < 1) {
            return;
        }
        palindromeNumberPyramid(out, n);
        for (int i = n - 1; i >= 1; i--) {
            String line = buildPalindromeLine(i);
            int maxLen = buildPalindromeLine(n).length();
            int pad = (maxLen - line.length()) / 2;
            out.println(spaces(pad) + line);
        }
    }

    /** {@code n×n} grid, row-major {@code 1 … n²}. */
    public static void sequentialNumberSquare(PrintStream out, int n) {
        int k = 1;
        int w = widthOf(n * n);
        for (int r = 0; r < n; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < n; c++) {
                if (c > 0) {
                    sb.append(' ');
                }
                sb.append(padNumber(k++, w));
            }
            out.println(sb);
        }
    }

    /**
     * Pack {@code 1 … n(n+1)/2} into a triangular block (row lengths {@code n, n-1, …, 1}); print each row as
     * descending numbers, top row first.
     */
    public static void reverseSequentialTriangle(PrintStream out, int n) {
        int next = n * (n + 1) / 2;
        for (int i = 1; i <= n; i++) {
            int len = n - i + 1;
            out.println(joinRangeDown(next, next - len + 1));
            next -= len;
        }
    }

    /** Pascal (binomial coefficients), left-aligned. */
    public static void pascalTriangle(PrintStream out, int rows) {
        for (int i = 0; i < rows; i++) {
            StringBuilder sb = new StringBuilder();
            int v = 1;
            for (int j = 0; j <= i; j++) {
                if (j > 0) {
                    sb.append(' ');
                }
                sb.append(v);
                v = v * (i - j) / (j + 1);
            }
            out.println(sb);
        }
    }

    /** Same rows as Pascal, right-aligned to the longest (last) row. */
    public static void rightPascalPattern(PrintStream out, int rows) {
        String[] lines = new String[rows];
        int maxLen = 0;
        for (int i = 0; i < rows; i++) {
            StringBuilder sb = new StringBuilder();
            int v = 1;
            for (int j = 0; j <= i; j++) {
                if (j > 0) {
                    sb.append(' ');
                }
                sb.append(v);
                v = v * (i - j) / (j + 1);
            }
            lines[i] = sb.toString();
            maxLen = Math.max(maxLen, lines[i].length());
        }
        for (int i = 0; i < rows; i++) {
            int pad = maxLen - lines[i].length();
            out.println(spaces(pad) + lines[i]);
        }
    }

    /** {@code n×n} alternating {@code 0}/{@code 1}. */
    public static void alternatingBinaryPattern(PrintStream out, int n) {
        for (int r = 0; r < n; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < n; c++) {
                if (c > 0) {
                    sb.append(' ');
                }
                sb.append((r + c) % 2);
            }
            out.println(sb);
        }
    }

    /** Inverted centered pyramid: row {@code i} shows {@code 1…(n-i+1)}. */
    public static void descendingNumberPyramid(PrintStream out, int n) {
        int maxLen = joinRange(1, n).length();
        for (int i = 1; i <= n; i++) {
            int len = n - i + 1;
            String core = joinRange(1, len);
            int pad = (maxLen - core.length()) / 2;
            out.println(spaces(pad) + core);
        }
    }

    /** Border {@code 1}, interior {@code 0} (for {@code n ≥ 2}). */
    public static void hollowNumberSquare(PrintStream out, int n) {
        for (int r = 0; r < n; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < n; c++) {
                if (c > 0) {
                    sb.append(' ');
                }
                boolean edge = r == 0 || r == n - 1 || c == 0 || c == n - 1;
                sb.append(edge ? 1 : 0);
            }
            out.println(sb);
        }
    }

    /** Hourglass: wide row of consecutive numbers narrowing then widening. */
    public static void numberHourglass(PrintStream out, int n) {
        int num = 1;
        for (int i = n; i >= 1; i--) {
            StringBuilder sb = new StringBuilder();
            int gapPad = (n - i) * 2;
            sb.append(spaces(gapPad));
            for (int j = 0; j < i; j++) {
                if (j > 0) {
                    sb.append(' ');
                }
                sb.append(num++);
            }
            out.println(sb);
        }
        for (int i = 2; i <= n; i++) {
            StringBuilder sb = new StringBuilder();
            int gapPad = (n - i) * 2;
            sb.append(spaces(gapPad));
            for (int j = 0; j < i; j++) {
                if (j > 0) {
                    sb.append(' ');
                }
                sb.append(num++);
            }
            out.println(sb);
        }
    }

    /** Multiplication table {@code i×j} for {@code i,j} in {@code 1…n}. */
    public static void multiplicationTablePattern(PrintStream out, int n) {
        int w = widthOf(n * n);
        for (int r = 1; r <= n; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 1; c <= n; c++) {
                if (c > 1) {
                    sb.append(' ');
                }
                sb.append(padNumber(r * c, w));
            }
            out.println(sb);
        }
    }

    /** {@code 1…n²} in row-major order; odd rows L→R, even rows R→L. */
    public static void snakeNumberPattern(PrintStream out, int n) {
        int w = widthOf(n * n);
        int k = 1;
        for (int r = 0; r < n; r++) {
            StringBuilder sb = new StringBuilder();
            if (r % 2 == 0) {
                for (int c = 0; c < n; c++) {
                    if (c > 0) {
                        sb.append(' ');
                    }
                    sb.append(padNumber(k++, w));
                }
            } else {
                int end = k + n - 1;
                for (int c = 0; c < n; c++) {
                    if (c > 0) {
                        sb.append(' ');
                    }
                    sb.append(padNumber(end - c, w));
                }
                k = end + 1;
            }
            out.println(sb);
        }
    }

    /** Same centered layout as {@link #numberPyramid}. */
    public static void centeredIncreasingNumbers(PrintStream out, int n) {
        numberPyramid(out, n);
    }

    /** Hollow numeric pyramid; interior numbers on each row are blanked, width matches solid pyramid. */
    public static void hollowPyramidNumbers(PrintStream out, int n) {
        String base = joinRange(1, n);
        int maxLen = base.length();
        for (int i = 1; i <= n; i++) {
            String solid = joinRange(1, i);
            String hollow;
            if (i == 1 || i == n) {
                hollow = solid;
            } else {
                String last = String.valueOf(i);
                hollow = "1" + spaces(solid.length() - 1 - last.length()) + last;
            }
            int pad = (maxLen - hollow.length()) / 2;
            out.println(spaces(pad) + hollow);
        }
    }

    private static String joinRange(int from, int to) {
        StringBuilder sb = new StringBuilder();
        for (int j = from; j <= to; j++) {
            if (j > from) {
                sb.append(' ');
            }
            sb.append(j);
        }
        return sb.toString();
    }

    private static String joinRangeDown(int high, int low) {
        StringBuilder sb = new StringBuilder();
        for (int j = high; j >= low; j--) {
            if (j < high) {
                sb.append(' ');
            }
            sb.append(j);
        }
        return sb.toString();
    }

    private static String buildPalindromeLine(int peak) {
        StringBuilder sb = new StringBuilder();
        for (int j = 1; j <= peak; j++) {
            if (j > 1) {
                sb.append(' ');
            }
            sb.append(j);
        }
        for (int j = peak - 1; j >= 1; j--) {
            sb.append(' ').append(j);
        }
        return sb.toString();
    }

    private static int widthOf(int v) {
        return String.valueOf(Math.abs(v)).length();
    }

    private static String padNumber(int v, int w) {
        String s = String.valueOf(v);
        while (s.length() < w) {
            s = " " + s;
        }
        return s;
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
