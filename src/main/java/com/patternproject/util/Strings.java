package com.patternproject.util;

/** Java 8 replacement for {@code String#repeat} (Java 11+). */
public final class Strings {

    private Strings() {
    }

    public static String repeat(String unit, int count) {
        if (count <= 0) {
            return "";
        }
        if (count == 1) {
            return unit;
        }
        int len = unit.length();
        StringBuilder sb = new StringBuilder(len * count);
        for (int i = 0; i < count; i++) {
            sb.append(unit);
        }
        return sb.toString();
    }
}
