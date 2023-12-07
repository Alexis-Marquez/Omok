import java.awt.*;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class CpuPlayerSmart extends Player{
    private final Board board;
    HumanPlayer opponent;
    Board.Place prevMove;
    private char[][] currBoard;
    private int move = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CpuPlayerSmart cpuPlayerSmart = (CpuPlayerSmart) o;
        return Objects.equals(board, cpuPlayerSmart.board) && Objects.equals(name, cpuPlayerSmart.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), board, name);
    }
    public CpuPlayerSmart(String name, Board board, Color color, HumanPlayer opponent){
        super(color);
        this.name=name;
        this.board=board;
        this.symbol = 'x';
        this.opponent = opponent;
        new Board.Place(board.size/2,board.size/2);
    }
    public void pickPlace() {
        currBoard = board.getCurrentBoard();
        if (!block(board.getLastMove().x(), board.getLastMove().y(), opponent)) {
            this.placeStone();
        }
    }

    private boolean block(int x, int y, HumanPlayer opponent) {
        int l, r, count, tries;
        l = x-1;
        r = x+1;
        count = 1;
        tries = 0;
        while(tries<5){
            if(l>=0&&currBoard[y][l]== opponent.getSymbol()){       //Horizontal block
                count++;
                l--;
                if(count>=3){
                    if(!board.isOccupied(l,y)) {
                        board.placeStone(l, y, this);
                        board.setWon(board.isWonBy(y, l, this), this.name);
                        return true;
                    }
                    else if(!board.isOccupied(r,y)){
                        board.placeStone(r, y, this);
                        board.setWon(board.isWonBy(y, r, this), this.name);
                        return true;
                    }
                }
            }
            else if(r< currBoard.length&&currBoard[y][r]== opponent.getSymbol()){
                count++;
                r++;
                if(count>=3){
                    if(!board.isOccupied(r,y)) {
                        board.placeStone(r, y, this);
                        board.setWon(board.isWonBy(y, r, this), this.name);
                        return true;
                    }
                    else if(!board.isOccupied(l,y)){
                        board.placeStone(l, y, this);
                        board.setWon(board.isWonBy(y, l, this), this.name);
                        return true;
                    }
                }
            }
            tries++;
        }
        int u, d;
        tries = 0;
        u = y-1;
        d = y+1;
        count = 1;
        while(tries < 5){
            if(u >= 0 && currBoard[u][x] == opponent.getSymbol()){          //Vertical block
                count++;
                u--;
                if(count>=3){
                    if(!board.isOccupied(x,u)) {
                        board.placeStone(x, u, this);
                        board.setWon(board.isWonBy(u, x, this), this.name);
                        return true;
                    }
                    else if(!board.isOccupied(x,d)){
                        board.placeStone(x, d, this);
                        board.setWon(board.isWonBy(d, x, this), this.name);
                        return true;
                    }
                }
            }
            else if(d < currBoard.length&&currBoard[d][x]== opponent.getSymbol()){
                count++;
                d++;
                if(count>=3){
                    if(!board.isOccupied(x,d)) {
                        board.placeStone(x, d, this);
                        board.setWon(board.isWonBy(d, x, this), this.name);
                        return true;
                    }
                    else if(!board.isOccupied(x,u)){
                        board.placeStone(x, u, this);
                        board.setWon(board.isWonBy(u, x, this), this.name);
                        return true;
                    }
                }
            }
            tries++;
        }
        l = x-1;
        r = x+1;
        u = y-1;
        d = y+1;
        count = 1;
        tries = 0;
        while(tries < 5){
            if(l >= 0 && u >= 0 && currBoard[u][l] == opponent.getSymbol()){
                count++;
                l--;
                u--;
                if(count>=3){
                    if(!board.isOccupied(l,u)) {
                        board.placeStone(l, u, this);
                        board.setWon(board.isWonBy(u, l, this), this.name);
                        return true;
                    }
                    else if(!board.isOccupied(r,d)){
                        board.placeStone(r, d, this);
                        board.setWon(board.isWonBy(d, r, this), this.name);
                        return true;
                    }
                }
            }
            else if(r < currBoard.length && d < currBoard.length && currBoard[d][r] == opponent.getSymbol()){
                count++;
                r++;
                d++;
                if(count>=3){
                    if(!board.isOccupied(r,d)) {
                        board.placeStone(r, d, this);
                        board.setWon(board.isWonBy(d, r, this), this.name);
                        return true;
                    }
                    else if(!board.isOccupied(l,u)){
                        board.placeStone(l, u, this);
                        board.setWon(board.isWonBy(u, l, this), this.name);
                        return true;
                    }
                }
            }
            tries++;
        }
        l = x-1;
        r = x+1;
        u = y-1;
        d = y+1;
        count = 1;
        tries = 0;
        while(tries<5){
            if(l >= 0 && d < currBoard.length && currBoard[d][l] == opponent.getSymbol()){
                count++;
                l--;
                d++;
                if(count>=3){
                    if(!board.isOccupied(l,d)) {
                        board.placeStone(l, d, this);
                        board.setWon(board.isWonBy(d, l, this), this.name);
                        return true;
                    }
                    else if(!board.isOccupied(r,u)){
                        board.placeStone(r, u, this);
                        board.setWon(board.isWonBy(u, r, this), this.name);
                        return true;
                    }
                }
            }
            else if(r < currBoard.length && u >= 0 && currBoard[u][r] == opponent.getSymbol()){
                count++;
                r++;
                u--;
                if(count>=3){
                    if(!board.isOccupied(r,u)) {
                        board.placeStone(r, u, this);
                        board.setWon(board.isWonBy(u, r, this), this.name);
                        return true;
                    }
                    else if(!board.isOccupied(l,d)){
                        board.placeStone(l, d, this);
                        board.setWon(board.isWonBy(d, l, this), this.name);
                        return true;
                    }
                }
            }
            tries++;
        }
        return false;
    }

    private void placeStone() {
        if(move == 0) {
            int x = board.size / 2;
            int y = board.size / 2;
            while (board.isOccupied(x, y)) {
                x++;
                y++;
            }
            board.placeStone(x, y, this);
            this.prevMove = new Board.Place(x, y);
            move++;
            board.setWon(board.isWonBy(y, x, this), this.name);
        }
        else {
            Board.Place curr = tryWin(prevMove.x(),prevMove.y());
                board.placeStone(curr.x(), curr.y(), this);
                prevMove = new Board.Place(curr.x(), curr.y());
                board.setWon(board.isWonBy(curr.y(), curr.x(), this), this.name);
        }
    }
    private Board.Place tryWin(int x, int y) {
        int l, r, count, tries;
        Board.Place currPlace = null;
        l = x;
        r = x;
        count = 0;
        int currMax = count;
        tries = 0;
        while (tries < 5) {
            if (l >= 0 && currBoard[y][l] == this.getSymbol()) {       //Horizontal block
                count++;
                l--;
                if (l>=0&&!board.isOccupied(l, y)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(l, y);
                    }
                } else if (r>=0&&r<board.size&&!board.isOccupied(r, y)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(r, y);
                    }
                }
            } else if (r < currBoard.length && currBoard[y][r] == this.getSymbol()) {
                count++;
                r++;
                if (r< board.size&&!board.isOccupied(r, y)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(r, y);
                    }
                } else if (l>=0&&l< board.size&&!board.isOccupied(l, y)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(l, y);
                    }
                }
            }
            tries++;
        }
        int u, d;
        tries = 0;
        u = y;
        d = y;
        count = 0;
        while (tries < 5) {
            if (u >= 0 && currBoard[u][x] == this.getSymbol()) {          //Vertical block
                count++;
                u--;
                if (u>=0&&!board.isOccupied(x, u)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(x, u);
                    }
                } else if (d>=0&&d< board.size&&!board.isOccupied(x, d)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(x, d);
                    }
                }
            } else if (d < currBoard.length && currBoard[d][x] == this.getSymbol()) {
                count++;
                d++;
                if (d<board.size&&!board.isOccupied(x, d)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(x, d);
                    }
                } else if (u>=0&&u< board.size&&!board.isOccupied(x, u)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(x, u);
                    }
                }
            }
            tries++;
        }
        l = x;
        r = x;
        u = y;
        d = y;
        count = 0;
        tries = 0;
        while (tries < 5) {
            if (l >= 0 && u >= 0 && currBoard[u][l] == this.getSymbol()) {
                count++;
                l--;
                u--;
                if (l>=0&&u>=0&&!board.isOccupied(l, u)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(l, u);
                    }
                } else if (r>=0&&r<board.size&&d<board.size&&d>=0&&!board.isOccupied(r, d)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(r, d);
                    }
                }
            } else if (r < currBoard.length && d < currBoard.length && currBoard[d][r] == this.getSymbol()) {
                count++;
                r++;
                d++;
                if (r<board.size&&d<board.size&&!board.isOccupied(r, d)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(r, d);
                    }
                } else if (l< board.size&&u<board.size&&u>=0&&l>=0&&!board.isOccupied(l, u)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(l, u);
                    }
                }
            }
            tries++;
        }
        l = x;
        r = x;
        u = y;
        d = y;
        count = 0;
        tries = 0;
        while (tries < 5) {
            if (l >= 0 && d < currBoard.length && currBoard[d][l] == this.getSymbol()) {
                count++;
                l--;
                d++;
                if (l>=0&&d<board.size&&!board.isOccupied(l, d)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(l, d);
                    }
                } else if (r>=0&&u>=0&&u<board.size&&r<board.size&&!board.isOccupied(r, u)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(r, u);
                    }
                }
            } else if (r < currBoard.length && u >= 0 && currBoard[u][r] == this.getSymbol()) {
                count++;
                r++;
                u--;
                if ((r<board.size)&&u>=0&&!board.isOccupied(r, u)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(r, u);
                    }
                } else if (l<board.size&&l>=0&&d<board.size&&d>=0&&!board.isOccupied(l, d)) {
                    if (currMax < count) {
                        currMax = count;
                        currPlace = new Board.Place(l, d);
                    }
                }
            }
            tries++;
        }
        if (currPlace == null) {
            int xRand;
            int n = 0;
            int yRand;
            while(true){
                    if (n < 20) {
                        xRand = prevMove.x() + ThreadLocalRandom.current().nextInt(-1, 1 + 1);
                        yRand = prevMove.y() + ThreadLocalRandom.current().nextInt(-1, 1 + 1);
                    } else {
                        xRand = prevMove.x() + ThreadLocalRandom.current().nextInt(-board.size + prevMove.x(), board.size - prevMove.x() + 1);
                        yRand = prevMove.y() + ThreadLocalRandom.current().nextInt(-board.size + prevMove.x(), board.size - prevMove.x() + 1);
                    }
                    if(xRand< board.size&&xRand>=0&&yRand<board.size&&yRand>=0) {
                        if (!board.isOccupied(xRand, yRand)) {
                            break;
                        }
                    }
                n++;
            }
            System.out.println(xRand+", "+yRand);
            return new Board.Place(xRand, yRand);
        } else {
                return currPlace;
            }
        }

    @Override
    public char getSymbol() {
        return this.symbol;
    }
    public Color getColor(){return this.color;}
}
