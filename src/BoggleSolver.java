import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.TrieSET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class BoggleSolver {
    private final TrieSET set;

    private class Dice {
        private int x, y;
        private String value;

        Dice(int x, int y, String value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        int X() {
            return this.x;
        }

        int Y() {
            return this.y;
        }

        String value() {
            return this.value;
        }
    }

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
                possibleWords.addAll(findAll(board, i, j));
            }
        }

        BoggleBoard board;
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

    private Iterable<Dice> adjacent(BoggleBoard board, Dice x) {
        Bag<Dice> adjacentDice = new Bag<>();

        for (int i = x.X() - 1; i < x.X() + 2; i++) {
            if (i < 0 || i >= 4)
                continue;
            for (int j = x.Y() - 1; j < x.Y() + 2; j++) {
                if (j < 0 || j >= 4)
                    continue;

                adjacentDice.add(new Dice(i, j, x.value() + board.getLetter(i, j)));
            }
        }

        return adjacentDice;
    }

    private ArrayList<String> findAll(BoggleBoard board, int sourceX, int sourceY) {
        ArrayList<String> strings = new ArrayList<>();
        Stack<Dice> stack = new Stack<>();
        boolean[][] marked = new boolean[4][4];

        for (int i = 0; i < 4; i++)
            Arrays.fill(marked[i], false);

        stack.push(new Dice(sourceX, sourceY, String.valueOf(board.getLetter(sourceX, sourceY))));
        Dice item;

        while (!stack.isEmpty()) {
            item = stack.pop();

            if (item.value().length() >= 3)
                strings.add(item.value());

            marked[item.X()][item.Y()] = true;

            for (Dice adj : adjacent(board, item)) {
                if (!marked[adj.X()][adj.Y()])
                    stack.push(adj);
            }
        }

        return strings;
    }

    public static void main(String[] args) {
        BoggleBoard board = new BoggleBoard("board-points4.txt");
        In in = new In("dictionary-algs4.txt");
        BoggleSolver boggleSolver = new BoggleSolver(in.readAllLines());
    }

}
