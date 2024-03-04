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
    int[] emptyRows;
    int[] emptyCols;

    /**
     * Each time a game is created, pass settings to constructor
     */
    public GameLogic() {
        boardWidth = 4;
        boardHeight = 4;

        numTilesGenerated = 2;
        mantissa = 2;
        boardState = new int[boardHeight][boardWidth];
        // initialize boardState
        for (int i = 0; i < boardState.length; i++)
            for (int n = 0; n < boardState[i].length; n++)
                boardState[i][n] = -1;

        // holds number of empty spaces in a row
        emptyRows = new int[boardHeight];
        for (int i = 0; i < emptyRows.length; i++)
            emptyRows[i] = boardWidth;
        emptyCols = new int[boardWidth];
        for (int i = 0; i < emptyCols.length; i++)
            emptyCols[i] = boardHeight;
    }

    /**
     * Generates tiles in random empty positions if there are any
     * Generates up to numTilesGenerated tiles where available
     */
    public void generateRandom() {
        int count = 0;
        while (count < numTilesGenerated) {
            // generate tiles
            int selectedRow = -1, selectedCol = -1;

            int randRow = (int) (Math.random() * boardHeight);
            int randCol = (int) (Math.random() * boardWidth);

            if (emptyRows[randRow] <= 0) {
                for (int i = randRow; i < (randRow + boardHeight); i++) {
                    if (emptyRows[i % boardHeight] > 0) {
                        selectedRow = i % boardHeight;
                        emptyRows[selectedRow]++;
                        break;
                    }
                }
                // if all of the rows are empty, set lose condition to 0 and break out of while
                if (selectedRow < 0)
                    break;
            } else {
                selectedRow = randRow;
                emptyRows[selectedRow]++;
            }

            if (emptyCols[randCol] <= 0) {
                for (int i = randCol; i < (randCol + boardWidth); i++) {
                    if (emptyCols[i % boardWidth] > 0) {
                        selectedCol = i % boardWidth;
                        emptyCols[selectedCol]++;
                        break;
                    }
                }
                // if all of the rows are empty, set lose condition to 0 and break out of while
                if (selectedCol < 0)
                    break;
            } else {
                selectedCol = randCol;
                emptyCols[selectedCol]++;
            }

            // if successful
            if (selectedRow >= 0 && selectedCol >= 0) {
                // System.out.println(selectedRow + " " + selectedCol + "\n");
                count++;
            } else
                break;
        }

        // check loss condition
        if (count < numTilesGenerated) {
            checkLost();
        }
    }

    public boolean checkLost() {
        return false;
    }

}
