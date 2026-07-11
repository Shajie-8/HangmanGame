// AI-CHECK: Validated record immutable fields, checking validation triggers during construction.
package ph.edu.dlsu.lbycpob.hangman.statistics;

import java.util.Locale;

public record GameStatistics(int gamesPlayed, int gamesWon, int bestGuessesRemaining) {
    // UNDERSTAND: Compact constructor validates the integrity of state variables before object creation.
    // DECISION: Using a record class guarantees immutability, eliminating risk of outside side-effects.
    public GameStatistics {
        // UNDERSTAND: Branch logic checks that counts and metrics cannot fall below zero.
        if (gamesPlayed < 0) {
            throw new IllegalArgumentException("gamesPlayed must be >= 0, got " + gamesPlayed);
        }
        if (gamesWon < 0 || gamesWon > gamesPlayed) {
            throw new IllegalArgumentException("gamesWon must be between 0 and gamesPlayed (" + gamesPlayed + "), got " + gamesWon);
        }
        if (bestGuessesRemaining < 0) {
            throw new IllegalArgumentException("bestGuessesRemaining must be >= 0, got " + bestGuessesRemaining);
        }
    }

    public static GameStatistics empty() {
        return new GameStatistics(0, 0, 0);
    }

    public GameStatistics withGame(boolean won, int guessesRemaining) {
        if (guessesRemaining < 0) {
            throw new IllegalArgumentException("guessesRemaining must be >= 0, got " + guessesRemaining);
        }
        // UNDERSTAND: Variable declaration tracking best score. Uses ternary logic to define the first game's baseline.
        int newBest = (gamesPlayed == 0) ? guessesRemaining : Math.max(bestGuessesRemaining, guessesRemaining);
        return new GameStatistics(gamesPlayed + 1, gamesWon + (won ? 1 : 0), newBest);
    }
