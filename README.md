# Pattern Project

A small Java console application that prints **star (`*`)** and **numeric** patterns from a named catalog. Rendering is separate from the interactive CLI; names and aliases are registered in `PatternCatalog`.

## Features

- **Interactive session** — enter a pattern name and size; after each result, choose whether to try another pattern.
- **Star and number shapes** — triangles, pyramids, diamonds, Pascal / Sierpinski stars, Floyd’s triangle, multiplication table, snake fill, and more.
- **Test-friendly rendering** — shape code writes to a `PrintStream` (typically `System.out`), not hard-coded global output.
- **Single registry** — aliases and help text live alongside each pattern in `PatternCatalog`.

## Requirements

- **Java 8 or newer** (standard library only; `String.repeat` from Java 11 is replaced by `com.patternproject.util.Strings.repeat`).

## Quick start

From the project root, compile every source file and run:

**Windows (PowerShell)**

```powershell
New-Item -ItemType Directory -Force -Path out | Out-Null
javac -source 8 -target 8 -d out (Get-ChildItem -Path src\main\java -Recurse -Filter *.java).FullName
java -cp out com.patternproject.PatternApplication
```

**macOS / Linux**

```bash
mkdir -p out
find src/main/java -name "*.java" -print0 | xargs -0 javac -source 8 -target 8 -d out
java -cp out com.patternproject.PatternApplication
```

**IntelliJ IDEA:** mark `src/main/java` as a sources root, then run `com.patternproject.PatternApplication`.

The class `StarPatternDemo` still exposes a `main` method and delegates to `PatternApplication` for older run configurations.

## Usage

1. Start the app; it explains the flow.
2. Choose **kind**: `1` / `star` / `*` for star patterns, or `2` / `number` for number patterns.
3. Read the **filtered list** for that kind only.
4. Enter a **pattern name** (e.g. `butterfly`, `floyd triangle`).
5. Enter **size / rows** (for `hollow rectangle` under stars, this is **height**; width is prompted next).
6. When asked *Do you want any other pattern?*, answer `yes` / `y` or `no` / `n` — you will choose the kind again for the next pattern.

## Supported patterns (names are aliases)

| Concept | Example names |
|--------|----------------|
| Filled square | `square`, `square pattern` |
| Right-aligned growing triangle | `right triangle`, `right`, `triangle` |
| Left-aligned growing triangle | `left triangle`, `left` |
| Left-aligned shrinking triangle | `inverted right`, `inverted right triangle` |
| Centered upside-down triangle | `inverted triangle`, `inverted tri` |
| Centered pyramid | `pyramid` |
| Centered inverted pyramid | `inverted pyramid`, `inverse pyramid` |
| Solid diamond | `diamond` |
| Hollow diamond outline | `full hollow diamond`, `hollow diamond` |
| Hollow square | `hollow square`, `hollow` |
| Hollow rectangle | `hollow rectangle`, `hollow rect` |
| Sandglass | `sandglass`, `hourglass` |
| Butterfly | `butterfly` |
| X on grid | `x pattern`, `x` |
| Zig-zag (3 rows) | `zig zag`, `zigzag`, `zig-zag` |
| Hollow triangle | `hollow triangle` |
| Christmas tree | `christmas tree`, `tree` |
| Pascal mod 2 (stars) | `pascal star`, `pascal`, `pascal pattern` |
| Arrow | `arrow`, `arrow pattern` |
| Box with cross | `border cross`, `cross` |

### Number patterns

| Concept | Example names |
|--------|----------------|
| Simple number triangle | `simple number triangle` |
| Repeated number triangle | `repeated number triangle` |
| Reverse number triangle | `reverse number triangle` |
| Floyd’s triangle | `floyd triangle`, `floyd`, `floyd's triangle` |
| 0–1 triangle | `0-1 triangle`, `zero one triangle`, `binary triangle` |
| Number pyramid | `number pyramid` |
| Palindrome pyramid | `palindrome pyramid`, `palindrome number pyramid` |
| Diamond (numbers) | `diamond number`, `diamond number pattern` |
| Sequential square | `sequential number square`, `sequential square` |
| Reverse sequential triangle | `reverse sequential triangle` |
| Pascal triangle (values) | `pascal triangle`, `triangle pascal`, `numbered pascal` |
| Right Pascal | `right pascal pattern`, `right pascal` |
| Alternating binary grid | `alternating binary pattern` |
| Descending number pyramid | `descending pyramid`, `descending number pyramid` |
| Hollow number square | `hollow number square` |
| Number hourglass | `number hourglass`, `number sandglass` |
| Multiplication table | `multiplication table pattern`, `multiplication table` |
| Snake pattern | `snake number pattern`, `snake pattern` |
| Centered increasing | `centered increasing numbers`, `centered increasing` |
| Hollow pyramid (numbers) | `hollow pyramid numbers`, `hollow number pyramid` |

## Architecture

| Package | Responsibility |
|---------|----------------|
| `com.patternproject` | Application entry: `PatternApplication` wires dependencies. |
| `com.patternproject.cli` | `PatternConsoleApp` — prompts, validation loop, help output. |
| `com.patternproject.catalog` | `PatternCatalog`, `PatternDefinition`, `PatternDrawer` — name → behavior. |
| `com.patternproject.rendering` | `StarShapes`, `NumberShapes` — pure drawing to a `PrintStream`. |
| `com.patternproject.util` | `Normalization`, `Strings` — input normalization and Java 8–safe string repeat. |

**Flow:** `PatternApplication` builds a `PatternCatalog.standard()`, then runs `PatternConsoleApp`. The console resolves the normalized name, invokes the matching `PatternDrawer`, which calls `StarShapes` or `NumberShapes` with `System.out` / `System.err` as appropriate.

## Project layout

```text
src/main/java/com/patternproject/
  PatternApplication.java      # main(String[])
  StarPatternDemo.java         # deprecated delegate to PatternApplication
  cli/PatternConsoleApp.java
  catalog/
    PatternCatalog.java
    PatternDefinition.java
    PatternDrawer.java
  rendering/StarShapes.java
  rendering/NumberShapes.java
  util/Normalization.java
  util/Strings.java
```

## License

No license file is included in this repository; add one if you plan to distribute or open-source the project.
