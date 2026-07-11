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