package ph.edu.dlsu.lbycpob.hangman;

import ph.edu.dlsu.lbycpob.hangman.game.Hangman;
import ph.edu.dlsu.lbycpob.hangman.render.AsciiArtRenderer;
import ph.edu.dlsu.lbycpob.hangman.render.HangmanRenderer;
import ph.edu.dlsu.lbycpob.hangman.repository.ClasspathWordRepository;
import ph.edu.dlsu.lbycpob.hangman.repository.WordRepository;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final String GAME_ASSETS_BASE_PATH = "/game-assets";

    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        HangmanRenderer renderer = new AsciiArtRenderer(GAME_ASSETS_BASE_PATH + "/hangman-art");
        WordRepository dummyRepo = new ClasspathWordRepository(GAME_ASSETS_BASE_PATH + "/words", random);

        Hangman introGame = new Hangman(renderer, dummyRepo, random, "");
        introGame.intro();

        // UNDERSTAND: Input/Output operation reading the dictionary file path selection from the user.
        System.out.print("Enter the word list filename (test.txt, words.txt, or large.txt): ");
        String filename = scanner.nextLine().trim();

        WordRepository repository;
        // UNDERSTAND: Variable declaration of the local File instance to check system file presence.
        File localFile = new File(filename);