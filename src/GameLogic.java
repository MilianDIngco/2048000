package src;

/*
//  * Should have
 * Variable board size
 * and then other stuff
 */

public class GameLogic {

    boolean loseCondition = false;

    // add stuff to not let board width to get below 2x2
    int boardWidth, boardHeight;
    int numTilesGenerated;
    int mantissa; // 2 ^ etc
    int[][] boardState;// = {
    // { 1, 1, 4, 8 },
    // { 1, 2, 1, 2 },
    // { 1, 0, 0, 2 },
    // { 0, 0, 0, 0 },
    // };
    int[][] prevBoardState;
    int[] emptyRows;// = { 0, 0, 2, 4 };

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

    public GameLogic(int boardWidth, int boardHeight, int numTilesGenerated, int mantissa) {
        this.boardWidth = (boardWidth < 3) ? 3 : boardWidth;
        this.boardHeight = (boardHeight < 3) ? 3 : boardHeight;

        this.numTilesGenerated = (numTilesGenerated < 1) ? 1 : numTilesGenerated;
        this.mantissa = (mantissa < 2) ? 2 : mantissa;

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
                    System.out.println("------------------------failed to generate row------------------------");
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
                    System.out.println("------------------------failed to generate col------------------------");
                    break;
                }
            }
            int pickNum = (int) ((Math.random() < 0.9) ? Math.pow(mantissa, 1) : Math.pow(mantissa, 2));
            boardState[randRow][randCol] = pickNum;
            // check empty rows
            checkEmpty();
            // check lose conditions
            count++;
        }
    }

    public void rightMove() {
        for (int i = 0; i < boardHeight; i++) {
            int start = boardWidth - 1;
            int look = boardWidth - 2;
            while (look != -1 && start != 0 && start != look) {
                if (boardState[i][start] == 0 && boardState[i][look] != 0) { // fill empty space
                    boardState[i][start] = boardState[i][look];
                    boardState[i][look--] = 0;
                } else if (boardState[i][look] != 0 && boardState[i][look] == boardState[i][start]) { // combine squares
                    boardState[i][start] *= mantissa;
                    boardState[i][look] = 0;
                    start--;
                    look = start - 1;
                } else if (boardState[i][look] != 0 && boardState[i][look] != boardState[i][start]
                        && start - 1 != look) {
                    // slides different tiles next to each other
                    boardState[i][start - 1] = boardState[i][look];
                    boardState[i][look] = 0;
                    start--;
                    look = start - 1;
                } else if (boardState[i][look] != 0 && start - 1 == look) {
                    start = look;
                    look--;
                } else {
                    look--;
                }
                checkEmpty();
            }
        }
    }

    public void leftMove() {
        for (int i = 0; i < boardHeight; i++) {
            int start = 0;
            int look = 1;
            while (look != boardWidth && start != boardWidth - 1 && start != look) {
                if (boardState[i][start] == 0 && boardState[i][look] != 0) { // fill empty space
                    boardState[i][start] = boardState[i][look];
                    boardState[i][look++] = 0;
                } else if (boardState[i][look] != 0 && boardState[i][look] == boardState[i][start]) { // combine squares
                    boardState[i][start] *= mantissa;
                    boardState[i][look] = 0;
                    start++;
                    look = start + 1;
                } else if (boardState[i][look] != 0 && boardState[i][look] != boardState[i][start]
                        && start + 1 != look) {
                    // slides different tiles next to each other
                    boardState[i][start + 1] = boardState[i][look];
                    boardState[i][look] = 0;
                    start++;
                    look = start + 1;
                } else if (boardState[i][look] != 0 && start + 1 == look) {
                    start = look;
                    look++;
                } else {
                    look++;
                }
                checkEmpty();
            }
        }
    }

    public void upMove() {
        for (int i = 0; i < boardWidth; i++) {
            int start = 0;
            int look = 1;
            while (look != boardWidth && start != boardWidth - 1 && start != look) {
                if (boardState[start][i] == 0 && boardState[look][i] != 0) { // fill empty space
                    boardState[start][i] = boardState[look][i];
                    boardState[look][i] = 0;
                    look++;
                } else if (boardState[look][i] != 0 && boardState[look][i] == boardState[start][i]) { // combine squares
                    boardState[start][i] *= mantissa;
                    boardState[look][i] = 0;
                    start++;
                    look = start + 1;
                } else if (boardState[look][i] != 0 && boardState[look][i] != boardState[start][i]
                        && start + 1 != look) {
                    // slides different tiles next to each other
                    boardState[start + 1][i] = boardState[look][i];
                    boardState[look][i] = 0;
                    start++;
                    look = start + 1;
                } else if (boardState[look][i] != 0 && start + 1 == look) {
                    start = look;
                    look++;
                } else {
                    look++;
                }
                checkEmpty();
            }
        }
    }

    public void downMove() {
        for (int i = 0; i < boardWidth; i++) {
            int start = boardHeight - 1;
            int look = boardHeight - 2;
            while (look != -1 && start != 0 && start != look) {
                if (boardState[start][i] == 0 && boardState[look][i] != 0) { // fill empty space
                    boardState[start][i] = boardState[look][i];
                    boardState[look][i] = 0;
                    look--;
                } else if (boardState[look][i] != 0 && boardState[look][i] == boardState[start][i]) { // combine squares
                    boardState[start][i] *= mantissa;
                    boardState[look][i] = 0;
                    start--;
                    look = start - 1;
                } else if (boardState[look][i] != 0 && boardState[look][i] != boardState[start][i]
                        && start - 1 != look) {
                    // slides different tiles next to each other
                    boardState[start - 1][i] = boardState[look][i];
                    boardState[look][i] = 0;
                    start--;
                    look = start - 1;
                } else if (boardState[look][i] != 0 && start - 1 == look) {
                    start = look;
                    look--;
                } else {
                    look--;
                }
                checkEmpty();
            }
        }
    }

    public boolean checkLost() {
        return false;
    }

    public void checkEmpty() {
        for (int i = 0; i < boardState.length; i++) {
            int count = boardWidth;
            for (int n = 0; n < boardState[i].length; n++) {
                if (boardState[i][n] != 0) {
                    count--;
                }
            }
            emptyRows[i] = count;
        }
    }

    public void copyBoard() {
        for (int i = 0; i < boardState.length; i++)
            for (int n = 0; n < boardState[i].length; n++)
                prevBoardState[i][n] = boardState[i][n];
    }

    public void printPrevBoard() {
        System.out.println("--------------------");
        for (int[] i : prevBoardState) {
            for (int n : i) {
                System.out.print(n + " ");
            }
            System.out.println();
        }
        System.out.println("--------------------");
    }

    public void printBoard() {
        System.out.println("--------------------");
        int count = 0;
        for (int[] i : boardState) {
            System.out.print(emptyRows[count++] + "{ ");
            // System.out.print("{ ");
            for (int n : i) {
                System.out.print(n + ", ");
            }
            System.out.print("},\n");
        }
        System.out.println("--------------------");
    }

}
