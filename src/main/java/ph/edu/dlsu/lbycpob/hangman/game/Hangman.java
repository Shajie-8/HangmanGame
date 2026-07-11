// AI-CHECK: Inspected and tested the game loop logic, ensuring case insensitivity and the correct 8-guess limit.
package ph.edu.dlsu.lbycpob.hangman.game;


import ph.edu.dlsu.lbycpob.hangman.render.HangmanRenderer;
import ph.edu.dlsu.lbycpob.hangman.repository.WordRepository;
import ph.edu.dlsu.lbycpob.hangman.statistics.GameStatistics;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

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

    @Override
    public void intro() {
        // UNDERSTAND: Input/Output operation sending the welcome text block directly to the standard output.
        // DECISION: Using simple print statements to format the banner without external dependency overhead.
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("            Welcome to Hangman!             ");
        System.out.println(" I will think of a random word while you try to guess its letters. ");
        System.out.println(" Every time you guess a letter that isn't in my word, ");
        System.out.println("      a new body part of the hanging man appears. ");
        System.out.println("                 Good luck!!!               ");
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println();
    }

    @Override
    public int playOneGame(String secretWord) {
        // UNDERSTAND: Converts the input word to uppercase to maintain case-insensitive game states internally.
        secretWord = secretWord.toUpperCase();
        int guessesRemaining = MAX_GUESSES;
        StringBuilder guessedLetters = new StringBuilder();

        // UNDERSTAND: Loop terminates when the user runs out of guesses (reaches 0) or wins (exits inside the loop).
        // DECISION: A while-loop is selected because the exit conditions are state-driven rather than count-driven.
        while (guessesRemaining > 0) {
            displayHangman(guessesRemaining);
            String hint = createHint(secretWord, guessedLetters.toString());
            System.out.println("Secret word : " + hint);
            System.out.println("Your guesses: " + guessedLetters);
            System.out.println("Guesses left: " + guessesRemaining);

            char guess = readGuess(guessedLetters.toString());
            guessedLetters.append(guess);

            // UNDERSTAND: Branch logic evaluates whether the character exists in the secret word string.
            // DECISION: A simple if-else is used over a switch because we are testing a variable index lookup condition.
            if (secretWord.indexOf(guess) >= 0) {
                System.out.println("Correct!");
                // UNDERSTAND: Branch logic checks if the complete secret word has been revealed.
                if (isWordGuessed(secretWord, guessedLetters.toString())) {
                    displayHangman(guessesRemaining);
                    System.out.println("Secret word : " + secretWord);
                    System.out.println("Your guesses: " + guessedLetters);
                    System.out.println("You win! My word was \"" + secretWord + "\".");
                    return guessesRemaining;
                }
            } else {
                System.out.println("Incorrect.");
                guessesRemaining--;
            }
            System.out.println();
        }

        displayHangman(0);
        System.out.println("Secret word : " + secretWord);
        System.out.println("Your guesses: " + guessedLetters);
        System.out.println("You lose! My word was \"" + secretWord + "\".");
        return 0;
    }

    @Override
    public void displayHangman(int guessCount) {
        // UNDERSTAND: Invokes the renderer's rendering behavior.
        // DECISION: Any low-level IOException is wrapped in an unchecked RuntimeException to avoid declaring throws on the game contract.
        try {
            renderer.render(guessCount);
        } catch (IOException e) {
            throw new RuntimeException("Could not display the hangman picture.", e);
        }
    }

    @Override
    public String createHint(String secretWord, String guessedLetters) {
        StringBuilder hint = new StringBuilder();

        // UNDERSTAND: Loop terminates when we reach the end of the secret word (index 'i' equals word length).
        // DECISION: A standard for-loop is chosen because we are iterating sequentially across a known, finite range.
        for (int i = 0; i < secretWord.length(); i++) {
            // UNDERSTAND: Array/Collection operation accessing the character value at index 'i' from the secretWord string.
            char c = secretWord.charAt(i);

            // UNDERSTAND: Branch logic checks if the character is in the set of guessed characters.
            if (guessedLetters.indexOf(Character.toUpperCase(c)) >= 0) {
                hint.append(Character.toUpperCase(c));
            } else {
                hint.append('-');
            }
        }
        return hint.toString();
    }
}
