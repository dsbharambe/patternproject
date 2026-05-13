package com.patternproject.cli;

import com.patternproject.catalog.PatternCatalog;
import com.patternproject.catalog.PatternDefinition;
import com.patternproject.catalog.PatternKind;
import com.patternproject.util.Normalization;

import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

/**
 * Interactive CLI: choose star vs number, then pattern name and size; dispatches via {@link PatternCatalog}.
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
        out.println("Pattern printer — choose star (*) or number patterns, then a name and size.\n");

        boolean continueSession = true;
        while (continueSession) {
            PatternKind kind = askKind();

            printHelpForKind(kind);

            out.print("Enter pattern name: ");
            String rawName = in.nextLine();
            String key = Normalization.patternKey(rawName);

            String sizePrompt = kind == PatternKind.STAR
                    ? "Enter size / rows (height for hollow rectangle): "
                    : "Enter size / rows (or n for n×n grids): ";
            out.print(sizePrompt);
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
                PatternDefinition def = match.get();
                if (def.kind() != kind) {
                    err.println("That name belongs to " + labelForKind(def.kind())
                            + " patterns. You chose " + labelForKind(kind) + " — try again or pick another name.");
                } else {
                    def.drawer().draw(n, in, out, err);
                }
            } else {
                err.println("Unknown pattern: \"" + rawName.trim() + "\" for " + labelForKind(kind) + ".");
                err.println("See the list printed above.");
            }

            continueSession = askContinue();
        }
        out.println("Goodbye.");
    }

    private PatternKind askKind() {
        while (true) {
            out.println("Which kind of pattern do you want?");
            out.println("  1 — Star (*) patterns");
            out.println("  2 — Number patterns");
            out.print("Enter 1 or 2 (or star / number): ");
            String raw = Normalization.patternKey(in.nextLine());
            if (raw.equals("1") || raw.equals("star") || raw.equals("*")) {
                return PatternKind.STAR;
            }
            if (raw.equals("2") || raw.equals("number") || raw.equals("num") || raw.equals("numeric")) {
                return PatternKind.NUMBER;
            }
            err.println("Please enter 1 or 2, or type star / number.");
        }
    }

    private void printHelpForKind(PatternKind kind) {
        out.println();
        if (kind == PatternKind.STAR) {
            out.println("Star (*) patterns — names you can enter (case-insensitive):");
        } else {
            out.println("Number patterns — names you can enter (case-insensitive):");
        }
        for (PatternDefinition def : catalog.definitionsForKind(kind)) {
            if (def.sectionTitle() != null) {
                out.println();
                out.println(def.sectionTitle());
            }
            out.println(def.helpLine());
        }
        out.println();
    }

    private static String labelForKind(PatternKind kind) {
        return kind == PatternKind.STAR ? "star (*)" : "number";
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
