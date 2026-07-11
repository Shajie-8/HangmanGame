package ph.edu.dlsu.lbycpob.hangman.repository;

import java.util.Objects;
import java.util.Random;

public final class FileWordRepository implements WordRepository {
    private final Random random;

    // UNDERSTAND: Instantiates a physical disk file searcher.
    public FileWordRepository(Random random) {
        this.random = Objects.requireNonNull(random, "random must not be null");
    }




