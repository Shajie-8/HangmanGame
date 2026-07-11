// AI-CHECK: Verified that the contract utilizes the standard java.io.IOException propagation pattern for file fetching.
package ph.edu.dlsu.lbycpob.hangman.repository;

import java.io.IOException;

public interface WordRepository {
    // UNDERSTAND: Contract to load a file and select a random word from it.
    // DECISION: Propagates IOException to allow calling strategies to handle missing resources.
    String getRandomWord(String filename) throws IOException;
}