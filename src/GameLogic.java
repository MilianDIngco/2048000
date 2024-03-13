package src;

/*
 * Should have
 * Variable board size
 * and then other stuff
 */

public class GameLogic {

    boolean loseCondition = false;

    int boardWidth, boardHeight;
    int numTilesGenerated;
    int mantissa; // 2 ^ etc
    int[][] boardState;
    int[][] prevBoardState;
    int[] emptyRows;

    /**
     * Each time a game is created, pass settings to constructor
     */
    public GameLogic() {
        boardWidth = 4;
        boardHeight = 4;

        numTilesGenerated = 2;
        mantissa = 2;
        boardState = new int[boardHeight][boardWidth];
        prevBoardState = new int[boardHeight][boardWidth];
        // initialize boardState
        for (int i = 0; i < boardState.length; i++) {
            for (int n = 0; n < boardState[i].length; n++) {
                boardState[i][n] = 0;
                prevBoardState[i][n] = 0;
            }
        }
        // holds number of empty spaces in a row
        emptyRows = new int[boardHeight];
        for (int i = 0; i < emptyRows.length; i++)
            emptyRows[i] = boardWidth;
    }

    /**
     * Generates tiles in random empty positions if there are any
     * Generates up to numTilesGenerated tiles where available
     */
    public void generateRandom() {
        int count = 0;

        while (count < numTilesGenerated) {
            // select random row
            int randRow = (int) (Math.random() * (double) boardHeight);
            int prevRow = randRow;
            // loop until find empty row
            if (emptyRows[randRow] == 0) {
                for (int i = randRow + 1; i < emptyRows.length + randRow; i++) {
                    if (emptyRows[i % emptyRows.length] > 0) {
                        randRow = i % emptyRows.length;
                        break;
                    }
                }
                if (randRow == prevRow) {
                    // check lose condition
                    // if lost, break;
                    System.out.println("failed to generate row");
                    break;
                }
            }

            // select random column
            int randCol = (int) (Math.random() * (double) boardWidth);
            int prevCol = randCol;
            // loop until find empty column
            if (boardState[randRow][randCol] != 0) {
                int len = boardState[randRow].length;
                for (int i = randCol + 1; i < len + randCol + 1; i++) {
                    if (boardState[randRow][i % len] == 0) {
                        randCol = i % len;
                        break;
                    }
                }
                if (randCol == prevCol) {
                    System.out.println("failed to generate col");
                    break;
                }
            }
            boardState[randRow][randCol] = 1;
            emptyRows[randRow]--;
            printBoard();
            // check lose conditions
            count++;
        }
    }

    public boolean checkLost() {
        return false;
    }

    public void copyBoard() {
        for (int i = 0; i < boardState.length; i++)
            for (int n = 0; n < boardState[i].length; n++)
                prevBoardState[i][n] = boardState[i][n];
    }

    public void printBoard() {
        System.out.println("--------------------");
        for (int[] i : boardState) {
            for (int n : i) {
                System.out.print(n + " ");
            }
            System.out.println();
        }
        System.out.println("--------------------");
    }

}
