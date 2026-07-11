package ph.edu.dlsu.lbycpob.hangman.repository;

import java.util.Objects;
import java.util.Random;

public final class ClasspathWordRepository implements WordRepository {
    private final String resourceBasePath;
    private final Random random;

    // UNDERSTAND: Instantiates a classpath word file searcher.
    // DECISION: Strips trailing slashes to prevent double-slash compilation paths.
    public ClasspathWordRepository(String resourceBasePath, Random random) {
        Objects.requireNonNull(resourceBasePath, "resourceBasePath must not be null");
        this.random = Objects.requireNonNull(random, "random must not be null");
        if (resourceBasePath.isBlank()) {
            throw new IllegalArgumentException("resourceBasePath must not be blank");
        }
        this.resourceBasePath = resourceBasePath.endsWith("/")
                ? resourceBasePath.substring(0, resourceBasePath.length() - 1)
                : resourceBasePath;
    }