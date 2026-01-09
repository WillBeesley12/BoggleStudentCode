import java.util.ArrayList;
import java.util.Arrays;

public class Boggle {
    private static boolean [][] visited;
    private static TST TST = new TST();
    private static int row;
    private static int col;
    private static ArrayList<String> goodWords;

    public static void dfs(int r, int c, String word, char[][] board) {
        // Check to make sure we are not at the edge of the board
        if (r < 0 || r >= row || c < 0 || c >= col) {
            return;
        }
        if (visited[r][c]) {
            return;
        }
        // Add to the word
        word += board[r][c];

        // If there aren't any words starting with this prefix, then just stop
        String longestPrefix = TST.getLongestPrefix(word);
        if (!longestPrefix.equals(word)) {
            return;
        }
        // Mark visited
        visited[r][c] = true;
        // If this is a word that isn't already in goodWords, add it
        if (TST.lookup(word) == 1) {
            goodWords.add(word);
            TST.insert(word, 0);
        }
        // Go all four directions
        dfs(r + 1, c, word, board);
        dfs(r, c + 1, word, board);
        dfs(r, c - 1, word, board);
        dfs(r - 1, c, word, board);

        // Go back
        visited[r][c] = false;
    }

    public static String[] findWords(char[][] board, String[] dictionary) {
        row = board.length;
        col = board[0].length;
        visited = new boolean[row][col];
        goodWords = new ArrayList<String>();
        // Insert all words into the TST
        TST = new TST();
        for (int i = 0; i < dictionary.length; i++) {
            TST.insert(dictionary[i], 1);
        }
        // Do each combo starting at every single place on the board
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                dfs(i, j, "", board);
            }
        }

        // Convert the list into a sorted array of strings, then return the array.
        String[] sol = new String[goodWords.size()];
        goodWords.toArray(sol);
        Arrays.sort(sol);
        return sol;
    }
}
