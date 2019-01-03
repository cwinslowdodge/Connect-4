package cis163.connect4;

import java.util.Stack;

/**
 * Created by Charles Dodge on 9/17/15.
 */
public class ConnectFour {
    int currentPlayer;
    int [][] board;
    boolean gameWinner = false;
    GameState currentState;
    Stack history = new Stack();
    int discTotal = 0;
    int maxTurns;

    /**
     * a constructor that creates a game board of size 7x7
     */
    public ConnectFour() {
        this (7, 7); /* call the second constructor below */
    }

    /**
     * Create a game board of the specified dimension
     * @param col
     * @param row
     */
    public ConnectFour (int row, int col) {
        /* a constructor that creates a game board of size heightxwidth */
        currentPlayer = 1;
        board = new int[row][col];
        currentState = GameState.IN_PROGRESS;
        maxTurns = col * row;
    }

    /**
     * Simulate the current player dropping her colored disk into the
     * specified column
     * @param col the column number where the disk is dropped
     * @return the number of disks now placed in the column ("disc height")
     * @throws IllegalArgumentException when the specified column is FULL
     * (no room for new disks)
     */
    public int dropDiskAt (int col) {
        int rowBoundry = board.length - 1;

        //if greater than 0 the space has been used
        if(board[0][col] > 0){
            throw new IllegalArgumentException("Column Full");
        }

        while (rowBoundry >= 0){

            //if 0 then space hasn't been used
            if(board[rowBoundry][col] == 0){
                board[rowBoundry][col] = currentPlayer;
                verticalWins(col);
                horizontalWin(rowBoundry);
                diagonalWin(rowBoundry, col);
                discTotal ++;

                int[] position = {rowBoundry, col};//new int[]
                history.push(position);

                if(discTotal == maxTurns){
                    currentState = GameState.CATS;
                }
                if(!gameWinner){
                    togglePlayer();
                }
                break;
            }
            rowBoundry --;
        }
        return board.length - rowBoundry;
    }

    /**
     * Which player is currently playing?
     * @return Player.PlayerOne or Player.PlayerTwo
     */
    public Player getTurn() {
        return (currentPlayer == 1) ? Player.PlayerOne : Player.PlayerTwo;
    }

    public void playerWins(){

        currentState = (currentPlayer == 1) ? GameState.PLAYER_ONE_WON:GameState.PLAYER_TWO_WON;

    }
    /**
     * How far are we into the game?
     *
     * @return one of the enumeration members of GameState
     */
    public GameState getStatus() {

        return currentState;

    }

    /**
     * undoes last move
     * @return
     */
    public int[] undo(){

        if(history.empty()){
            throw new IllegalStateException("No moves to undo.");
        }
        discTotal --;
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        int[] turn = (int[]) history.pop();
        board[turn[0]][turn[1]] = 0;
        return turn;

    }

    /**
     * checks for horizontal connection of 4 connections of current player on current row
     * @param discHeight
     * @return
     */
    private boolean horizontalWin(int discHeight){

        int i = 0,
                connects = 0;

        while(i < board[1].length){
            if(board[discHeight][i] == currentPlayer){
                connects ++;}
            else {
                connects = 0;
            }
            if(connects == 4){
                gameWinner = true;
                playerWins();
                break;
            }
            i++;
        }
        return gameWinner;
    }

    /**
     * checks for vertical connection of 4 in a row of last column played
     * @param column
     * @return
     */
    private boolean verticalWins(int column){
        int i = 0, connects = 0;

        while(i < board.length){
            if(board[i][column] == currentPlayer){
                ++ connects;
            }
            else {
                connects = 0;
            }
            if(connects == 4){
                playerWins();
                gameWinner = true;
                break;
            }
            i++;
        }
        return gameWinner;
    }

    /**
     * tests diagonal from ne and nw to bottom of board of current point played
     * to check for 4 in a row of current player
     * @param row
     * @param column
     * @return
     */
    private boolean diagonalWin(int row, int column){

        int maxRow = row;
        int maxCol = column;
        int connectNE = 0;

        while(maxRow > 0 && maxCol < board[0].length - 1){
            maxRow--;
            maxCol++;

        }

        while (maxRow < board.length - 1 && maxCol > 0) {
            maxRow++;
            maxCol--;

            if(board[maxRow][maxCol] == currentPlayer){
                connectNE ++;
            }else{
                connectNE = 0;
            }

            if(connectNE == 4){
                gameWinner = true;
                playerWins();
                break;
            }
        }

        int minCol = column;
        int maxRow2 = row;
        int connectNW = 0;

        while(maxRow2 > 0 && minCol > 0){
            minCol--;
            maxRow2--;
        }

        while (maxRow2 < board.length - 1 && minCol < board[0].length - 1) {
            maxRow2++;
            minCol++;
            if(board[maxRow2][minCol] == currentPlayer){
                connectNW ++;
            }else{
                connectNW = 0;
            }
            if(connectNW == 4){
                gameWinner = true;
                playerWins();
                break;
            }
        }
        return gameWinner;
    }

    /**
     * toggles between player 1 and 2
     */
    private void togglePlayer(){
        if(currentPlayer == 1){
            currentPlayer = 2;
        }else{
            currentPlayer = 1;
        }
    }
}
