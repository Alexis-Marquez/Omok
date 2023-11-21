import java.awt.*;
import java.util.Objects;

/**
 * A player in an Omok game. It holds the name of the player and
 * can be used to identify a specific player throughout the game.
 * The Player class helps to keep track of the moves made by each
 * player during the game.
 */
public class HumanPlayer extends Player{


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HumanPlayer that = (HumanPlayer) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    /** Create a new player with the given name. */
    public HumanPlayer(String name, char Symbol, Color color) {
        super(color);
        this.name = name;
        this.color = color;
        this.symbol=Symbol;
    }

    /** Return the name of this player. */
    public String name() {
        return name;
    }
    public char getSymbol(){return this.symbol;}
    public Color getColor(){return this.color;}
}
