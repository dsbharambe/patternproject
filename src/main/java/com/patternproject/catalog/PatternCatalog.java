package com.patternproject.catalog;

import com.patternproject.rendering.NumberShapes;
import com.patternproject.rendering.StarShapes;
import com.patternproject.util.Normalization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Registry of pattern names → definitions. Single place to add or rename patterns.
 */
public final class PatternCatalog {

    private final List<PatternDefinition> ordered;
    private final Map<String, PatternDefinition> byAlias;

    private PatternCatalog(List<PatternDefinition> ordered, Map<String, PatternDefinition> byAlias) {
        this.ordered = Collections.unmodifiableList(new ArrayList<PatternDefinition>(ordered));
        this.byAlias = Collections.unmodifiableMap(new HashMap<String, PatternDefinition>(byAlias));
    }

    public static PatternCatalog standard() {
        List<PatternDefinition> defs = new ArrayList<>();

        defs.add(new PatternDefinition(
                "square",
                "Star (*) shapes:",
                "  square              — filled n×n square",
                (n, in, out, err) -> {
                    StarShapes.square(out, n);
                    return true;
                },
                Arrays.asList("square", "square pattern")));

        defs.add(new PatternDefinition(
                "right_triangle",
                "  right triangle      — left-aligned, * .. n stars",
                (n, in, out, err) -> {
                    StarShapes.rightTriangle(out, n);
                    return true;
                },
                Arrays.asList("right triangle", "right", "triangle")));

        defs.add(new PatternDefinition(
                "left_triangle",
                "  left triangle       — right-aligned, * .. n stars",
                (n, in, out, err) -> {
                    StarShapes.leftTriangle(out, n);
                    return true;
                },
                Arrays.asList("left triangle", "left")));

        defs.add(new PatternDefinition(
                "inverted_right",
                "  inverted right      — left-aligned, n .. *",
                (n, in, out, err) -> {
                    StarShapes.invertedRightTriangle(out, n);
                    return true;
                },
                Arrays.asList("inverted right triangle", "inverted right")));

        defs.add(new PatternDefinition(
                "inverted_triangle",
                "  inverted triangle   — centered upside-down triangle",
                (n, in, out, err) -> {
                    StarShapes.invertedTriangle(out, n);
                    return true;
                },
                Arrays.asList("inverted triangle", "inverted tri")));

        defs.add(new PatternDefinition(
                "pyramid",
                "  pyramid             — centered triangle",
                (n, in, out, err) -> {
                    StarShapes.pyramid(out, n);
                    return true;
                },
                Arrays.asList("pyramid")));

        defs.add(new PatternDefinition(
                "inverted_pyramid",
                "  inverted pyramid    — centered, upside-down",
                (n, in, out, err) -> {
                    StarShapes.invertedPyramid(out, n);
                    return true;
                },
                Arrays.asList("inverted pyramid", "inverse pyramid")));

        defs.add(new PatternDefinition(
                "diamond",
                "  diamond             — solid diamond",
                (n, in, out, err) -> {
                    StarShapes.diamond(out, n);
                    return true;
                },
                Arrays.asList("diamond")));

        defs.add(new PatternDefinition(
                "full_hollow_diamond",
                "  full hollow diamond — hollow diamond outline",
                (n, in, out, err) -> {
                    StarShapes.fullHollowDiamond(out, n);
                    return true;
                },
                Arrays.asList("full hollow diamond", "hollow diamond")));

        defs.add(new PatternDefinition(
                "hollow_square",
                "  hollow square       — square border",
                (n, in, out, err) -> {
                    StarShapes.hollowSquare(out, n);
                    return true;
                },
                Arrays.asList("hollow square", "hollow")));

        defs.add(new PatternDefinition(
                "hollow_rectangle",
                "  hollow rectangle    — needs height (above) + width (next prompt)",
                (n, in, out, err) -> {
                    out.print("Enter width: ");
                    String wLine = in.nextLine();
                    int w;
                    try {
                        w = Integer.parseInt(wLine.trim());
                    } catch (NumberFormatException e) {
                        err.println("Width must be an integer.");
                        return true;
                    }
                    if (w < 1) {
                        err.println("Width must be at least 1.");
                        return true;
                    }
                    StarShapes.hollowRectangle(out, n, w);
                    return true;
                },
                Arrays.asList("hollow rectangle", "hollow rect")));

        defs.add(new PatternDefinition(
                "sandglass",
                "  sandglass           — hourglass",
                (n, in, out, err) -> {
                    StarShapes.sandglass(out, n);
                    return true;
                },
                Arrays.asList("sandglass", "hourglass")));

        defs.add(new PatternDefinition(
                "butterfly",
                "  butterfly           — wing shape, 2n rows",
                (n, in, out, err) -> {
                    StarShapes.butterfly(out, n);
                    return true;
                },
                Arrays.asList("butterfly")));

        defs.add(new PatternDefinition(
                "x_pattern",
                "  x pattern           — diagonals on n×n",
                (n, in, out, err) -> {
                    StarShapes.xPattern(out, n);
                    return true;
                },
                Arrays.asList("x pattern", "x")));

        defs.add(new PatternDefinition(
                "zig_zag",
                "  zig zag             — 3-row zig-zag, width = size",
                (n, in, out, err) -> {
                    StarShapes.zigZag(out, Math.max(4, n));
                    return true;
                },
                Arrays.asList("zig zag", "zigzag", "zig-zag")));

        defs.add(new PatternDefinition(
                "hollow_triangle",
                "  hollow triangle     — hollow centered triangle",
                (n, in, out, err) -> {
                    StarShapes.hollowTriangle(out, n);
                    return true;
                },
                Arrays.asList("hollow triangle")));

        defs.add(new PatternDefinition(
                "christmas_tree",
                "  christmas tree      — stacked tiers + trunk",
                (n, in, out, err) -> {
                    StarShapes.christmasTree(out, n);
                    return true;
                },
                Arrays.asList("christmas tree", "tree")));

        defs.add(new PatternDefinition(
                "pascal_star",
                "  pascal star         — Pascal mod 2 (Sierpinski-style)",
                (n, in, out, err) -> {
                    StarShapes.pascalStar(out, n);
                    return true;
                },
                Arrays.asList("pascal star", "pascal", "pascal pattern")));

        defs.add(new PatternDefinition(
                "arrow",
                "  arrow               — pyramid + stem",
                (n, in, out, err) -> {
                    StarShapes.arrow(out, n);
                    return true;
                },
                Arrays.asList("arrow", "arrow pattern")));

        defs.add(new PatternDefinition(
                "border_cross",
                "  border cross        — box with cross through center",
                (n, in, out, err) -> {
                    StarShapes.borderCross(out, n);
                    return true;
                },
                Arrays.asList("border cross", "cross")));

        defs.add(new PatternDefinition(
                "simple_number_triangle",
                "Number patterns:",
                PatternKind.NUMBER,
                "  simple number triangle   — rows 1, 12…, …, 1…n",
                (n, in, out, err) -> {
                    NumberShapes.simpleNumberTriangle(out, n);
                    return true;
                },
                Arrays.asList(
                        "simple number triangle",
                        "number triangle simple")));

        defs.add(new PatternDefinition(
                "repeated_number_triangle",
                PatternKind.NUMBER,
                "  repeated number triangle — row i is i repeated i times",
                (n, in, out, err) -> {
                    NumberShapes.repeatedNumberTriangle(out, n);
                    return true;
                },
                Arrays.asList("repeated number triangle")));

        defs.add(new PatternDefinition(
                "reverse_number_triangle",
                PatternKind.NUMBER,
                "  reverse number triangle — row i : i … 1",
                (n, in, out, err) -> {
                    NumberShapes.reverseNumberTriangle(out, n);
                    return true;
                },
                Arrays.asList("reverse number triangle")));

        defs.add(new PatternDefinition(
                "floyds_triangle",
                PatternKind.NUMBER,
                "  floyd triangle         — Floyd’s consecutive fill",
                (n, in, out, err) -> {
                    NumberShapes.floydsTriangle(out, n);
                    return true;
                },
                Arrays.asList("floyd triangle", "floyd", "floyd's triangle")));

        defs.add(new PatternDefinition(
                "zero_one_triangle",
                PatternKind.NUMBER,
                "  0-1 triangle           — alternating 0/1 per cell",
                (n, in, out, err) -> {
                    NumberShapes.zeroOneTriangle(out, n);
                    return true;
                },
                Arrays.asList(
                        "0-1 triangle",
                        "zero one triangle",
                        "binary triangle")));

        defs.add(new PatternDefinition(
                "number_pyramid",
                PatternKind.NUMBER,
                "  number pyramid         — centered 1…i rows",
                (n, in, out, err) -> {
                    NumberShapes.numberPyramid(out, n);
                    return true;
                },
                Arrays.asList("number pyramid")));

        defs.add(new PatternDefinition(
                "palindrome_number_pyramid",
                PatternKind.NUMBER,
                "  palindrome number pyramid — 1 2 … i … 2 1",
                (n, in, out, err) -> {
                    NumberShapes.palindromeNumberPyramid(out, n);
                    return true;
                },
                Arrays.asList("palindrome pyramid", "palindrome number pyramid")));

        defs.add(new PatternDefinition(
                "diamond_number",
                PatternKind.NUMBER,
                "  diamond number          — numeric palindrome diamond",
                (n, in, out, err) -> {
                    NumberShapes.diamondNumberPattern(out, n);
                    return true;
                },
                Arrays.asList("diamond number", "diamond number pattern")));

        defs.add(new PatternDefinition(
                "sequential_number_square",
                PatternKind.NUMBER,
                "  sequential number square — n×n grid 1…n²",
                (n, in, out, err) -> {
                    NumberShapes.sequentialNumberSquare(out, n);
                    return true;
                },
                Arrays.asList("sequential number square", "sequential square")));

        defs.add(new PatternDefinition(
                "reverse_sequential_triangle",
                PatternKind.NUMBER,
                "  reverse sequential triangle — n…1 then … packed rows",
                (n, in, out, err) -> {
                    NumberShapes.reverseSequentialTriangle(out, n);
                    return true;
                },
                Arrays.asList("reverse sequential triangle")));

        defs.add(new PatternDefinition(
                "pascal_triangle",
                PatternKind.NUMBER,
                "  pascal triangle        — binomial coefficients",
                (n, in, out, err) -> {
                    NumberShapes.pascalTriangle(out, n);
                    return true;
                },
                Arrays.asList("pascal triangle", "triangle pascal", "numbered pascal")));

        defs.add(new PatternDefinition(
                "right_pascal",
                PatternKind.NUMBER,
                "  right pascal           — Pascal rows right-aligned",
                (n, in, out, err) -> {
                    NumberShapes.rightPascalPattern(out, n);
                    return true;
                },
                Arrays.asList(
                        "right pascal pattern",
                        "right pascal")));

        defs.add(new PatternDefinition(
                "alternating_binary_pattern",
                PatternKind.NUMBER,
                "  alternating binary pattern — n×n 0/1 checker",
                (n, in, out, err) -> {
                    NumberShapes.alternatingBinaryPattern(out, n);
                    return true;
                },
                Arrays.asList("alternating binary pattern")));

        defs.add(new PatternDefinition(
                "descending_number_pyramid",
                PatternKind.NUMBER,
                "  descending number pyramid — upside-down widening rows",
                (n, in, out, err) -> {
                    NumberShapes.descendingNumberPyramid(out, n);
                    return true;
                },
                Arrays.asList("descending pyramid", "descending number pyramid")));

        defs.add(new PatternDefinition(
                "hollow_number_square",
                PatternKind.NUMBER,
                "  hollow number square   — border 1, interior 0",
                (n, in, out, err) -> {
                    NumberShapes.hollowNumberSquare(out, n);
                    return true;
                },
                Arrays.asList("hollow number square")));

        defs.add(new PatternDefinition(
                "number_hourglass",
                PatternKind.NUMBER,
                "  number hourglass     — widen then narrow, consecutive ints",
                (n, in, out, err) -> {
                    NumberShapes.numberHourglass(out, n);
                    return true;
                },
                Arrays.asList("number hourglass", "number sandglass")));

        defs.add(new PatternDefinition(
                "multiplication_table",
                PatternKind.NUMBER,
                "  multiplication table — i×j for 1…n",
                (n, in, out, err) -> {
                    NumberShapes.multiplicationTablePattern(out, n);
                    return true;
                },
                Arrays.asList("multiplication table pattern", "multiplication table")));

        defs.add(new PatternDefinition(
                "snake_number_pattern",
                PatternKind.NUMBER,
                "  snake number pattern — row-major zig-zag rows",
                (n, in, out, err) -> {
                    NumberShapes.snakeNumberPattern(out, n);
                    return true;
                },
                Arrays.asList("snake number pattern", "snake pattern")));

        defs.add(new PatternDefinition(
                "centered_increasing_numbers",
                PatternKind.NUMBER,
                "  centered increasing numbers — centered 1…i rows",
                (n, in, out, err) -> {
                    NumberShapes.centeredIncreasingNumbers(out, n);
                    return true;
                },
                Arrays.asList("centered increasing numbers", "centered increasing")));

        defs.add(new PatternDefinition(
                "hollow_pyramid_numbers",
                PatternKind.NUMBER,
                "  hollow pyramid numbers — outline only",
                (n, in, out, err) -> {
                    NumberShapes.hollowPyramidNumbers(out, n);
                    return true;
                },
                Arrays.asList(
                        "hollow pyramid numbers",
                        "hollow number pyramid")));

        Map<String, PatternDefinition> index = new HashMap<>();
        for (PatternDefinition def : defs) {
            for (String alias : def.aliases()) {
                index.put(Normalization.patternKey(alias), def);
            }
        }
        return new PatternCatalog(defs, index);
    }

    public List<PatternDefinition> definitionsForKind(PatternKind kind) {
        List<PatternDefinition> list = new ArrayList<PatternDefinition>();
        for (PatternDefinition def : ordered) {
            if (def.kind() == kind) {
                list.add(def);
            }
        }
        return Collections.unmodifiableList(list);
    }

    public Optional<PatternDefinition> find(String normalizedKey) {
        return Optional.ofNullable(byAlias.get(normalizedKey));
    }

    public List<PatternDefinition> definitionsInDisplayOrder() {
        return ordered;
    }
}
