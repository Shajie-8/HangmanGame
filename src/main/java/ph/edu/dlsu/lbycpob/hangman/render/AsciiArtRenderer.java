package ph.edu.dlsu.lbycpob.hangman.render;

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
}
