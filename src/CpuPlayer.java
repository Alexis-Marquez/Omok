import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class CpuPlayer extends Player {
        private Board board;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CpuPlayer cpuPlayer = (CpuPlayer) o;
        return Objects.equals(board, cpuPlayer.board) && Objects.equals(name, cpuPlayer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash((Integer)super.hashCode(), board, name);
    }

    public CpuPlayer(String name, Board board, Color color){
            super(color);
            this.name=name;
            this.board=board;
            this.symbol = 'x';
        }
    public void pickPlace() {
        Random rand = new Random();
        int upperbound = board.size();
        int randX = rand.nextInt(upperbound);
        int randY = rand.nextInt(upperbound);
        if(!board.isOccupied(randX,randY)){
            board.placeStone(randX, randY, this);
            board.setWon(board.isWonBy(randY, randX, this), this.name);
        }
        else{
            this.pickPlace();
        }
    }
    public String getName() {
        return name;
    }

    @Override
    public char getSymbol() {
        return this.symbol;
    }
    public Color getColor(){return this.color;}
}
