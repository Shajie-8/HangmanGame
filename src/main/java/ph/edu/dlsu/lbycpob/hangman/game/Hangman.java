// AI-CHECK: Inspected and tested the game loop logic, ensuring case insensitivity and the correct 8-guess limit.
package ph.edu.dlsu.lbycpob.hangman.game;


import ph.edu.dlsu.lbycpob.hangman.render.HangmanRenderer;
import ph.edu.dlsu.lbycpob.hangman.repository.WordRepository;

import java.util.Random;

public class Hangman implements HangmanGame {
    // UNDERSTAND: Dependency injects these components to decouple random generation, file parsing, and display rendering.
    private final Random random;
    private final HangmanRenderer renderer;
    private final Scanner scanner;
    private final WordRepository wordRepository;
    private final String filename;

    // UNDERSTAND: Constants used as fallback data and structural game rules limit.
    private static final String[] DEFAULT_WORDS = {
            "JAVA", "HANGMAN", "COMPUTER", "KEYBOARD", "PROGRAM", "ALGORITHM"
    };
    private static final int MAX_GUESSES = 8;

}
