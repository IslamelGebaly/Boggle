import edu.princeton.cs.algs4.TrieSET;

import java.util.ArrayList;
import java.util.Stack;

public class BoggleSolver {
    private final TrieSET set;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null)
            throw new IllegalArgumentException();
        set = new TrieSET();
        for (String string : dictionary) {
            set.add(string);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        ArrayList<String> possibleWords = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                possibleWords.addAll(findAll(board, i, j))
            }
        }
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (set.contains(word)) {
            switch (word.length()) {
                case 3:
                    return 1;
                case 4:
                    return 1;
                case 5:
                    return 2;
                case 6:
                    return 3;
                case 7:
                    return 5;
                case 8:
                    return 11;
                default:
                    if (word.length() > 8)
                        return 11;
                    else
                        return 0;
            }
        }
        return 0;
    }

    public ArrayList<String> findAll(BoggleBoard board, int sourceX, int sourceY) {
        Stack<String> stack = new Stack<>();
        stack.push("" + board.getLetter(sourceX, sourceY));

        String
        while (!stack.isEmpty()) {

        }
    }

}
