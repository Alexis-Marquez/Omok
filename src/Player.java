import java.awt.*;
import java.util.Objects;

public abstract class Player {
    protected char symbol;
    protected Color color;
    protected String name;

    protected Player(Color color) {
        this.color = color;
    }

    public abstract char getSymbol();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return symbol == player.symbol && Objects.equals(color, player.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, color);
    }

    public abstract Color getColor();
}