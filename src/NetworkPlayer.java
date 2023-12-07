import java.awt.*;

public class NetworkPlayer extends Player {
    private final Board board;

    public NetworkPlayer(String name, Board board, Color color) {
        super(color);
        this.name=name;
        this.board=board;
        this.symbol = 'x';
    }

    public void pickPlace(int x, int y){
        if(!board.isOccupied(x,y)){
            board.placeStone(x, y, this);
            board.setWon(board.isWonBy(y, x, this), this.name);
        }
    }
    @Override
    public char getSymbol() {
        return 0;
    }

    @Override
    public Color getColor() {
        return null;
    }
}
