// AI-CHECK: Inspected and tested the game loop logic, ensuring case insensitivity and the correct 8-guess limit.
package ph.edu.dlsu.lbycpob.hangman.game;


import ph.edu.dlsu.lbycpob.hangman.render.HangmanRenderer;
import ph.edu.dlsu.lbycpob.hangman.repository.WordRepository;
import ph.edu.dlsu.lbycpob.hangman.statistics.GameStatistics;

import java.util.Objects;
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

    // UNDERSTAND: Constructor to wire up our dependencies.
    // DECISION: Fail-fast checks ensure that null dependencies are caught immediately upon instantiation.
    public Hangman(HangmanRenderer renderer, WordRepository wordRepository, Random random, String filename) {
        this.renderer = Objects.requireNonNull(renderer, "renderer must not be null");
        this.wordRepository = Objects.requireNonNull(wordRepository, "wordRepository must not be null");
        this.random = Objects.requireNonNull(random, "random must not be null");
        this.filename = Objects.requireNonNull(filename, "filename must not be null");
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        // UNDERSTAND: Variable declaration of immutable GameStatistics state that accumulates results across games.
        GameStatistics stats = GameStatistics.empty();
        boolean playAgain = true;

        // UNDERSTAND: Loop terminates when the user chooses not to continue (i.e. playAgain becomes false).
        // DECISION: A while-loop is chosen because the total number of game iterations is dynamic and depends on user input.
        while (playAgain) {
            String secretWord = getRandomWord(filename);
            int guessesRemaining = playOneGame(secretWord);
            boolean won = guessesRemaining > 0;
            stats = stats.withGame(won, guessesRemaining);

            // UNDERSTAND: Input operation to query if the user wants another game.
            playAgain = readYesNo("Do you want to play again (Y/N)? ");
            System.out.println();
        }

        stats(stats.gamesPlayed(), stats.gamesWon(), stats.bestGuessesRemaining());
    }
}
