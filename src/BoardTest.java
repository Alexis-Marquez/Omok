import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashSet;

import static junit.framework.TestCase.*;


public class BoardTest {
    //Here
    private Board board;
    private HumanPlayer player1;
    private HumanPlayer player2;

    @Before
    public void setUp() {
        this.board = new Board(10);
        player1 = new HumanPlayer("Player 1",'1', Color.BLACK);
        player2 = new HumanPlayer("Player 2",'2', Color.WHITE);
        }

    @After
    public void tearDown() {
        board.clear();
    }

    @Test
    public void TestSize(){
        assertEquals(10, board.size());
    }

    @Test
    public void TestClear1() {
        board.clear();
        board.placeStone(4, 4, player1);
        board.clear();
        assertTrue(board.isEmpty(4, 4));
    }
    @Test
    public void TestClear2() {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                board.placeStone(i, j, player1);
            }
        }
        board.clear();
        assertTrue(board.isEmpty(4, 4));
    }

    @Test
    public void TestIsFull1() {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.size(); j++) {
                board.placeStone(i, j, player1);
            }
        }
        assertTrue(board.isFull());
    }
    @Test
    public void TestIsFull2() {
        assertFalse(board.isFull());
    }

    @Test
    public void TestPlaceStone1() {
        board.placeStone(0, 0, player1);
        assertTrue(board.isOccupiedBy(0, 0, player1));
    }
    @Test
    public void TestPlaceStone2() {
        board.placeStone(0, 0, player2);
        assertFalse(board.isOccupiedBy(0, 0, player1));
    }

    @Test
    public void TestIsEmpty1() {
        board.placeStone(0,0,player1);
        assertFalse(board.isEmpty(0,0));
    }

    @Test
    public void TestIsEmpty2() {
        assertTrue(board.isEmpty(0,0));
    }

    @Test
    public void TestIsOccupied1() {
        board.placeStone(0,0,player1);
        assertTrue(board.isOccupied(0,0));
    }
    @Test
    public void TestIsOccupied2() {
        assertFalse(board.isOccupied(0,0));
    }
    @Test
    public void TestIsOccupiedBy1() {
        board.placeStone(0,0,player1);
        assertTrue(board.isOccupiedBy(0,0,player1));
    }
    @Test
    public void TestIsOccupiedBy2() {
        board.placeStone(0,0,player2);
        assertFalse(board.isOccupiedBy(0,0,player1));
    }
    @Test
    public void TestPlayerAt1() {
        board.placeStone(0,0,player1);
        HumanPlayer player = (HumanPlayer) board.playerAt(0,0,player1,player2);
        assertEquals(player, player1);
    }
    @Test
    public void TestIsWonBy() {
        board.clear();
        //horizontal win
        board.placeStone(0, 0, player1);
        board.placeStone(0, 1, player1);
        board.placeStone(0, 2, player1);
        board.placeStone(0, 3, player1);
        board.placeStone(0, 4, player1);
        assertTrue(board.isWonBy(4,0,player1));

        //vertical win
        board.clear();
        board.placeStone(0, 0, player1);
        board.placeStone(1, 0, player1);
        board.placeStone(2, 0, player1);
        board.placeStone(3, 0, player1);
        board.placeStone(4, 0, player1);
        assertTrue(board.isWonBy(0,4,player1));

        //diagonal 1 win
        board.clear();
        board.placeStone(0, 0, player1);
        board.placeStone(1, 1, player1);
        board.placeStone(2, 2, player1);
        board.placeStone(3, 3, player1);
        board.placeStone(4, 4, player1);
        assertTrue(board.isWonBy(4,4,player1));

        //diagonal 2 win
        board.clear();
        board.placeStone(4, 4, player1);
        board.placeStone(3, 3, player1);
        board.placeStone(2, 2, player1);
        board.placeStone(1, 1, player1);
        board.placeStone(0, 0, player1);
        assertTrue(board.isWonBy(4,4,player1));
    }

    @Test
    public void TestWinningRow() {

        board.placeStone(0, 4, player1);
        board.placeStone(0, 3, player1);
        board.placeStone(0, 2, player1);
        board.placeStone(0, 1, player1);
        board.placeStone(0, 0, player1);
        board.isWonBy(0,0,player1);
        HashSet <Board.Place> winningRow = new HashSet<>();
        winningRow.add(new Board.Place(0,4));
        winningRow.add(new Board.Place(0,3));
        winningRow.add(new Board.Place(0,2));
        winningRow.add(new Board.Place(0,1));
        winningRow.add(new Board.Place(0,0));
        if(board.winningRow()==null) fail();
        assertEquals(winningRow, board.winningRow());
    }
}