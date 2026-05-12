# Pattern Project

A small Java console application that prints **star (`*`) patterns** from a named catalog. It is structured for clarity: rendering is separate from the interactive CLI, and pattern names are registered in one place.

## Features

- **Interactive session** — enter a pattern name and size; after each result, choose whether to try another pattern.
- **Many built-in shapes** — triangles, pyramids, diamonds, butterfly, sandglass, Pascal / Sierpinski-style stars, and more.
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

1. Start the app; it prints a list of supported pattern names (case-insensitive).
2. Enter a **pattern name** (e.g. `pyramid`, `butterfly`, `hollow rectangle`).
3. Enter **size / rows** (for `hollow rectangle`, this is **height**; you will be prompted for **width** next).
4. When asked *Do you want any other pattern?*, answer `yes` / `y` or `no` / `n`.

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

## Architecture

| Package | Responsibility |
|---------|----------------|
| `com.patternproject` | Application entry: `PatternApplication` wires dependencies. |
| `com.patternproject.cli` | `PatternConsoleApp` — prompts, validation loop, help output. |
| `com.patternproject.catalog` | `PatternCatalog`, `PatternDefinition`, `PatternDrawer` — name → behavior. |
| `com.patternproject.rendering` | `StarShapes` — pure drawing to a `PrintStream`. |
| `com.patternproject.util` | `Normalization`, `Strings` — input normalization and Java 8–safe string repeat. |

**Flow:** `PatternApplication` builds a `PatternCatalog.standard()`, then runs `PatternConsoleApp`. The console resolves the normalized name, invokes the matching `PatternDrawer`, which calls `StarShapes` with `System.out` / `System.err` as appropriate.

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
  util/Normalization.java
  util/Strings.java
```

## License

No license file is included in this repository; add one if you plan to distribute or open-source the project.
