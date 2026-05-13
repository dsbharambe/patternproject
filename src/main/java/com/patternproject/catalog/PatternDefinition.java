package com.patternproject.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * One logical pattern: kind (star, number, or alphabet), help text, behavior, and aliases.
 */
public final class PatternDefinition {

    private final String id;
    private final String sectionTitle;
    private final PatternKind kind;
    private final String helpLine;
    private final PatternDrawer drawer;
    private final List<String> aliases;

    public PatternDefinition(String id, String helpLine, PatternDrawer drawer, List<String> aliases) {
        this(id, null, PatternKind.STAR, helpLine, drawer, aliases);
    }

    /** Star (or default-kind) pattern with an optional section heading printed in full help. */
    public PatternDefinition(String id, String sectionTitle, String helpLine, PatternDrawer drawer,
            List<String> aliases) {
        this(id, sectionTitle, PatternKind.STAR, helpLine, drawer, aliases);
    }

    /** Pattern of a given kind without an extra section line (e.g. most number patterns). */
    public PatternDefinition(String id, PatternKind kind, String helpLine, PatternDrawer drawer,
            List<String> aliases) {
        this(id, null, kind, helpLine, drawer, aliases);
    }

    /** Pattern with section heading and explicit kind (e.g. first row of number patterns). */
    public PatternDefinition(String id, String sectionTitle, PatternKind kind, String helpLine,
            PatternDrawer drawer, List<String> aliases) {
        this.id = id;
        this.sectionTitle = sectionTitle;
        this.kind = kind;
        this.helpLine = helpLine;
        this.drawer = drawer;
        this.aliases = Collections.unmodifiableList(new ArrayList<String>(aliases));
    }

    public String sectionTitle() {
        return sectionTitle;
    }

    public PatternKind kind() {
        return kind;
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
