package nl.kvtulder.tictactoe;

import java.io.Serializable;

public class Game implements Serializable {
    final private int BOARD_SIZE = 3;
    private Tile[][] board;

    private Boolean playerOneTurn;  // true if player 1's turn, false if player 2's turn
    private int movesPlayed;
    private Boolean gameOver;

    public Game() {
        // initialize the board
        board = new Tile[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                board[i][j] = Tile.BLANK;

        // set the new begin conditions
        playerOneTurn = true;
        gameOver = false;
        movesPlayed = 0;

    }

    // checks if a move is a valid move; if so, it updates the board
    public Tile draw(int row, int column) {

        switch (board[row][column]) {
            // if the clicked tile is blank, it is a valid move
            case BLANK:

                movesPlayed++;
                // draw a circle if it's the turn of player 1.
                if (playerOneTurn) {
                    playerOneTurn = false;
                    board[row][column] = Tile.CIRCLE;
                    return Tile.CIRCLE;
                } else {
                    board[row][column] = Tile.CROSS;
                    playerOneTurn = true;
                    return Tile.CROSS;
                }
            // tile not blank, invalid move; return invalid
            default:
                return Tile.INVALID;
        }
    }

    // make the board and the game over status value public, but not public editable
    public Tile[][] getBoard() {
        return board;
    }
    public Boolean getGameOver() {
        return gameOver;
    }

    // returns the game status: won: (p1 || p2), draw or in progress
    public GameState getGameState() {

        // Check if diagonal
        Boolean diagonal = checkIfWon(board[0][0], board[1][1], board[2][2]) ||
                checkIfWon(board[0][0], board[1][1], board[2][2]);
        if (diagonal) {
            gameOver = true;
            if (board[1][1] == Tile.CIRCLE)
                return GameState.PLAYER_ONE;
            else
                return GameState.PLAYER_TWO;
        }

        // check all horizontal and vertical
        for (int i = 0; i < 3; i++) {

            Boolean horizontal = checkIfWon(board[i][0], board[i][1], board[i][2]);
            Boolean vertical = checkIfWon(board[0][i], board[1][i], board[2][i]);
            if (horizontal || vertical) {

                gameOver = true;
                // a player won, but who?
                if (board[i][0] == Tile.CIRCLE && horizontal)
                    return GameState.PLAYER_ONE;
                else if (board[0][i] == Tile.CIRCLE && vertical)
                    return GameState.PLAYER_ONE;
                else
                    return GameState.PLAYER_TWO;
            }
        }
        // check if there is a empty tile
        if (movesPlayed < 9)
            return GameState.IN_PROGRESS;

        // no tiles over, a draw
        return GameState.DRAW;
    }

    // checks if three provided tiles are the same and not blank
    private boolean checkIfWon(Tile t1, Tile t2, Tile t3) {
        return (t1 == t2) && (t2 == t3) && (t1 != Tile.BLANK);
    }
}


