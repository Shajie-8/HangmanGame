package ph.edu.dlsu.lbycpob.hangman.render;

import ph.edu.dlsu.lbycpob.hangman.utils.ClasspathResources;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AsciiArtRenderer implements HangmanRenderer {
    // UNDERSTAND: Constants to define boundary restrictions on guesses.
    private static final int MIN_GUESSES_REMAINING = 0;
    private static final int MAX_GUESSES_REMAINING = 8;
    private final String resourceBasePath;

    // UNDERSTAND: Configures the base directory path of the visual assets.
    // DECISION: Sanitizes trailing slashes so the dynamically built paths match expectations.
    public AsciiArtRenderer(String resourceBasePath) {
        Objects.requireNonNull(resourceBasePath, "resourceBasePath must not be null");
        if (resourceBasePath.isBlank()) {
            throw new IllegalArgumentException("resourceBasePath must not be blank");
        }
        this.resourceBasePath = resourceBasePath.endsWith("/")
                ? resourceBasePath.substring(0, resourceBasePath.length() - 1)
                : resourceBasePath;
    }

    @Override
    public void render(int guessesRemaining) throws IOException {
        // UNDERSTAND: Branch logic checks if the argument matches the legal 0-8 game states.
        // DECISION: Uses an early exit check with an IllegalArgumentException to enforce valid state assumptions.
        if (guessesRemaining < MIN_GUESSES_REMAINING || guessesRemaining > MAX_GUESSES_REMAINING) {
            throw new IllegalArgumentException("guessesRemaining must be between "
                    + MIN_GUESSES_REMAINING + " and " + MAX_GUESSES_REMAINING + ", got " + guessesRemaining);
        }
        String resourcePath = resourceBasePath + "/display" + guessesRemaining + ".txt";
        List<String> lines = ClasspathResources.readLines(resourcePath);

        // UNDERSTAND: Loop terminates when all lines in the list are traversed.
        // DECISION: Enhanced for-loop is chosen because index ordering is managed by the list iterator.
        for (String line : lines) {
            // UNDERSTAND: Input/Output operation writing the ASCII lines to the system console.
            System.out.println(line);
        }
    }
}
