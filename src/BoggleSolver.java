import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

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
        ArrayList<String> validWords = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (String s : findAll(board, i, j))
                    if (!validWords.contains(s))
                        validWords.add(s);
            }
        }
        /*
        for (int i = 0; i < 4 * 4; i++) {
            for (String word : possibleWords.get(i)) {
                if (set.keysWithPrefix(word) == null)

            }
        }*/

        return validWords;
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

    private ArrayList<String> findAll(BoggleBoard board, int sourceX, int sourceY) {
        ArrayList<String> strings = new ArrayList<>();
        boolean[][] marked = new boolean[4][4];
        boolean validPath = true;


        for (int i = 0; i < 4; i++)
            Arrays.fill(marked[i], false);

        Dice item = new Dice(sourceX, sourceY, "" + board.getLetter(sourceX, sourceY));
        marked[sourceX][sourceY] = true;


        if (set.nextNode(set.getRoot(), item.value.charAt(item.value.length() - 1)) == null)
            validPath = false;
        else {
            for (String s : set.keysThatMatch(item.value)) {
                if (s.length() >= 3)
                    strings.add(s);
            }
        }

        marked[item.x][item.y] = true;

        if (validPath) {
            TrieSET.Node node = set.nextNode(set.getRoot(), item.value.charAt(item.value.length() - 1));
            for (int i = item.x - 1; i <= item.x + 1 && i < 4; i++) {
                for (int j = item.y - 1; j <= item.y + 1 && j < 4; j++) {
                    if (i >= 0 && j >= 0 && !marked[i][j]) {
                        strings.addAll(findAllUtil(board, node, marked, new Dice(i, j,
                                item.value + board.getLetter(i, j))));
                    }
                }
            }
        }

        marked[item.x][item.y] = false;
        return strings;
    }

    ArrayList<String> findAllUtil(BoggleBoard board, TrieSET.Node x, boolean[][] marked, Dice item) {
        marked[item.x][item.y] = true;
        boolean validPath = true;

        ArrayList<String> strings = new ArrayList<>();

        if (set.nextNode(x, item.value.charAt(item.value.length() - 1)) == null)
            validPath = false;
        else {
            for (String s : set.keysThatMatch(item.value)) {
                if (s.length() >= 3)
                    strings.add(s);
            }
        }

        if (validPath) {
            TrieSET.Node node = set.nextNode(x, item.value.charAt(item.value.length() - 1));
            for (int i = item.x - 1; i <= item.x + 1 && i < 4; i++) {
                for (int j = item.y - 1; j <= item.y + 1 && j < 4; j++) {
                    if (i >= 0 && j >= 0 && !marked[i][j]) {
                        strings.addAll(findAllUtil(board, node, marked, new Dice(i, j,
                                item.value + board.getLetter(i, j))));
                    }
                }
            }
        }

        marked[item.x][item.y] = false;
        return strings;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;

        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
