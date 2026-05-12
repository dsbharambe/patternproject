package com.patternproject;

import com.patternproject.catalog.PatternCatalog;
import com.patternproject.cli.PatternConsoleApp;

import java.util.Scanner;

/** Application entry point — wires the catalog and console session. */
public final class PatternApplication {

    private PatternApplication() {
    }

    public static void main(String[] args) {
        PatternCatalog catalog = PatternCatalog.standard();
        try (Scanner in = new Scanner(System.in)) {
            new PatternConsoleApp(in, System.out, System.err, catalog).run();
        }
    }
}
