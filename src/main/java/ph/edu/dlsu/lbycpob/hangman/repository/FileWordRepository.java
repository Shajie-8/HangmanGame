package ph.edu.dlsu.lbycpob.hangman.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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

        // UNDERSTAND: Branch logic checks file existence and state before starting high-overhead I/O.
        // DECISION: Fail-fast checks are executed using independent if blocks for granular error messages.
        if (!Files.exists(path)) {
            throw new IOException("Word list file does not exist: " + filename);
        }
        if (!Files.isRegularFile(path) || !Files.isReadable(path)) {
            throw new IOException("Word list file is not a readable file: " + filename);
        }
        List<String> words = new ArrayList<>();

        // UNDERSTAND: Input/Output operation utilizing buffered character reading over a UTF-8 stream.
        // DECISION: Try-with-resources guarantees file resources are automatically closed when leaving block.
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            // UNDERSTAND: Loop terminates when readLine returns null, signifying the end of the file.
            // DECISION: A while-loop is chosen because the number of lines is dynamic and unknown beforehand.
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    words.add(trimmed.toUpperCase());
                }
            }
        }
        if (words.isEmpty()) {
            throw new IOException("Word list file contains no words: " + filename);
        }
        return words.get(random.nextInt(words.size()));
    }
}



