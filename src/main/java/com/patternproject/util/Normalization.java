package com.patternproject.util;

import java.util.Locale;

/** Normalizes user text for stable matching (case, whitespace). */
public final class Normalization {

    private Normalization() {
    }

    public static String patternKey(String input) {
        if (input == null) {
            return "";
        }
        return input.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");
    }
}
