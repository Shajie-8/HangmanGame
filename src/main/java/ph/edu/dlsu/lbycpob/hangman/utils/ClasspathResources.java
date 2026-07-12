package ph.edu.dlsu.lbycpob.hangman.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ClasspathResources {
    private ClasspathResources() {
        // Utility class - never instantiated.
    }

    public static List<String> readLines(String resourcePath) throws IOException {
        Objects.requireNonNull(resourcePath, "resourcePath must not be null");

        // UNDERSTAND: Input/Output operation retrieving a stream mapped inside the bundled JAR resource tree.
        // DECISION: Try-with-resources handles cleanup of both the InputStream and BufferedReader on complete.
        try (InputStream input = ClasspathResources.class.getResourceAsStream(resourcePath)) {
            if (input == null) {
                throw new IOException("Resource not found on the classpath: " + resourcePath);
            }
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
                String line;
                // UNDERSTAND: Loop terminates when the line buffer reaches the end of the resource.
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
            return lines;
        }
    }
}