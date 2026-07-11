package ph.edu.dlsu.lbycpob.hangman.repository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Random;

public final class FileWordRepository implements WordRepository {
    private final Random random;

    // UNDERSTAND: Instantiates a physical disk file searcher.
    public FileWordRepository(Random random) {
        this.random = Objects.requireNonNull(random, "random must not be null");
    }

    @Override
    public String getRandomWord(String filename) throws IOException {
        Objects.requireNonNull(filename, "filename must not be null");
        if (filename.isBlank()) {
            throw new IllegalArgumentException("filename must not be blank");
        }
        Path path = Path.of(filename);



