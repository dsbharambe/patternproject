package com.patternproject.cli;

import com.patternproject.catalog.PatternCatalog;
import com.patternproject.catalog.PatternDefinition;
import com.patternproject.util.Normalization;

import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

/**
 * Interactive CLI: prints help, reads pattern + size, dispatches via {@link PatternCatalog}, repeats until the user quits.
 */
public final class PatternConsoleApp {

    private final Scanner in;
    private final PrintStream out;
    private final PrintStream err;
    private final PatternCatalog catalog;

    public PatternConsoleApp(Scanner in, PrintStream out, PrintStream err, PatternCatalog catalog) {
        this.in = in;
        this.out = out;
        this.err = err;
        this.catalog = catalog;
    }

    public void run() {
        printHelp();

        boolean continueSession = true;
        while (continueSession) {
            out.print("Enter pattern name: ");
            String rawName = in.nextLine();
            String key = Normalization.patternKey(rawName);

            out.print("Enter size / rows (height for hollow rectangle): ");
            String rawRows = in.nextLine();
            int n;
            try {
                n = Integer.parseInt(rawRows.trim());
            } catch (NumberFormatException e) {
                err.println("That is not a valid integer.");
                continueSession = askContinue();
                continue;
            }
            if (n < 1) {
                err.println("Value must be at least 1.");
                continueSession = askContinue();
                continue;
            }

            Optional<PatternDefinition> match = catalog.find(key);
            if (match.isPresent()) {
                match.get().drawer().draw(n, in, out, err);
            } else {
                err.println("Unknown pattern: \"" + rawName.trim() + "\"");
                err.println("Use one of the names listed above.");
            }

            continueSession = askContinue();
        }
        out.println("Goodbye.");
    }

    private void printHelp() {
        out.println("Star patterns (names are case-insensitive):");
        for (PatternDefinition def : catalog.definitionsInDisplayOrder()) {
            out.println(def.helpLine());
        }
        out.println();
    }

    private boolean askContinue() {
        while (true) {
            out.print("Do you want any other pattern? (yes/no): ");
            String answer = Normalization.patternKey(in.nextLine());
            if (answer.isEmpty()) {
                out.println("Please type yes or no.");
                continue;
            }
            if (answer.equals("y") || answer.equals("yes")) {
                return true;
            }
            if (answer.equals("n") || answer.equals("no")) {
                return false;
            }
            out.println("Please type yes or no.");
        }
    }
}
