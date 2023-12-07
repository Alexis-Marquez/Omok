// Alexis Marquez
// Paola Alvarado

import java.util.Arrays;
import java.util.HashSet;

public class Board {
//    int DEFAULT_SIZE = 15;

    public char[][] getCurrentBoard() {
        return board;
    }

    private final char[][] board; //board representation
    protected int size;

    public Place getLastMove() {
        return lastMove;
    }

    private Place lastMove;
    private boolean won = false;
    String winner;
    protected int x1,x2,y1,y2;
    private final HashSet<Place> winningRow = new HashSet<>();

    //    public Board() {
//        this.size = DEFAULT_SIZE;x1=x2=y1=y2=-5;
//        board = new char[size][size];
//        for (int col = 0; col<size;col++) {
//            for (int row = 0; row<size;row++) {
//                board[col][row] = '0';
//            }
//        }
//    }

    /** Create a new board of the specified size. */
    public Board(int size) {
        this.size = size;x1=x2=y1=y2=-1;
        board = new char[size][size];
        for (int col = 0; col<size;col++) {
            for (int row = 0; row<size;row++) {
                board[col][row] = '0';
            }
        }
    }

    /** Return the size of this board. */
    public int size() {
        return this.size;
    }

    /** Removes all the stones placed on the board, effectively
     * resetting the board to its original state.
     */
    public void clear() {
        for (char[] chars : board) {
            Arrays.fill(chars, '0');
        }
        x1=x2=y1=y2=-1;
    }

    /** Return a boolean value indicating whether all the places
     * on the board are occupied or not.
     */
    public boolean isFull() {
        for(char[] array: board){
            for(char inter: array){
                if(inter == '0'){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Place a stone for the specified player at a specified
     * intersection (x, y) on the board.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     * @param player Player whose stone is to be placed
     */
    public void placeStone(int x, int y, Player player) {
        lastMove = new Place(x,y);
        board[y][x] = player.getSymbol();
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is empty or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isEmpty(int x, int y) {
        return board[y][x] == '0';
    }

    /**
     * Is the specified place on the board occupied?
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupied(int x, int y) {
        return board[y][x] != '0';
    }

    /**
     * Return a boolean value indicating whether the specified
     * intersection (x, y) on the board is occupied by the given
     * player or not.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public boolean isOccupiedBy(int x, int y, HumanPlayer player) {
        return board[y][x] == player.getSymbol();
    }

    /**
     * Return the player who occupies the specified intersection (x, y)
     * on the board. If the place is empty, this method returns null.
     *
     * @param x 0-based column (vertical) index
     * @param y 0-based row (horizontal) index
     */
    public Player playerAt(int x, int y, Player player1, Player player2) {
        if(board[y][x]==player1.getSymbol()){
            return player1;
        } else if (board[y][x]==player2.getSymbol()) {
            return player2;
        }
        else{
            return null;
        }
    }

    /**
     * Return a boolean value indicating whether the given player
     * has a winning row on the board. A winning row is a consecutive
     * sequence of five or more stones placed by the same player in
     * a horizontal, vertical, or diagonal direction.
     */
    public boolean isWonBy(int y, int x, Player player) {
        return searchHorizontal(y,x,player.getSymbol())||searchVertical(y,x,player.getSymbol())||searchDiagonalOne(y,x,player.getSymbol())||searchDiagonalTwo(y,x,player.getSymbol());
    }
    private boolean searchHorizontal(int y, int x, char symbol){
        int l, r, count;
        l = x-1;
        r = x+1;
        count = 1;
        winningRow.add(new Place(x,y));
        while(count<5){
            if(l>=0&&board[y][l]==symbol){
                winningRow.add(new Place(l,y));
                count++;
                l--;
            }
            else if(r< board.length&&board[y][r]==symbol){
                winningRow.add(new Place(r,y));
                count++;
                r++;
            }
            else{
                winningRow.clear();
                return false;
            }
        }
        return true;
    }
    private boolean searchVertical(int y, int x, char symbol){
        int u, d, count;
        u = y-1;
        d = y+1;
        count = 1;
        winningRow.add(new Place(x,y));
        while(count < 5){
            if(u >= 0 && board[u][x] == symbol){
                winningRow.add(new Place(x,u));
                count++;
                u--;
            }
            else if(d < board.length&&board[d][x]==symbol){
                winningRow.add(new Place(x,d));
                count++;
                d++;
            }
            else{
                winningRow.clear();
                return false;
            }
        }
        return true;
    }
    private boolean searchDiagonalOne(int y, int x, char symbol){
        int l, r, count, u, d;
        l = x-1;
        r = x+1;
        u = y-1;
        d = y+1;
        count = 1;
        winningRow.add(new Place(x,y));
        while(count < 5){
            if(l >= 0 && u >= 0 && board[u][l] == symbol){
                count++;
                winningRow.add(new Place(l,u));
                l--;
                u--;
            }
            else if(r < board.length && d < board.length && board[d][r] == symbol){
                count++;
                winningRow.add(new Place(r,d));
                r++;
                d++;
            }
            else{
                winningRow.clear();
                return false;
            }
        }
        return true;
    }
    private boolean searchDiagonalTwo(int y, int x, char symbol){
        int l, r, count, u, d;
        l = x-1;
        r = x+1;
        u = y-1;
        d = y+1;
        count = 1;
        winningRow.add(new Place(x,y));
        while(count<5){
            if(l >= 0 && d < board.length && board[d][l] == symbol){
                count++;
                winningRow.add(new Place(l,d));
                l--;
                d++;
            }
            else if(r < board.length && u >= 0 && board[u][r] == symbol){
                count++;
                winningRow.add(new Place(r,u));
                r++;
                u--;
            }
            else{
                winningRow.clear();
                return false;
            }
        }
        return true;
    }

    /** Return the winning row. For those who are not familiar with
     * the Iterable interface, you may return an object of
     * List<Place>. */
    public Iterable<Place> winningRow() {
        if(this.winningRow.isEmpty()){
            return null;
        }
        return this.winningRow;
    }

    public boolean isInWinningRow(int x, int y){
        return winningRow.contains(new Place(x,y));
    }

    public boolean isWin() {

        return won;
    }

    public void setWon(boolean won, String name) {
        this.won = won; this.winner = name;
    }


    /**
     * An intersection on an Omok board identified by its 0-based column
     * index (x) and row index (y). The indices determine the position
     * of a place on the board, with (0, 0) denoting the top-left
     * corner and (n-1, n-1) denoting the bottom-right corner,
     * where n is the size of the board.
     *
     * @param x 0-based column index of this place.
     * @param y 0-based row index of this place.
     */
        public record Place(int x, int y) {
        /**
         * Create a new place of the given indices.
         *
         * @param x 0-based column (vertical) index
         * @param y 0-based row (horizontal) index
         */
        public Place {
        }
            // other methods if needed ...


        @Override
            public String toString() {
                return "Place{" +
                        "x=" + x +
                        ", y=" + y +
                        '}';
            }
        }
}