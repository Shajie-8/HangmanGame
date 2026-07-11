// AI-CHECK: Verified that this interface is a single-method functional interface designed for modular output.
package ph.edu.dlsu.lbycpob.hangman.render;

import java.io.IOException;

public interface HangmanRenderer {
    // UNDERSTAND: Renders the hangman drawing for the specified remaining guess count.
    // DECISION: Throws IOException to handle classpath file loading failures at the method boundary.
    void render(int guessesRemaining) throws IOException;
}