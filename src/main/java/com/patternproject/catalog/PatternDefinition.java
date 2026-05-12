package com.patternproject.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * One logical pattern: help text, how to draw it, and all user-facing aliases.
 */
public final class PatternDefinition {

    private final String id;
    private final String helpLine;
    private final PatternDrawer drawer;
    private final List<String> aliases;

    public PatternDefinition(String id, String helpLine, PatternDrawer drawer, List<String> aliases) {
        this.id = id;
        this.helpLine = helpLine;
        this.drawer = drawer;
        this.aliases = Collections.unmodifiableList(new ArrayList<String>(aliases));
    }

    public String id() {
        return id;
    }

    public String helpLine() {
        return helpLine;
    }

    public PatternDrawer drawer() {
        return drawer;
    }

    public List<String> aliases() {
        return aliases;
    }
}
