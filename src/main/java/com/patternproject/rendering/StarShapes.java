package com.patternproject.rendering;

import com.patternproject.util.Strings;

import java.io.PrintStream;

/**
 * Renders star shapes to a {@link PrintStream}. No user I/O — suitable for reuse and tests.
 */
public final class StarShapes {

    private StarShapes() {
    }

    public static void square(PrintStream out, int size) {
        for (int r = 0; r < size; r++) {
            out.println(Strings.repeat("*",size));
        }
    }

    public static void rightTriangle(PrintStream out, int rows) {
        for (int i = 1; i <= rows; i++) {
            out.println(Strings.repeat("*",i));
        }
    }

    public static void leftTriangle(PrintStream out, int rows) {
        for (int i = 1; i <= rows; i++) {
            int spaces = rows - i;
            out.println(Strings.repeat(" ",spaces) + Strings.repeat("*",i));
        }
    }

    public static void invertedRightTriangle(PrintStream out, int rows) {
        for (int i = rows; i >= 1; i--) {
            out.println(Strings.repeat("*",i));
        }
    }

    public static void pyramid(PrintStream out, int rows) {
        for (int i = 1; i <= rows; i++) {
            int stars = 2 * i - 1;
            int spaces = rows - i;
            out.println(Strings.repeat(" ",spaces) + Strings.repeat("*",stars));
        }
    }

    public static void invertedTriangle(PrintStream out, int rows) {
        invertedPyramid(out, rows);
    }

    public static void invertedPyramid(PrintStream out, int rows) {
        for (int i = rows; i >= 1; i--) {
            int stars = 2 * i - 1;
            int spaces = rows - i;
            out.println(Strings.repeat(" ",spaces) + Strings.repeat("*",stars));
        }
    }

    public static void diamond(PrintStream out, int halfRows) {
        pyramid(out, halfRows);
        for (int i = halfRows - 1; i >= 1; i--) {
            int stars = 2 * i - 1;
            int spaces = halfRows - i;
            out.println(Strings.repeat(" ",spaces) + Strings.repeat("*",stars));
        }
    }

    public static void fullHollowDiamond(PrintStream out, int halfRows) {
        hollowDiamond(out, halfRows);
    }

    public static void hollowDiamond(PrintStream out, int halfRows) {
        for (int i = 1; i <= halfRows; i++) {
            printHollowDiamondLine(out, halfRows, i);
        }
        for (int i = halfRows - 1; i >= 1; i--) {
            printHollowDiamondLine(out, halfRows, i);
        }
    }

    private static void printHollowDiamondLine(PrintStream out, int halfRows, int i) {
        int outer = halfRows - i;
        if (i == 1) {
            out.println(Strings.repeat(" ",outer) + "*");
        } else {
            int inner = 2 * i - 3;
            out.println(Strings.repeat(" ",outer) + "*" + Strings.repeat(" ",inner) + "*");
        }
    }

    public static void hollowSquare(PrintStream out, int size) {
        hollowRectangle(out, size, size);
    }

    public static void hollowRectangle(PrintStream out, int height, int width) {
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                boolean edge = r == 0 || r == height - 1 || c == 0 || c == width - 1;
                out.print(edge ? '*' : ' ');
            }
            out.println();
        }
    }

    public static void sandglass(PrintStream out, int rows) {
        invertedPyramid(out, rows);
        for (int i = 2; i <= rows; i++) {
            int stars = 2 * i - 1;
            int spaces = rows - i;
            out.println(Strings.repeat(" ",spaces) + Strings.repeat("*",stars));
        }
    }

    public static void butterfly(PrintStream out, int n) {
        for (int i = 1; i <= n; i++) {
            int wing = i;
            int gap = 2 * (n - i);
            out.println(Strings.repeat("*",wing) + Strings.repeat(" ",gap) + Strings.repeat("*",wing));
        }
        for (int i = n; i >= 1; i--) {
            int wing = i;
            int gap = 2 * (n - i);
            out.println(Strings.repeat("*",wing) + Strings.repeat(" ",gap) + Strings.repeat("*",wing));
        }
    }

    public static void xPattern(PrintStream out, int n) {
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                boolean diag = r == c || r + c == n - 1;
                out.print(diag ? '*' : ' ');
            }
            out.println();
        }
    }

    public static void zigZag(PrintStream out, int width) {
        if (width < 1) {
            return;
        }
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < width; col++) {
                int mod = (col + row) % 4;
                boolean topOrBot = (row == 0 || row == 2) && mod == 0;
                boolean mid = row == 1 && col % 4 == 2;
                out.print(topOrBot || mid ? '*' : ' ');
            }
            out.println();
        }
    }

    public static void hollowTriangle(PrintStream out, int rows) {
        for (int i = 1; i <= rows; i++) {
            int outer = rows - i;
            if (i == 1) {
                out.println(Strings.repeat(" ",outer) + "*");
            } else if (i == rows) {
                out.println(Strings.repeat(" ",outer) + Strings.repeat("*",2 * i - 1));
            } else {
                int inner = 2 * i - 3;
                out.println(Strings.repeat(" ",outer) + "*" + Strings.repeat(" ",inner) + "*");
            }
        }
    }

    public static void christmasTree(PrintStream out, int tiers) {
        if (tiers < 1) {
            return;
        }
        int maxStars = 2 * (3 * tiers - 1) + 1;
        for (int t = 0; t < tiers; t++) {
            for (int r = 0; r < 3; r++) {
                int stars = 2 * (3 * t + r) + 1;
                int pad = (maxStars - stars) / 2;
                out.println(Strings.repeat(" ",pad) + Strings.repeat("*",stars));
            }
        }
        int trunkH = Math.max(1, tiers / 2);
        int trunkW = tiers >= 2 ? 3 : 1;
        int trunkPad = (maxStars - trunkW) / 2;
        for (int i = 0; i < trunkH; i++) {
            out.println(Strings.repeat(" ",trunkPad) + Strings.repeat("*",trunkW));
        }
    }

    public static void pascalStar(PrintStream out, int rows) {
        for (int i = 0; i < rows; i++) {
            int c = 1;
            StringBuilder line = new StringBuilder();
            for (int k = 0; k <= i; k++) {
                line.append((c % 2 == 1) ? "* " : "  ");
                c = c * (i - k) / (k + 1);
            }
            int pad = rows - i - 1;
            out.println(Strings.repeat(" ",pad) + line);
        }
    }

    public static void arrow(PrintStream out, int size) {
        if (size < 2) {
            pyramid(out, Math.max(1, size));
            return;
        }
        int head = Math.max(2, size / 2 + 1);
        pyramid(out, head);
        int stemRows = Math.max(1, size - head);
        int stemPad = head - 1;
        for (int i = 0; i < stemRows; i++) {
            out.println(Strings.repeat(" ",stemPad) + "*");
        }
    }

    public static void borderCross(PrintStream out, int n) {
        if (n < 1) {
            return;
        }
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                boolean border = r == 0 || r == n - 1 || c == 0 || c == n - 1;
                boolean mid = n > 2 && (r == n / 2 || c == n / 2);
                out.print(border || mid ? '*' : ' ');
            }
            out.println();
        }
    }
}
