package ph.edu.dlsu.lbycpob.hangman.repository;

import java.util.Random;

public final class ClasspathWordRepository implements WordRepository {
    private final String resourceBasePath;
    private final Random random;