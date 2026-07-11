package ph.edu.dlsu.lbycpob.hangman.game;

public interface HangmanGame {
    void run();
    void intro();
    int playOneGame(String secretWord);
    void displayHangman(int guessCount);
    String createHint(String secretWord, String guessedLetters);
    char readGuess(String guessedLetters);
    String getRandomWord(String filename);
    void stats(int gamesCount, int gamesWon, int best);
}