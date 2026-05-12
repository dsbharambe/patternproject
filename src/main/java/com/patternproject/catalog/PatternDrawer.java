package com.patternproject.catalog;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Renders one pattern type. May read extra input from {@code in} (e.g. rectangle width).
 *
 * @return {@code true} if rendering ran (including after reporting invalid secondary input);
 *         {@code false} only if you reserve it for future use — callers treat unknown names separately
 */
@FunctionalInterface
public interface PatternDrawer {
    boolean draw(int primarySize, Scanner in, PrintStream out, PrintStream err);
}
