package cis163.connect4;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Charles Dodge on 9/17/15.
 */
public class ConnectFourTester {

    private ConnectFour myGame;

    @Before
    public void commonSetup() {
        myGame = new ConnectFour();
    }

    @Test
    public void gameStatusInProgressOnStart() {
        assertEquals(GameState.IN_PROGRESS, myGame.getStatus());
    }

    @Test
    public void playerOneShouldGoFirst() {
        assertEquals(Player.PlayerOne, myGame.getTurn());
    }

    @Test
    public void droppingADiscToANonFullColumnShallUpdatePlayerTurn() {
        assertEquals(Player.PlayerOne, myGame.getTurn());
        int this_var_is_unused = myGame.dropDiskAt(3);
        assertEquals(Player.PlayerTwo, myGame.getTurn());
    }

    @Test
    public void droppingADiscToANonFullColumnShouldIncreaseHeight() {
        int h1 = myGame.dropDiskAt(2); /* first player */
        int h2 = myGame.dropDiskAt(2); /* second player */
        assertEquals (h2, h1 + 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void droppingToFullColumnWillThrowException() {
        /* with default board height of 7 rows, the eight drop will
           trigger an exception!
         */
        for (int k = 0; k < 8; k++)
            myGame.dropDiskAt(0);
    }

    @Test
    public void testPlayerOneWinFourVertical () {

        int h1 = myGame.dropDiskAt(2);
        int h2 = myGame.dropDiskAt(3);
        h1 = myGame.dropDiskAt(2);
        h2 = myGame.dropDiskAt(3);
        h1 = myGame.dropDiskAt(2);
        h2 = myGame.dropDiskAt(3);
        h1 = myGame.dropDiskAt(2);


        assertEquals("PlayerOne horizontal win fail",GameState.PLAYER_ONE_WON, myGame.getStatus());
    }
    @Test
    public void playerOneHasNotWon(){
        int h1 = myGame.dropDiskAt(2);
        int h2 = myGame.dropDiskAt(2);
        h1 = myGame.dropDiskAt(2);
        h2 = myGame.dropDiskAt(3);
        h1 = myGame.dropDiskAt(2);
        h2 = myGame.dropDiskAt(3);
        h1 = myGame.dropDiskAt(2);
        assertEquals(GameState.IN_PROGRESS, myGame.getStatus());
    }
    @Test
    public void testPlayerTwoWinVertical(){
        int h1 = myGame.dropDiskAt(2);
        int h2 = myGame.dropDiskAt(3);
            h1 = myGame.dropDiskAt(2);
            h2 = myGame.dropDiskAt(3);
            h1 = myGame.dropDiskAt(2);
            h2 = myGame.dropDiskAt(3);
            h1 = myGame.dropDiskAt(4);
            h2 = myGame.dropDiskAt(3);


        assertEquals("PlayerTwo vertical win fail",GameState.PLAYER_TWO_WON, myGame.getStatus());
    }
    @Test
    public void testPlayerOneWinFourHorizontal () {

        int h1 = myGame.dropDiskAt(1);
        int h2 = myGame.dropDiskAt(1);
        h1 = myGame.dropDiskAt(2);
        h2 = myGame.dropDiskAt(2);
        h1 = myGame.dropDiskAt(3);
        h2 = myGame.dropDiskAt(3);
        h1 = myGame.dropDiskAt(4);


        assertEquals("PlayerOne horizontal win fail",GameState.PLAYER_ONE_WON, myGame.getStatus());
    }
    @Test
    public void testPlayerTwoWinHorizontal () {
        int h1 = myGame.dropDiskAt(1);
        int h2 = myGame.dropDiskAt(2);
        h1 = myGame.dropDiskAt(1);
        h2 = myGame.dropDiskAt(3);
        h1 = myGame.dropDiskAt(2);
        h2 = myGame.dropDiskAt(4);
        h1 = myGame.dropDiskAt(2);
        h2 = myGame.dropDiskAt(5);

        assertEquals("PlayerTwo horizontal win fail", GameState.PLAYER_TWO_WON, myGame.getStatus());
    }
    @Test
    public void testPlayerTwoWinDiagonalNE2SW () {
        int h1 = myGame.dropDiskAt(2);
        int h2 = myGame.dropDiskAt(1);
        h1 = myGame.dropDiskAt(1);
        h2 = myGame.dropDiskAt(2);
        h1 = myGame.dropDiskAt(3);
        h2 = myGame.dropDiskAt(4);
        h1 = myGame.dropDiskAt(3);
        h2 = myGame.dropDiskAt(3);
        h1 = myGame.dropDiskAt(4);
        h2 = myGame.dropDiskAt(4);
        h1 = myGame.dropDiskAt(1);
        h2 = myGame.dropDiskAt(4);

        assertEquals("PlayerTwo SW to NE Diagonal win fail", GameState.PLAYER_TWO_WON, myGame.getStatus());
    }
    @Test
    public void testPlayerOneWinDiagonalNE2SW () {
        int h1 = myGame.dropDiskAt(1);
        int h2 = myGame.dropDiskAt(2);
        h1 = myGame.dropDiskAt(2);
        h2 = myGame.dropDiskAt(1);
        h1 = myGame.dropDiskAt(4);
        h2 = myGame.dropDiskAt(3);
        h1 = myGame.dropDiskAt(3);
        h2 = myGame.dropDiskAt(4);
        h1 = myGame.dropDiskAt(3);
        h2 = myGame.dropDiskAt(4);
        h1 = myGame.dropDiskAt(4);
        h2 = myGame.dropDiskAt(1);

        assertEquals("PlayerOne SW to NE Diagonal win fail", GameState.PLAYER_ONE_WON, myGame.getStatus());
    }

    @Test
    public void testPlayerTwoWinDiagonalSE2NW () {
        int h1 = myGame.dropDiskAt(1 );
        int h2 = myGame.dropDiskAt(4 );
            h1 = myGame.dropDiskAt(3 );
            h2 = myGame.dropDiskAt(3 );
            h1 = myGame.dropDiskAt(2 );
            h2 = myGame.dropDiskAt(2 );
            h1 = myGame.dropDiskAt(4 );
            h2 = myGame.dropDiskAt(2 );
            h1 = myGame.dropDiskAt(1 );
            h2 = myGame.dropDiskAt(1 );
            h1 = myGame.dropDiskAt( 3);
            h2 = myGame.dropDiskAt( 1);

        assertEquals("PlayerTwo SE to NW Diagonal win fail", GameState.PLAYER_TWO_WON, myGame.getStatus());
    }
    @Test
    public void testPlayerOneWinDiagonalSE2NW  () {
        int h1 = myGame.dropDiskAt(4);
        int h2 = myGame.dropDiskAt(3);
            h1 = myGame.dropDiskAt(3);
            h2 = myGame.dropDiskAt(2);
            h1 = myGame.dropDiskAt(2);
            h2 = myGame.dropDiskAt(4);
            h1 = myGame.dropDiskAt(2);
            h2 = myGame.dropDiskAt(1);
            h1 = myGame.dropDiskAt(1);
            h2 = myGame.dropDiskAt(1);
            h1 = myGame.dropDiskAt(1);


        assertEquals("PlayerOne SE to NW Diagonal win fail", GameState.PLAYER_ONE_WON, myGame.getStatus());
    }


}
